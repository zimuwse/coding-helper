package org.muzi.open.helper.model.java;

/**
 * @author: muzi
 * @time: 2018-05-22 19:34
 * @description:
 * @Param("name") javaType name
 */
public class MapperParameter {
    private String fieldName;
    private String javaName;
    private String javaType;
    private String paramName;
    private boolean annotation;
    private String comment;

    public MapperParameter(String fieldName, String javaName, String javaType, String paramName, boolean annotation, String comment) {
        this.fieldName = fieldName;
        this.javaName = javaName;
        this.javaType = javaType;
        this.paramName = paramName;
        this.annotation = annotation;
        this.comment = comment;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getJavaType() {
        return javaType;
    }

    public boolean getAnnotation() {
        return annotation;
    }

    public String getComment() {
        return comment;
    }

    public String getJavaName() {
        return javaName;
    }

    public String getParamName() {
        return paramName;
    }
}
