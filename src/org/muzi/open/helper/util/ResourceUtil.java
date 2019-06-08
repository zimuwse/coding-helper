package org.muzi.open.helper.util;

import org.apache.commons.io.IOUtils;

/**
 * @author: li.rui
 * @time: 2019-06-08 22:50
 * @description:
 */
public class ResourceUtil {
    public static String getResourceAsString(String location) throws Exception {
        return new String(IOUtils.toByteArray(ResourceUtil.class.getResourceAsStream(location)), "utf-8");
    }
}
