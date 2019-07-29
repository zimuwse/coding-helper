package org.muzi.open.helper.ui;

import org.muzi.open.helper.config.db.DBOperation;
import org.muzi.open.helper.config.db.DBTypeConfig;
import org.muzi.open.helper.model.db.TableField;
import org.muzi.open.helper.model.db.TableIndex;
import org.muzi.open.helper.model.db.TableMethod;
import org.muzi.open.helper.model.java.JavaMethodType;
import org.muzi.open.helper.model.java.JavaMethodUtil;
import org.muzi.open.helper.model.java.TableToJavaPreference;
import org.muzi.open.helper.util.PopUtil;
import org.muzi.open.helper.util.StringUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * @author: li.rui
 * @time: 2019-07-23 22:32
 * @description:
 */
public class ChooseMethod extends BaseUI {
    private static final long serialVersionUID = 7389911664362150067L;
    private JPanel panel;
    private JTable tableFieldsHolder;
    private JScrollPane jspTableFieldHolder;
    private JButton btnChooseSelected;
    private JButton btnDelSelected;
    private JScrollPane jspMethodHolder;
    private JTable tableMethos;
    private JComboBox cbMethodType;
    private JButton btnConfirm;
    private TableToJavaPreference preference;
    private String table;
    private List<TableField> fields = null;
    private List<TableIndex> indices = null;
    private Set<String> indexFields = new HashSet<>();

    @Override
    protected JPanel panel() {
        return panel;
    }

