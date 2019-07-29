package org.muzi.open.helper.util;

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
}
