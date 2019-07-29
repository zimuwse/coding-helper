package org.muzi.open.helper.config.db;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: muzi
 * @time: 2018-05-22 17:39
 * @description:
 */
public class DBTypeConfig {

    private static Map<String, Class> map = new HashMap<>();

    static {
        map.put("MYSQL", MysqlOperation.class);
        map.put("MYSQL-DEBUG", DebugMysqlOperation.class);
    }

    public static DBOperation getInstance(DBConfig config) throws Exception {
        Class clz = map.get(config.getDriverType());
        if (null == clz)
            throw new RuntimeException("no database handler found.");
        Constructor constructor = clz.getConstructor(DBConfig.class);
        return (DBOperation) constructor.newInstance(config);
    }
}
