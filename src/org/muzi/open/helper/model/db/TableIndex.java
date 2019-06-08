package org.muzi.open.helper.model.db;

import java.util.List;

/**
 * @author: muzi
 * @time: 2018-05-17 15:45
 * @description:
 */
public class TableIndex {
    private String name;
    private boolean unique;
    private String field;
    private List<String> fields;

    public TableIndex(String name, boolean unique, String field) {
        this.name = name;
        this.unique = unique;
        this.field = field;
    }

    public TableIndex(String name, boolean unique, List<String> fields) {
        this.name = name;
        this.unique = unique;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public String getField() {
        return field;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "TableIndex{" +
                "name='" + name + '\'' +
                ", unique=" + unique +
                ", field='" + field + '\'' +
                ", fields=" + fields +
                '}';
    }
}
