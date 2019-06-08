package org.muzi.open.helper.config.db;

import org.muzi.open.helper.model.db.Table;
import org.muzi.open.helper.model.db.TableField;
import org.muzi.open.helper.model.db.TableIndex;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: muzi
 * @time: 2018-05-17 15:04
 * @description:
 */
public class MysqlOperation extends AbstractDBOperation {

    private final static Pattern pattern = Pattern.compile("\\d+");

    public MysqlOperation(DBConfig config) {
        super(config);
    }

    @Override
    public String type() {
        return "MYSQL";
    }

    /**
     * driver class
     *
     * @return
     */
    @Override
    public String driverClassName() {
        return "com.mysql.jdbc.Driver";
    }

    /**
     * connection url
     *
     * @return
     */
    @Override
    public String url() {
        return "jdbc:mysql://" + config().getHost() + ":" + config().getPort() + "/" + config().getDbName() + "?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    }

    /**
     * sql to list tables of database
     *
     * @param database
     * @return
     */
    @Override
    public String sqlOfTables(String database) {
        return "SHOW TABLE STATUS";
    }

    /**
     * sql to get metadata of the given table
     *
     * @param database
     * @param table
     * @return
     */
    @Override
    public String sqlOfTable(String database, String table) {
        return "SHOW TABLE STATUS WHERE NAME= '" + table + "'";
    }

    /**
     * sql to list fields of table
     *
     * @param table
     * @return
     */
    @Override
    public String sqlOfFields(String table) {
        return "SHOW FULL FIELDS FROM " + table;
    }

    /**
     * sql to list indexes of table
     *
     * @param table
     * @return
     */
    @Override
    public String sqlOfIndexes(String table) {
        return "SHOW INDEX FROM " + table;
    }

    /**
     * parse table metadata from ResultSet
     *
     * @param resultSet
     * @return
     */
    @Override
    protected Table parseTable(ResultSet resultSet) {
        try {
            String name = resultSet.getString("Name");
            String comment = resultSet.getString("Comment");
            if (null != comment)
                comment = comment.replaceAll("\r|\n", "");
            return new Table(name, comment);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * parse index metadata from ResultSet
     *
     * @param resultSet
     * @return
     */
    @Override
    protected TableIndex parseIndex(ResultSet resultSet) {
        try {
            String name = resultSet.getString("Key_name");
            boolean unique = "0".equals(resultSet.getString("Non_unique"));
            String field = resultSet.getString("Column_name");
            return new TableIndex(name, unique, field);
        } catch (Exception e) {
            return null;
        }
    }

    private int getLen(String type) {
        try {
            Matcher matcher = pattern.matcher(type);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group());
            }
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    /**
     * parse field metadata from ResultSet
     *
     * @param resultSet
     * @return
     */
    @Override
    protected TableField parseField(ResultSet resultSet) {
        try {
            TableField field = new TableField();
            field.setName(resultSet.getString("Field"));
            field.setType(resultSet.getString("Type"));

            field.setLength(getLen(field.getType()));
            field.setNotNull("NO".equals(resultSet.getString("Null")));
            field.setDefaultValue(resultSet.getString("Default"));
            field.setExtra(resultSet.getString("Extra"));
            field.setComment(resultSet.getString("Comment"));
            return field;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Class mapping(String type) {
        if (null == type)
            return String.class;
        if (type.startsWith("bigint"))
            return Long.class;
        if (type.contains("int"))
            return Integer.class;
        if (type.startsWith("float") || type.startsWith("double") || type.startsWith("decimal"))
            return BigDecimal.class;
        if (type.startsWith("date") || type.startsWith("year"))
            return Date.class;
        if (type.startsWith("timestamp"))
            return Timestamp.class;
        return String.class;
    }
}
