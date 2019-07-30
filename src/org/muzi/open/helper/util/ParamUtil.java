package org.muzi.open.helper.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author: zimuwse
 * @time: 2019-07-25 11:36
 * @description:
 */
public class ParamUtil {


    public static <T> boolean isInExpects(T src, T... expects) {
        if (null == src || expects.length == 0)
            return false;
        for (T exp : expects) {
            if (exp.equals(src))
                return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof CharSequence) {
            return ((String) obj).trim().length() == 0;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else {
            return false;
        }
    }
}
