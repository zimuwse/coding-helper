package org.muzi.open.helper.service.convert;

import org.muzi.open.helper.util.EncodingUtil;

/**
 * @author: muzi
 * @time: 2019-06-04 22:27
 * @description:
 */
public class URLConverter extends AbstractEncodeConverter {
    private boolean encode;

    public URLConverter(boolean encode) {
        this.encode = encode;
    }

    @Override
    public String getOutput(String input, String[] params) throws Exception {
        String charset = getCharset(params);
        return encode ? EncodingUtil.urlEncode(input, charset) : EncodingUtil.urlDecode(input, charset);
    }
}
