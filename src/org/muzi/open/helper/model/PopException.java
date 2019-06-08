package org.muzi.open.helper.model;

import javax.swing.*;

/**
 * @author: muzi
 * @time: 2018-05-24 11:24
 * @description:
 */
public class PopException extends RuntimeException {

    private static final long serialVersionUID = 5307943297663223666L;
    private JComponent component;

    public PopException(JComponent component, String message) {
        super(message);
        this.component = component;
    }

    public JComponent getComponent() {
        return component;
    }
}
