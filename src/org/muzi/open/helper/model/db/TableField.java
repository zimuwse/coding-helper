package org.muzi.open.helper.model.db;

/**
 * @author: muzi
 * @time: 2018-05-17 14:52
 * @description:
 */
public class TableField {
    private String name;
    private String type;
    private int length;
    private boolean unsigned;
    private boolean notNull;
    private String comment;
    private String defaultValue = "";
    private String extra = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean getUnsigned() {
        return unsigned;
    }

    public void setUnsigned(boolean unsigned) {
        this.unsigned = unsigned;
    }

    public boolean getNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        if (null != comment)
            this.comment = comment;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        if (null != defaultValue)
            this.defaultValue = defaultValue;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        if (null != extra)
            this.extra = extra;
    }
}
