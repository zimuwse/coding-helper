package org.muzi.open.helper.service.convert;

import org.muzi.open.helper.util.StringUtil;

/**
 * @author: muzi
 * @time: 2019-06-04 22:27
 * @description:
 */
public class CamelCaseConverter extends AbstractConverter {
    private boolean unCamel;

    public CamelCaseConverter(boolean unCamel) {
        this.unCamel = unCamel;
    }

    /**
     * get optional param names
     *
     * @return
     */
    @Override
    public String[] getOptionalParams() {
        //return unCamel ? new String[]{"line_separator", "connector"} : new String[0];
        return new String[]{"separator", "connector"};
    }

    /**
     * get optional param values
     *
     * @return
     */
    @Override
    public String[] getOptionalParamsValues() {
        //return unCamel ? new String[]{"\\n", "_"} : new String[0];
        return new String[]{"\\n", "_"};
    }

    @Override
    public String getOutput(String input, String[] params) {
        String separator = getParams(params, 0);
        String connector = getParams(params, 1);
        String[] arr = input.split(separator);
        StringBuilder builder = new StringBuilder();
        for (String s : arr) {
            if (unCamel) {
                builder.append(StringUtil.unCamel(s, connector)).append('\n');
            } else {
                builder.append(StringUtil.camelCase(s)).append('\n');
            }
        }
        return builder.toString();
    }
}
