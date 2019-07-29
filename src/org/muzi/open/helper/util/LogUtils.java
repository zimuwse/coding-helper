package org.muzi.open.helper.util;

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
        StringBuilder builder = new StringBuilder();
        String[] arr = tpl.split("\\{\\}");
        for (int i = 0; i < arr.length && i < args.length; i++) {
            builder.append(arr[i]).append(args[i]);
        }
        System.out.println(builder.toString());
    }
}
