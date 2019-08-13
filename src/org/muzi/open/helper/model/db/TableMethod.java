package org.muzi.open.helper.model.db;

import java.util.List;

/**
 * @author: muzi
 * @time: 2019-07-23 20:43
 * @description:
 */
public class TableMethod {
    //name of table
    private String tableName;
    //sql type:pageQuery,select one ,select list,update,insert,insert batch,delete
    private String methodType;
    //such as queryBy,getBy,insert,add,updateById
    private String methodName;
    //params
    private List<String> tableFields;

    public TableMethod(TableMethod tableMethod) {
        this(tableMethod.tableName, tableMethod.methodType, tableMethod.methodName, tableMethod.tableFields);
    }

    public TableMethod(String tableName, String methodType, String methodName, List<String> tableFields) {
        this.tableName = tableName;
        this.methodType = methodType;
        this.methodName = methodName;
        this.tableFields = tableFields;
    }

    public String getTableName() {
        return tableName;
    }

    public String getMethodType() {
        return methodType;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<String> getTableFields() {
        return tableFields;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TableMethod))
            return false;
        TableMethod that = (TableMethod) obj;
        if (this.tableName.equals(that.tableName)) {
            if (this.methodName.equals(that.methodName)) {
                return true;
            }
        }
        return false;
    }
}
