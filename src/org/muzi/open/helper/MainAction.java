package org.muzi.open.helper;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.SystemIndependent;
import org.muzi.open.helper.config.BaseConf;
import org.muzi.open.helper.ui.IndexUI;
import org.picocontainer.PicoContainer;

/**
 * @author: muzi
 * @time: 2018-05-18 17:49
 * @description:
 */
public class MainAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        new IndexUI(e.getProject()).show(200, 200);
    }
}
