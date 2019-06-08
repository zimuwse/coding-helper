package org.muzi.open.helper.util;

/**
 * @author: muzi
 * @time: 2017-09-26 17:24
 * @description:
 */
public class FormatUtil {
    private static String SPACE = "   ";

    public static String formatJson(String json) {
        if (null == json)
            return "";
        StringBuilder result = new StringBuilder();
        int length = json.length();
        int number = 0;
        char key = 0;
        for (int i = 0; i < length; i++) {
            key = json.charAt(i);
            if ((key == '[') || (key == '{')) {
                if ((i - 1 > 0) && (json.charAt(i - 1) == ':')) {
                    result.append('\n');
                    result.append(indent(number));
                }
                result.append(key);
                result.append('\n');
                number++;
                result.append(indent(number));
                continue;
            }
            if ((key == ']') || (key == '}')) {
                result.append('\n');
                number--;
                result.append(indent(number));
                result.append(key);
                if (((i + 1) < length) && (json.charAt(i + 1) != ',')) {
                    result.append('\n');
                }
                continue;
            }
            if ((key == ',')) {
                result.append(key);
                result.append('\n');
                result.append(indent(number));
                continue;
            }
            result.append(key);
        }

        return result.toString();
    }

    private static String indent(int number) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            builder.append(SPACE);
        }
        return builder.toString();
    }
}
