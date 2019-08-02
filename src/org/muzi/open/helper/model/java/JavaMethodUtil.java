package org.muzi.open.helper.model.java;

import org.muzi.open.helper.util.StringUtil;

/**
 * @author: li.rui
 * @time: 2019-07-25 20:56
 * @description:
 */
public class JavaMethodUtil {
    public static String getMethodPrefix(TableToJavaPreference preference, JavaMethodType methodType) {
        String method = getPreferenceMethodPrefix(preference, methodType);
        return StringUtil.isEmpty(method) ? methodType.getPrefix() : method;
    }


    public static String getPreferenceMethodPrefix(TableToJavaPreference preference, JavaMethodType methodType) {
        if (null == preference)
            return null;
        switch (methodType) {
            case PAGE_QUERY:
                return preference.getMethodPageQuery();
            case INSERT_BATCH:
                return preference.getMethodInsertBatch();
            case INSERT:
                return preference.getMethodInsert();
            case SELECT_LIST:
                return preference.getMethodQueryList();
            case SELECT_ONE:
                return preference.getMethodQueryOne();
            case DELETE:
                return preference.getMethodDelete();
            case UPDATE:
                return preference.getMethodUpdate();
        }
        return null;
    }
}
