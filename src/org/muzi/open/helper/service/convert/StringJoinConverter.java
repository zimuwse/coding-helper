package org.muzi.open.helper.service.convert;

import org.muzi.open.helper.util.StringUtil;

/**
 * @author: muzi
 * @time: 2019-06-04 22:27
 * @description:
 */
public class StringJoinConverter extends AbstractConverter {

    /**
     * 获取可选参数列表
     *
     * @return
     */
    @Override
    public String[] getOptionalParams() {
        return new String[]{"separator", "connector"};
    }

    /**
     * get optional param values
     *
     * @return
     */
    @Override
    public String[] getOptionalParamsValues() {
        return new String[]{"\\n", ","};
    }

    @Override
    public String getOutput(String input, String[] params) {
        String separator = getParams(params, 0);
        String connector = getParams(params, 1);
        return StringUtil.join(input.split(separator), connector);
    }
}
