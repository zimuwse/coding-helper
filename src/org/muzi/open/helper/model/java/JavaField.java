package org.muzi.open.helper.model.java;

import org.muzi.open.helper.model.db.TableField;
import org.muzi.open.helper.util.StringUtil;

/**
 * @author: muzi
 * @time: 2018-05-22 19:02
 * @description:
 */
public class JavaField extends TableField {
    private String upFirstFieldName;
    private String fieldName;
    private String fieldType;
    private Class fieldTypeClz;

    public JavaField(TableField field) {
        setName(field.getName());
        setType(field.getType());
        setLength(field.getLength());
        setUnsigned(field.getUnsigned());
        setNotNull(field.getNotNull());
        setComment(field.getComment());
        setDefaultValue(field.getDefaultValue());
        setExtra(field.getExtra());
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
        this.upFirstFieldName = StringUtil.upperFirst(fieldName);
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public void setFieldTypeClz(Class fieldTypeClz) {
        this.fieldTypeClz = fieldTypeClz;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public Class getFieldTypeClz() {
        return fieldTypeClz;
    }

    public String getUpFirstFieldName() {
        return upFirstFieldName;
    }
}
