package org.muzi.open.helper.service.convert;

import org.muzi.open.helper.util.EncodingUtil;

/**
 * @author: muzi
 * @time: 2019-06-04 22:27
 * @description:
 */
public class UnicodeConverter extends AbstractConverter {
    private boolean encode;

    public UnicodeConverter(boolean encode) {
        this.encode = encode;
    }

    @Override
    public String getOutput(String input, String[] params) {
        return encode ? EncodingUtil.toUnicode(input) : EncodingUtil.decodeUnicode(input);
    }
}