    public ChooseMethod(TableToJavaPreference preference, String table) {
        this.preference = preference;
        this.table = table;
        try {
            DBOperation operation = DBTypeConfig.getInstance(this.preference);
            operation.connect();
            fields = operation.fields(table);
            indices = operation.indexes(table);
            for (TableIndex index : indices) {
                if (null != index.getField())
                    indexFields.add(index.getField());
                if (null != index.getFields())
                    indexFields.addAll(index.getFields());
            }
            operation.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initUI() {
        super.initUI();
        initMethodType();
        initFieldsTable();
        initMethodsTable();
        initDefaultMethod();
    }

    private void initMethodType() {
        JavaMethodType[] javaMethodTypes = JavaMethodType.values();
        for (JavaMethodType methodType : javaMethodTypes)
            cbMethodType.addItem(methodType.name());
    }

    private void initFieldsTable() {
        String[] columns = {"NAME", "TYPE", "NULL", "DEFAULT", "COMMENT"};
        Object[][] data = null;
        if (null != fields) {
            data = new Object[fields.size()][columns.length];
            for (int i = 0; i < fields.size(); i++) {
                TableField field = fields.get(i);
                data[i][0] = field.getName();
                data[i][1] = field.getType();
                data[i][2] = field.getNotNull();
                data[i][3] = field.getDefaultValue();
                data[i][4] = field.getComment();
            }
        }
        DefaultTableModel tableModel = new TableFieldsTableModel(data, columns);
        tableFieldsHolder.setAutoCreateColumnsFromModel(true);
        tableFieldsHolder.setModel(tableModel);
        tableFieldsHolder.setPreferredScrollableViewportSize(new Dimension(500, 300));
        tableFieldsHolder.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        tableFieldsHolder.setBounds(30, 30, 500, 300);
        tableFieldsHolder.setRowHeight(25);
    }

    private void initMethodsTable() {
        String[] methodCols = {"METHOD NAME", "METHOD TYPE", "METHOD PARAMS"};
        DefaultTableModel methodModel = new TableFieldsTableModel(null, methodCols, true);
        tableMethos.setAutoCreateColumnsFromModel(true);
        tableMethos.setModel(methodModel);
        tableMethos.setPreferredScrollableViewportSize(new Dimension(500, 300));
        tableMethos.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        tableMethos.setBounds(30, 30, 500, 300);
        tableMethos.setRowHeight(25);
        tableMethos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    private void initDefaultMethod() {
        if (null == this.indices)
            return;
        //insert & insertBatch
        addMethod(JavaMethodType.INSERT, buildMethodName(JavaMethodType.INSERT, null, null), null);
        addMethod(JavaMethodType.INSERT_BATCH, buildMethodName(JavaMethodType.INSERT_BATCH, null, null), null);
        //pageQuery
        List<String> indexFields = new ArrayList<>(this.indexFields.size());
        indexFields.addAll(this.indexFields);
        String[] indexFieldArr = indexFields.toArray(new String[0]);
        addMethod(JavaMethodType.PAGE_QUERY, buildMethodName(JavaMethodType.PAGE_QUERY, indexFields, indexFieldArr), indexFieldArr);
        //index analyse
        for (TableIndex index : this.indices) {
            List<String> fields = new ArrayList<>(4);
            if (null != index.getFields()) {
                fields.addAll(index.getFields());
            } else {
                fields.add(index.getField());
            }
            String[] fieldArr = fields.toArray(new String[0]);
            if (index.isUnique()) {
                addMethod(JavaMethodType.DELETE, buildMethodName(JavaMethodType.DELETE, fields, fieldArr), fieldArr);
                addMethod(JavaMethodType.SELECT_ONE, buildMethodName(JavaMethodType.SELECT_ONE, fields, fieldArr), fieldArr);
                addMethod(JavaMethodType.UPDATE, buildMethodName(JavaMethodType.UPDATE, fields, fieldArr), fieldArr);
            } else {
                addMethod(JavaMethodType.SELECT_LIST, buildMethodName(JavaMethodType.SELECT_LIST, fields, fieldArr), fieldArr);
            }
        }
    }

    private void addMethod(JavaMethodType methodType, String currMethodName, String[] fieldsArr) {
        DefaultTableModel model = (DefaultTableModel) tableMethos.getModel();
        Object[] method = new Object[3];
        method[0] = currMethodName;
        method[1] = methodType;
        if (methodType.isAcceptParams())
            method[2] = StringUtil.join(fieldsArr, ",");
        else
            method[2] = "";
        model.addRow(method);
    }


    @Override
    protected void initBind() {
        btnChooseSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = tableFieldsHolder.getSelectedRows();
                if (rows.length == 0)
                    return;
                List<String> fields = new ArrayList<>(rows.length);
                for (int row : rows) {
                    fields.add(tableFieldsHolder.getValueAt(row, 0).toString());
                }
                String[] fieldArr = fields.toArray(new String[0]);
                DefaultTableModel model = (DefaultTableModel) tableMethos.getModel();
                JavaMethodType methodType = JavaMethodType.get(cbMethodType.getSelectedItem().toString());
                String currMethodName = buildMethodName(methodType, fields, fieldArr);
                int rowsCount = model.getRowCount();
                boolean exist = false;
                for (int i = 0; i < rowsCount; i++) {
                    String existMethod = model.getValueAt(i, 0).toString();
                    if (existMethod.equalsIgnoreCase(currMethodName)) {
                        exist = true;
                        break;
                    }
                }
                if (exist) {
                    PopUtil.info("method[" + currMethodName + "] already existed.");
                    return;
                }
                Object[] method = new Object[3];
                method[0] = currMethodName;
                method[1] = methodType;
                if (methodType.isAcceptParams())
                    method[2] = StringUtil.join(fieldArr, ",");
                else
                    method[2] = "";
                model.addRow(method);
            }
        });

        btnDelSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = tableMethos.getSelectedRows();
                if (rows.length == 0)
                    return;
                DefaultTableModel model = (DefaultTableModel) tableMethos.getModel();
                for (int row : rows) {
                    model.removeRow(row);
                }
            }
        });
    }

    /**
     * delete,update的参数作为where条件
     * select one,select list的参数作为where条件,select one增加limit 1,select list不加limit限制
     * insert,insertBatch不接受参数
     * pageQuery的参数作为where条件，并且增加offset、pageSize
     *
     * @param methodType
     * @param fields
     * @param fieldArr
     * @return
     */
    private String buildMethodName(JavaMethodType methodType, List<String> fields, String[] fieldArr) {
        List<String> indexFieldsList = new ArrayList<>(this.indexFields);
        if (null != fields)
            indexFieldsList.retainAll(fields);
        String prefix = JavaMethodUtil.getMethodPrefix(this.preference, methodType);
        switch (methodType) {
            case SELECT_ONE:
            case SELECT_LIST:
                return prefix + StringUtil.upperFirst(StringUtil.camelCase(StringUtil.join(fieldArr, "_")));
            case INSERT:
            case INSERT_BATCH:
            case PAGE_QUERY:
                return prefix;
            case DELETE:
            case UPDATE:
                return prefix + StringUtil.upperFirst(StringUtil.camelCase(StringUtil.join(indexFieldsList.toArray(new String[0]), "_")));
        }
        return prefix + StringUtil.upperFirst(StringUtil.camelCase(StringUtil.join(fieldArr, "_")));
    }

    public Set<TableMethod> getMethods() {
        Set<TableMethod> set = new HashSet<>();
        DefaultTableModel model = (DefaultTableModel) tableMethos.getModel();
        int rows = model.getRowCount();
        if (rows > 0) {
            for (int i = 0; i < rows; i++) {
                String methodName = model.getValueAt(i, 0).toString();
                if (StringUtil.isEmpty(methodName))
                    continue;
                String methodType = model.getValueAt(i, 1).toString();
                String methodParamsStr = model.getValueAt(i, 2).toString();
                if (StringUtil.isEmpty(methodParamsStr))
                    set.add(new TableMethod(table, methodType, methodName, null));
                else {
                    String[] methodParams = methodParamsStr.split(",");
                    set.add(new TableMethod(table, methodType, methodName, Arrays.asList(methodParams)));
                }
            }
        }
        return set;
    }

    static class TableFieldsTableModel extends DefaultTableModel {
        private static final long serialVersionUID = 4523870312270719236L;
        private boolean isMethodTable;

        public TableFieldsTableModel(Object[][] data, Object[] columnNames, boolean isMethodTable) {
            super(data, columnNames);
            this.isMethodTable = isMethodTable;
        }

        public TableFieldsTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            if (!isMethodTable)
                return false;
            if (column == 0)
                return true;
            return false;
        }
    }

}
