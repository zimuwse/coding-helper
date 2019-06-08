package org.muzi.open.helper.service.convert;

import org.muzi.open.helper.util.MD5Util;

/**
 * @author: muzi
 * @time: 2019-06-04 22:27
 * @description:
 */
public class MD5Converter extends AbstractEncodeConverter {

    @Override
    public String getOutput(String input, String[] params) throws Exception {
        String charset = getCharset(params);
        return MD5Util.md5(input, charset);
    }
}
