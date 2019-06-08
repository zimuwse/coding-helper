package org.muzi.open.helper.service.tplengine;

/**
 * @author: muzi
 * @time: 2019-06-07 12:34
 * @description:
 */
public interface TplEngine {
    /**
     * render to string
     *
     * @param tpl  tpl path
     * @param data
     * @return
     */
    String render(String tpl, Object data) throws Exception;
}
