package org.muzi.open.helper.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * @author: muzi
 * @time: 2018-05-17 14:42
 * @description:
 */
public class StringUtil {

    /**
     * judge a string is empty or not
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return null == s || s.length() == 0 || s.trim().length() == 0;
    }

    /**
     * upper the first char of the string
     *
     * @param s
     * @return
     */
    public static String upperFirst(String s) {
        if (isEmpty(s))
            return null;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * lower the first char of the string
     *
     * @param s
     * @return
     */
    public static String lowerFirst(String s) {
        if (isEmpty(s))
            return null;
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }


    /**
     * camelCase
     *
     * @param s
     * @return
     */
    public static String camelCase(String s) {
        if (isEmpty(s))
            return null;
        String[] temp = s.split("_");
        if (temp.length == 1)
            return s;
        if (temp.length == 2)
            return temp[0] + upperFirst(temp[1]);
        StringBuilder builder = new StringBuilder(temp[0]);
        for (int i = 1; i < temp.length; i++)
            builder.append(upperFirst(temp[i]));
        return builder.toString();
    }

    /**
     * remove head chars of string that start with the head
     *
     * @param s
     * @param head
     * @return
     */
    public static String removeHead(String s, String head) {
        if (isEmpty(head))
            return s;
        if (s.startsWith(head))
            return s.substring(head.length());
        return s;
    }


    /**
     * is integer
     *
     * @param s
     * @return
     */
    public static boolean isInteger(String s) {
        if (isEmpty(s))
            return false;
        Pattern pattern = Pattern.compile("[\\d]+");
        return pattern.matcher(s).matches();
    }

    /**
     * remove camel case
     *
     * @param s
     * @param sep
     * @return
     */
    public static String removeCamelCase(String s, String sep) {
        if (null == s || s.length() == 0)
            return null;
        if (s.length() == 1)
            return s.toLowerCase();
        StringBuilder builder = new StringBuilder();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (Character.isUpperCase(ch)) {
                builder.append(sep).append(Character.toLowerCase(ch));
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    public static String join(String[] arr, String connector) {
        StringBuilder builder = new StringBuilder();
        for (String s : arr) {
            builder.append(s).append(connector);
        }
        builder.delete(builder.length() - connector.length(), builder.length());
        return builder.toString().trim();
    }

    /**
     * getOptionalParamsValues
     *
     * @param s
     * @return
     */
    public static String unCamel(String s, String sep) {
        if (isEmpty(s))
            return s;
        StringBuilder builder = new StringBuilder();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (Character.isLetter(ch)) {
                if (Character.isUpperCase(ch)) {
                    if (i > 0)
                        builder.append(sep);
                    builder.append(Character.toLowerCase(ch));
                } else {
                    builder.append(ch);
                }
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    public static String[] sort(String[] arr, final boolean asc) {
        Set<String> set = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return asc ? o1.compareTo(o2) : o2.compareTo(o1);
            }
        });
        set.addAll(Arrays.asList(arr));
        return set.toArray(new String[0]);
    }

    public static String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    public static <T> T fromJson(String json, Class<T> clz) {
        try {
            return new ObjectMapper().readValue(json, clz);
        } catch (IOException e) {
            return null;
        }
    }

}
