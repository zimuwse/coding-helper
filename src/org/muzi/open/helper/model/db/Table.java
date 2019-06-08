package org.muzi.open.helper.model.db;

/**
 * @author: muzi
 * @time: 2018-05-17 16:03
 * @description:
 */
public class Table {
    private String name;
    private String comment;

    public Table() {
    }

    public Table(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
