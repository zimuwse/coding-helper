package org.muzi.open.helper;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.muzi.open.helper.ui.TestUI;

/**
 * @author: muzi
 * @time: 2018-05-18 17:49
 * @description:
 */
public class TestMainAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        new TestUI().show(400,400);
    }
}
