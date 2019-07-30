package org.muzi.open.helper.ui;

import org.muzi.open.helper.config.db.DBOperation;
import org.muzi.open.helper.config.db.DBTypeConfig;
import org.muzi.open.helper.model.db.Table;
import org.muzi.open.helper.model.db.TableMethod;
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
import java.util.*;
import java.util.List;

import static org.muzi.open.helper.util.LogUtils.log;

/**
 * @author: muzi
 * @time: 2019-07-26 18:39
 * @description:
 */
public class DatabaseTableList extends BaseUI implements MapUIResult<String, Set<TableMethod>> {
    private static final long serialVersionUID = 5368055922684871432L;
    private JPanel panel;
    private JScrollPane jspDBTable;
    private JTable dbTable;
    private JButton btnConfirm;
    private JButton btnCancel;
    private TableToJavaPreference preference;
    private List<Table> tables;
    private Map<String, Set<TableMethod>> methods;
    private UIResult<Map<String, Set<TableMethod>>> uiResult;

    public DatabaseTableList(TableToJavaPreference preference, UIResult<Map<String, Set<TableMethod>>> uiResult) throws HeadlessException {
        this.preference = preference;
        this.methods = new HashMap<>();
        this.uiResult = uiResult;
    }

    @Override
    protected JPanel panel() {
        return panel;
    }


    @Override
    protected void initUI() {
        super.initUI();
        setTitle("Choose Tables From Database["+preference.getDbName()+"] TO Generate Files");
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

    @Override
    protected void initBind() {
        super.initBind();
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiResult.setUIResult(methods);
                close();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
    }

    private void initTable() {
        String[] columns = {"ALL", "TABLE NAME", "TABLE COMMENT", "METHODS CONFIG"};
        Object[][] data = new Object[tables.size()][columns.length];
        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            data[i][0] = false;
            data[i][1] = table.getName();
            data[i][2] = table.getComment();
            data[i][3] = null;
        }
        CheckHeaderCellTableModel model = new CheckHeaderCellTableModel(data, columns);

        TableDataButtonEvent event = new TableDataButtonEvent() {
            @Override
            public void invoke(ActionEvent e) {
                TableDataButton button = (TableDataButton) e.getSource();
                String tableName = dbTable.getModel().getValueAt(button.getRow(), 1).toString();
                new ChooseMethod(preference, tableName, DatabaseTableList.this).show(800, 600);
            }

            @Override
            public void onCall(int row, int col) {
                String tableName = dbTable.getModel().getValueAt(row, 1).toString();
                new ChooseMethod(preference, tableName, DatabaseTableList.this).show(800, 600);
            }
        };
        dbTable.setRowHeight(25);
        dbTable.setModel(model);
        dbTable.setRowSelectionAllowed(false);
        dbTable.setAutoCreateColumnsFromModel(true);
        dbTable.setBounds(30, 30, 500, 300);
        dbTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        dbTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
        dbTable.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(dbTable));
        dbTable.getColumnModel().getColumn(3).setCellRenderer(new ColumnCellRenderer());
        dbTable.getColumnModel().getColumn(3).setCellEditor(new TableDataButtonEditor(event));
        //dbTable.updateUI();

    }

    @Override
    public void addMapUIResult(String key, Set<TableMethod> val) {
        log("addMapUIResult=key:{},val:{}", key, val);
        this.methods.put(key, val);
    }

    @Override
    public Set<TableMethod> getMapUIResult(String key) {
        return this.methods.get(key);
    }

    static class CheckHeaderCellTableModel extends DefaultTableModel {
        private static final long serialVersionUID = -3295581072864170310L;

        public CheckHeaderCellTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0 || column == getColumnCount() - 1;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
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
        private JCheckBox checkBox;

        public CheckHeaderCellRenderer(final JTable table) {
            this.tableModel = (CheckHeaderCellTableModel) table.getModel();
            this.tableHeader = table.getTableHeader();
            checkBox = new JCheckBox();
            checkBox.setText("ALL");
            checkBox.setSelected(false);
            tableHeader.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() > 0) {
                        // 获得选中列
                        int selectColumn = tableHeader.columnAtPoint(e.getPoint());
                        if (selectColumn == 0) {
                            boolean value = !checkBox.isSelected();
                            checkBox.setSelected(value);
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
                checkBox.setBorderPainted(true);
                checkBox.setHorizontalAlignment(SwingConstants.CENTER);
                component = checkBox;
            } else {
                JLabel label = new JLabel(valueStr);
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                component = label;
            }
            component.setForeground(tableHeader.getForeground());
            component.setBackground(tableHeader.getBackground());
            component.setFont(tableHeader.getFont());
            component.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            return component;
        }
    }

    static class ColumnCellRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return new TableDataButton("Config Methods", row, column);
        }
    }

    static class TableDataButton extends JButton {
        private static final long serialVersionUID = 3073329031741685959L;
        private int row;
        private int col;

        public TableDataButton(String text, int row, int col) {
            super(text);
            this.row = row;
            this.col = col;
        }

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

        void onCall(int row, int col);
    }


    class TableDataButtonEditor extends DefaultCellEditor {
        private static final long serialVersionUID = 3950200155663470286L;
        private TableDataButtonEvent event;

        public TableDataButtonEditor(TableDataButtonEvent event) {
            super(new JTextField());
            this.event = event;
        }

        @Override
        public boolean isCellEditable(EventObject anEvent) {
            return true;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            TableDataButton button = new TableDataButton("Config Methods", row, column);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TableDataButtonEditor.this.fireEditingCanceled();
                    getEvent().invoke(e);
                }
            });
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    getEvent().onCall(row, column);
                }
            }).start();*/
            return button;
        }

        private TableDataButtonEvent getEvent() {
            return event;
        }
    }

}
