package org.muzi.open.helper.config.db;

import org.muzi.open.helper.model.db.Table;
import org.muzi.open.helper.model.db.TableField;
import org.muzi.open.helper.model.db.TableIndex;
import org.muzi.open.helper.model.java.JavaField;

import java.sql.Connection;
import java.util.List;

/**
 * @author: muzi
 * @time: 2018-05-17 15:26
 * @description:
 */
public interface DBOperation {

    /**
     * database type
     *
     * @return
     */
    String type();

    /**
     * driver class
     *
     * @return
     */
    String driverClassName();

    /**
     * get connection
     *
     * @return
     */
    Connection connect() throws Exception;

    /**
     * connection url
     *
     * @return
     */
    String url();

    /**
     * sql to list tables of database
     *
     * @param database
     * @return
     */
    String sqlOfTables(String database);

    /**
     * sql to get metadata of the given table
     *
     * @param database
     * @param table
     * @return
     */
    String sqlOfTable(String database, String table);

    /**
     * sql to list fields of table
     *
     * @param table
     * @return
     */
    String sqlOfFields(String table);

    /**
     * sql to list indexes of table
     *
     * @param table
     * @return
     */
    String sqlOfIndexes(String table);


    /**
     * list tables of database
     *
     * @param database
     * @return
     */
    List<Table> tables(String database);


    /**
     * get the given table's metadata
     *
     * @param database
     * @param table
     * @return
     */
    Table table(String database, String table);

    /**
     * list indexes of table
     *
     * @param table
     * @return
     */
    List<TableIndex> indexes(String table);

    /**
     * list fields of table
     *
     * @param table
     * @return
     */
    List<TableField> fields(String table);

    /**
     * table fields to java fields
     *
     * @param tableFields
     * @return
     */
    List<JavaField> toJavaFields(List<TableField> tableFields);

    /**
     * type mapping
     *
     * @param type
     * @return
     */
    Class mapping(String type);

    /**
     * close connection
     */
    void close();
}
