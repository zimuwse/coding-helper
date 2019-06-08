package org.muzi.open.helper.service.convert;

import org.muzi.open.helper.util.FormatUtil;

/**
 * @author: muzi
 * @time: 2019-06-04 22:27
 * @description:
 */
public class JsonFormatConverter extends AbstractConverter {

    @Override
    public String getOutput(String input, String[] params) throws Exception {
        return FormatUtil.formatJson(input);
    }
}
