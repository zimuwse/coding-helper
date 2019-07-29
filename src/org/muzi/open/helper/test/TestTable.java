package org.muzi.open.helper.test;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: li.rui
 * @time: 2019-07-29 17:14
 * @description:
 */
public class TestTable {
    public static void main(String[] args) {
        // frame
        JFrame f;
        // Table
        JTable j;
        // Frame initiallization
        f = new JFrame();

        // Frame Title
        f.setTitle("JTable Example"); //$NON-NLS-1$

        // Initializing the JTable
        j = new JTable(new JTableModel());
        j.addColumn(new TableColumn(0));
        j.addColumn(new TableColumn(1));
        j.addColumn(new TableColumn(2, 200, new ButtonRenderer(), new ButtonEditor()));
        j.addColumn(new TableColumn(3, 200, new ButtonRenderer(), new ButtonEditor()));

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        // Frame Size
        f.setSize(500, 200);
        // Frame Visible = true
        f.setVisible(true);


    }


    static class ButtonRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = (JButton) value;
            button.setText("before");
            return button;
        }
    }

    static class ButtonEditor extends DefaultCellEditor {
        private static final long serialVersionUID = -6546334664166791132L;

        public ButtonEditor() {
            super(new JTextField());
            this.setClickCountToStart(1);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JButton button = (JButton) value;
            button.setText("after");
            button.addActionListener(new AbstractAction() {
                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("edit!!!!"); //$NON-NLS-1$
                }
            });
            return button;
        }
    }

    static class JTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private static final String[] COLUMN_NAMES = new String[]{"Id", "Stuff", "Button1", "Button2"};
        private static final Class<?>[] COLUMN_TYPES = new Class<?>[]{Integer.class, String.class, JButton.class, JButton.class};

        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        @Override
        public int getRowCount() {
            return 4;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return COLUMN_NAMES[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return COLUMN_TYPES[columnIndex];
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            /*Adding components*/
            switch (columnIndex) {
                case 0:
                    return rowIndex;
                case 1:
                    return "Text for " + rowIndex;
                case 2: // fall through
                    /*Adding button and creating click listener*/
                case 3:
                    final JButton button = new JButton(COLUMN_NAMES[columnIndex]);
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(button),
                                    "Button clicked for row " + rowIndex);
                        }
                    });
                    return button;
                default:
                    return "Error";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            switch (columnIndex) {

                case 0:
                    return false;
                case 1:
                    return false;
                case 2:
                    return true;
                case 3:
                    return true;
                default:
                    return false;

            }
        }
    }


}
