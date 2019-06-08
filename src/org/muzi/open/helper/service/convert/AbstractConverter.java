package org.muzi.open.helper.service.convert;

import org.muzi.open.helper.util.StringUtil;

/**
 * @author: muzi
 * @time: 2019-06-05 09:40
 * @description:
 */
public abstract class AbstractConverter implements IConverter {

    protected final int CHECK_PASS = -1;

    /**
     * get optional param names
     *
     * @return
     */
    @Override
    public String[] getOptionalParams() {
        return null;
    }

    /**
     * get optional param values
     *
     * @return
     */
    @Override
    public String[] getOptionalParamsValues() {
        return null;
    }

    /**
     * get optional param count
     *
     * @return
     */
    @Override
    public int getOptionalParamsCount() {
        if (null == getOptionalParams())
            return 0;
        return getOptionalParams().length;
    }

    /**
     * equal 0 means input err
     * grater than 0 means params err
     *
     * @param input
     * @param params
     * @return
     */
    @Override
    public int checkInput(String input, String[] params) {
        if (StringUtil.isEmpty(input))
            return 0;
        int checkInput = checkInput(input);
        if (CHECK_PASS != checkInput)
            return checkInput;
        int checkParams = checkParams(params);
        if (checkParams > 0)
            return checkParams;
        return CHECK_PASS;
    }

    protected int checkInput(String input) {
        return CHECK_PASS;
    }

    protected int checkParams(String[] params) {
        return CHECK_PASS;
    }

    protected String getParams(String[] params, int index) {
        if (null == params || params.length == 0 || params.length <= index)
            return getOptionalParamsValues()[index];
        return params[index];
    }
}
