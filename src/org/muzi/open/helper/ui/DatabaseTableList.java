package org.muzi.open.helper.ui;

import org.muzi.open.helper.config.db.DBOperation;
import org.muzi.open.helper.config.db.DBTypeConfig;
import org.muzi.open.helper.model.db.Table;
import org.muzi.open.helper.model.java.TableToJavaPreference;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @author: li.rui
 * @time: 2019-07-26 18:39
 * @description:
 */
public class DatabaseTableList extends BaseUI {
    private static final long serialVersionUID = 5368055922684871432L;
    private JPanel panel;
    private JScrollPane jspDBTable;
    private JTable dbTable;
    private JButton btnConfirm;
    private JButton btnCancel;
    private TableToJavaPreference preference;
    private List<Table> tables;

    public DatabaseTableList(TableToJavaPreference preference) throws HeadlessException {
        this.preference = preference;
    }

    @Override
    protected JPanel panel() {
        return panel;
    }


    @Override
    protected void initUI() {
        super.initUI();
        try {
            DBOperation operation = DBTypeConfig.getInstance(this.preference);
            operation.connect();
            this.tables = operation.tables(preference.getDbName());
            operation.close();
            initTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTable() {
        String[] columns = {"ALL", "TABLE NAME", "TABLE COMMENT"};
        Object[][] data = new Object[tables.size()][columns.length];
        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            data[i][0] = false;
            data[i][1] = table.getName();
            data[i][2] = table.getComment();
        }
        CheckHeaderCellTableModel model = new CheckHeaderCellTableModel(data, columns);

        TableDataButtonEvent event = new TableDataButtonEvent() {
            @Override
            public void invoke(ActionEvent e) {
                TableDataButton button = (TableDataButton) e.getSource();
                String tableName = dbTable.getModel().getValueAt(button.getRow(), 1).toString();
                new ChooseMethod(preference, tableName).show(600, 600);
            }
        };

        dbTable.setModel(model);
        dbTable.setAutoCreateColumnsFromModel(true);
        dbTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
        dbTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        dbTable.setBounds(30, 30, 500, 300);
        dbTable.setRowHeight(25);
        dbTable.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(dbTable));
        dbTable.getColumnModel().getColumn(3).setCellEditor(new TableDataButtonEditor(event));

    }

    static class CheckHeaderCellTableModel extends DefaultTableModel {
        private static final long serialVersionUID = -3295581072864170310L;

        public CheckHeaderCellTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == getColumnCount() - 1)
                return JButton.class;
            return getValueAt(0, columnIndex).getClass();
        }

        public void selectAllOrNull(boolean value) {
            for (int index = 0; index < getRowCount(); index++) {
                this.setValueAt(value, index, 0);
            }
        }
    }

    static class CheckHeaderCellRenderer implements TableCellRenderer {
        CheckHeaderCellTableModel tableModel;
        JTableHeader tableHeader;
        final JCheckBox selectBox;
        final TableDataButton button;

        public CheckHeaderCellRenderer(final JTable table) {
            this.tableModel = (CheckHeaderCellTableModel) table.getModel();
            this.tableHeader = table.getTableHeader();
            selectBox = new JCheckBox();
            selectBox.setSelected(false);
            button = new TableDataButton();
            button.setText("Config Methods");
            tableHeader.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() > 0) {
                        // 获得选中列
                        int selectColumn = tableHeader.columnAtPoint(e.getPoint());
                        if (selectColumn == 0) {
                            boolean value = !selectBox.isSelected();
                            selectBox.setSelected(value);
                            tableModel.selectAllOrNull(value);
                            tableHeader.repaint();
                        }
                    }
                }
            });
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            String valueStr = (String) value;
            JComponent component = null;
            if (column == 0) {
                selectBox.setHorizontalAlignment(SwingConstants.CENTER);
                selectBox.setBorderPainted(true);
                component = selectBox;
            } else if (column == 3) {
                button.setCol(column);
                button.setRow(row);
            } else {
                JLabel label = new JLabel(valueStr);
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                component = label;
            }
            //component.setForeground(tableHeader.getForeground());
            //component.setBackground(tableHeader.getBackground());
            //component.setFont(tableHeader.getFont());
            //component.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            return component;
        }
    }

    static class TableDataButton extends JButton {
        private static final long serialVersionUID = 3073329031741685959L;
        private int row;
        private int col;

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }
    }

    interface TableDataButtonEvent {
        void invoke(ActionEvent e);
    }


    class TableDataButtonEditor extends DefaultCellEditor {
        private static final long serialVersionUID = 3950200155663470286L;
        private TableDataButton button;
        private TableDataButtonEvent event;

        public TableDataButtonEditor(TableDataButtonEvent event) {
            super(new JTextField());
            this.event = event;
            button = new TableDataButton();
            button.setText("Config Methods");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    event.invoke(e);
                }
            });
        }
    }

}
