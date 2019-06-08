package org.muzi.open.helper.service.convert;

/**
 * @author: muzi
 * @time: 2019-06-05 15:48
 * @description:
 */
public abstract class AbstractEncodeConverter extends AbstractConverter {

    @Override
    public String[] getOptionalParams() {
        return new String[]{"charset"};
    }

    /**
     * get optional param values
     *
     * @return
     */
    @Override
    public String[] getOptionalParamsValues() {
        return new String[]{"utf-8"};
    }

    protected String getCharset(String[] params) {
        return getParams(params, 0);
    }
}
