package org.muzi.open.helper.model.java;

/**
 * @author: li.rui
 * @time: 2019-07-24 00:26
 * @description:
 */
public enum JavaMethodType {
    SELECT_ONE("queryBy", true),
    SELECT_LIST("queryListBy", true),
    PAGE_QUERY("pageQuery", true),
    INSERT("insert", false),
    INSERT_BATCH("insertBatch", false),
    UPDATE("updateBy", true),
    DELETE("delBy", true);

    private String prefix;
    private boolean acceptParams;

    JavaMethodType(String prefix, boolean acceptParams) {
        this.prefix = prefix;
        this.acceptParams = acceptParams;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isAcceptParams() {
        return acceptParams;
    }

    public static JavaMethodType get(String name) {
        for (JavaMethodType type : JavaMethodType.values()) {
            if (type.name().equalsIgnoreCase(name))
                return type;
        }
        return null;
    }
}
