package org.muzi.open.helper.service.convert;

import org.muzi.open.helper.util.EncodingUtil;

/**
 * @author: muzi
 * @time: 2019-06-04 22:27
 * @description:
 */
public class Base64Converter extends AbstractEncodeConverter {
    private boolean encode;

    public Base64Converter(boolean encode) {
        this.encode = encode;
    }

    @Override
    public String getOutput(String input, String[] params) throws Exception {
        String charset = getCharset(params);
        return encode ? EncodingUtil.base64Encode(input, charset) : EncodingUtil.base64Decode(input, charset);
    }
}
