package org.muzi.open.helper.config.db;

import org.muzi.open.helper.model.db.Table;
import org.muzi.open.helper.model.db.TableField;
import org.muzi.open.helper.model.db.TableIndex;
import org.muzi.open.helper.model.java.JavaField;
import org.muzi.open.helper.util.StringUtil;

import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.*;

/**
 * @author: muzi
 * @time: 2018-05-17 15:50
 * @description:
 */
public abstract class AbstractDBOperation implements DBOperation {
    private Connection connection = null;
    private DBConfig config;

    public AbstractDBOperation(DBConfig config) {
        this.config = config;
    }

    protected DBConfig config() {
        return config;
    }

    /**
     * get connection
     *
     * @return
     */
    @Override
    public Connection connect() throws Exception {
        if (null == connection) {
            synchronized (this) {
                if (null == connection)
                    connection = getConnection();
            }
        }
        return connection;
    }

    /**
     * load driver class
     *
     * @throws Exception
     */
    private Driver loadDriver(String driverPath) throws Exception {
        driverPath = "file:" + driverPath;
        URLClassLoader loader = new URLClassLoader(new URL[]{new URL(driverPath)}, ClassLoader.getSystemClassLoader());
        Class clz = loader.loadClass(driverClassName());
        return (Driver) clz.newInstance();
    }

    /**
     * get connection
     *
     * @return
     * @throws Exception
     */
    private Connection getConnection() throws Exception {
        Driver driver = loadDriver(config().getDriverJar());
        Properties properties = new Properties();
        properties.setProperty("user", config().getUser());
        properties.setProperty("password", config().getPwd());
        return driver.connect(url(), properties);
    }

    /**
     * list tables of database
     *
     * @param database
     * @return
     */
    @Override
    public List<Table> tables(String database) {
        try {
            List<Table> tables = new ArrayList<>();
            String sql = sqlOfTables(database);
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                tables.add(parseTable(resultSet));
            }
            resultSet.close();
            statement.close();
            return tables;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * get the given table's metadata
     *
     * @param database
     * @param table
     * @return
     */
    @Override
    public Table table(String database, String table) {
        try {
            Table tableData = null;
            String sql = sqlOfTable(database, table);
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                tableData = parseTable(resultSet);
                break;
            }
            resultSet.close();
            statement.close();
            return tableData;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * parse table metadata from ResultSet
     *
     * @param resultSet
     * @return
     */
    abstract protected Table parseTable(ResultSet resultSet);

    /**
     * list indexes of table
     *
     * @param table
     * @return
     */
    @Override
    public List<TableIndex> indexes(String table) {
        try {
            List<TableIndex> tables = new ArrayList<>();
            String sql = sqlOfIndexes(table);
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            Map<String, TableIndex> indexMap = new HashMap<>();
            while (resultSet.next()) {
                TableIndex index = parseIndex(resultSet);
                if (indexMap.containsKey(index.getName())) {
                    indexMap.get(index.getName()).getFields().add(index.getField());
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(index.getField());
                    index.setFields(list);
                    indexMap.put(index.getName(), index);
                }
            }
            resultSet.close();
            statement.close();
            if (indexMap.isEmpty())
                return tables;
            for (Map.Entry<String, TableIndex> entry : indexMap.entrySet()) {
                tables.add(entry.getValue());
            }
            return tables;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * parse index metadata from ResultSet
     *
     * @param resultSet
     * @return
     */
    abstract protected TableIndex parseIndex(ResultSet resultSet);

    /**
     * list fields of table
     *
     * @param table
     * @return
     */
    @Override
    public List<TableField> fields(String table) {
        try {
            List<TableField> tables = new ArrayList<>();
            String sql = sqlOfFields(table);
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                TableField field = parseField(resultSet);
                if (null == field)
                    continue;
                tables.add(field);
            }
            resultSet.close();
            statement.close();
            return tables;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * to java field
     *
     * @param tableFields
     * @return
     */
    @Override
    public List<JavaField> toJavaFields(List<TableField> tableFields) {
        if (null == tableFields || tableFields.isEmpty())
            return null;
        List<JavaField> javaFields = new ArrayList<>(tableFields.size());
        for (TableField tableField : tableFields) {
            JavaField javaField = new JavaField(tableField);
            javaField.setFieldName(StringUtil.camelCase(tableField.getName()));
            Class clz = mapping(tableField.getType());
            javaField.setFieldType(clz.getSimpleName());
            javaField.setFieldTypeClz(clz);
            javaFields.add(javaField);
        }
        return javaFields;
    }

    /**
     * parse field metadata from ResultSet
     *
     * @param resultSet
     * @return
     */
    abstract protected TableField parseField(ResultSet resultSet);

    /**
     * close connection
     */
    @Override
    public void close() {
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {

            }
        }
    }
}
