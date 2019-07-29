package org.muzi.open.helper.model.java;

import org.muzi.open.helper.model.db.TableMethod;

import java.util.Set;

/**
 * @author: muzi
 * @time: 2019-07-23 20:43
 * @description:
 */
public class JavaMethodConfig {
    //name of table
    private String tableName;
    //table methods
    private Set<TableMethod> tableMethods;

    public JavaMethodConfig(String tableName, Set<TableMethod> tableMethods) {
        this.tableName = tableName;
        this.tableMethods = tableMethods;
    }

    public String getTableName() {
        return tableName;
    }

    public Set<TableMethod> getTableMethods() {
        return tableMethods;
    }
}
