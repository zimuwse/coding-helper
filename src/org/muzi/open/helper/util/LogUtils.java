package org.muzi.open.helper.util;

import org.muzi.open.helper.config.BaseConf;

import java.util.Collection;
import java.util.Map;

/**
 * @author: muzi
 * @time: 2019-07-29 18:14
 * @description:
 */
public class LogUtils {
    /**
     * log
     *
     * @param tpl  use {} as placeholder
     * @param args
     */
    public static void log(String tpl, Object... args) {
        if (!BaseConf.isDebug())
            return;
        StringBuilder builder = new StringBuilder();
        String[] arr = tpl.split("\\{\\}");
        if (arr.length == 1) {
            System.out.println(tpl);
            return;
        }
        for (int i = 0; i < arr.length && i < args.length; i++) {
            builder.append(arr[i]);
            Object arg = args[i];
            if (arg instanceof Collection) {
                builder.append(StringUtil.toJson(arg));
            } else if (arg instanceof Map) {
                builder.append(StringUtil.toJson(arg));
            } else {
                builder.append(arg);
            }
        }
        System.out.println(builder.toString());
    }
}
