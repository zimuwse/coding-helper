package org.muzi.open.helper.config;

import com.intellij.ide.util.PropertiesComponent;
import org.muzi.open.helper.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: muzi
 * @time: 2018-05-24 10:01
 * @description:
 */
public class BaseConf {

    private final static String prefix = "app.";

    public String namespace() {
        return "common.";
    }

    private String key(String key) {
        return prefix + namespace() + key;
    }

    public String get(String key) {
        return Conf.get(key(key));
    }

    public void set(String key, String val) {
        Conf.set(key(key), val);
    }

    public int readInt(String key) {
        String val = get(key);
        if (null == val || "".equals(val))
            return 0;
        return Integer.parseInt(val);
    }

    public void saveInt(String key, int val) {
        set(key, String.valueOf(val));
    }

    public void saveBool(String key, boolean val) {
        set(key, val ? "1" : "0");
    }

    public boolean readBool(String key) {
        return "1".equals(get(key));
    }

    public static boolean isDebug() {
        return Conf.isDebug();
    }

    public static void print() {
        if (isDebug())
            for (Map.Entry<String, String> entry : Conf.map.entrySet()) {
                System.out.println(entry.getKey() + " => " + entry.getValue());
            }
    }

    static class Conf {
        private static PropertiesComponent component;

        private static Map<String, String> map;

        private static boolean DEBUG = true;

        static {
            if (isDebug())
                map = new HashMap<>();
            else
                component = PropertiesComponent.getInstance();
        }

        static boolean isDebug() {
            return DEBUG;
        }

        static String get(String key) {
            if (DEBUG)
                return map.get(key);
            else
                return component.getValue(key);
        }

        static void set(String key, String value) {
            if (DEBUG)
                map.put(key, value);
            else
                component.setValue(key, value);
        }
    }

    /**
     * process conf
     *
     * @param obj
     * @param save
     * @return
     */
    protected void process(Object obj, boolean save) {
        try {
            Class clz = obj.getClass();
            for (; clz != Object.class; clz = clz.getSuperclass()) {
                if (clz.isAnnotationPresent(PersistableConfig.class)) {
                    Field[] fields = clz.getDeclaredFields();
                    for (Field field : fields) {
                        String key = "";
                        if (field.isAnnotationPresent(PersistableConfig.class)) {
                            PersistableConfig config = field.getAnnotation(PersistableConfig.class);
                            if (config.skip())
                                continue;
                            key = config.key();
                        }
                        String name = field.getName();
                        if ("".equals(key)) {
                            key = StringUtil.removeCamelCase(name, ".");
                        }
                        Class type = field.getType();
                        String typeName = type.getSimpleName();
                        if (save) {
                            Method method = null;
                            if ("boolean".equals(typeName)) {
                                method = clz.getMethod("is" + StringUtil.upperFirst(name));
                            } else {
                                method = clz.getMethod("get" + StringUtil.upperFirst(name));
                            }
                            if ("int".equals(typeName)) {
                                saveInt(key, (int) method.invoke(obj));
                            } else if ("boolean".equals(typeName)) {
                                Object result = method.invoke(obj);
                                if (null != result)
                                    saveBool(key, (boolean) result);
                            } else if ("String".equals(typeName)) {
                                Object result = method.invoke(obj);
                                if (null != result)
                                    set(key, result.toString());
                            }
                        } else {
                            Method method = clz.getMethod("set" + StringUtil.upperFirst(name), type);
                            if ("int".equals(typeName)) {
                                method.invoke(obj, readInt(key));
                            } else if ("boolean".equals(typeName)) {
                                method.invoke(obj, readBool(key));
                            } else if ("String".equals(typeName)) {
                                method.invoke(obj, get(key));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
