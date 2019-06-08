package org.muzi.open.helper.service.convert;

/**
 * @author: muzi
 * @time: 2019-06-04 20:45
 * @description:
 */
public interface IConverter {
    /**
     * get optional param names
     *
     * @return
     */
    String[] getOptionalParams();

    /**
     * get optional param values
     *
     * @return
     */
    String[] getOptionalParamsValues();

    /**
     * get optional param count
     *
     * @return
     */
    int getOptionalParamsCount();

    /**
     * equal 0 means input err
     * grater than 0 means params err
     *
     * @param input
     * @param params
     * @return
     */
    int checkInput(String input, String[] params);

    /**
     * get convert output
     *
     * @param input
     * @param params
     * @return
     */
    String getOutput(String input, String[] params) throws Exception;


}
