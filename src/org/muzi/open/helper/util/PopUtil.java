package org.muzi.open.helper.util;

import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.util.PopupUtil;
import org.muzi.open.helper.config.BaseConf;
import org.muzi.open.helper.model.PopException;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author: zimuwse
 * @time: 2018-01-22 14:19
 * @description:
 */
public class PopUtil {

    public static void pop(Exception e) {
        if (e instanceof PopException) {
            err(((PopException) e).getComponent(), e);
        } else {
            err(e.getMessage());
        }
    }

    public static void err(String msg) {
        if (BaseConf.isDebug())
            print(msg);
        else
            PopupUtil.showBalloonForActiveComponent(msg, MessageType.ERROR);
    }

    public static void info(String msg) {
        if (BaseConf.isDebug())
            print(msg);
        else
            PopupUtil.showBalloonForActiveComponent(msg, MessageType.INFO);
    }

    public static void err(Component component, String msg) {
        pop(component, msg, "error");
    }

    public static void err(Component component, Throwable throwable) {
        err(component, getStackErrMsg(throwable));
    }

    public static void info(Component component, String msg) {
        pop(component, msg, "info");
    }


    private static void pop(Component component, String msg, String type) {
        MessageType messageType = MessageType.ERROR;
        if ("info".equals(type)) {
            messageType = MessageType.INFO;
        } else if ("warning".equals(type)) {
            messageType = MessageType.WARNING;
        }
        if (BaseConf.isDebug()) {
            if (component instanceof JButton) {
                System.out.println("[Comp:" + JButton.class.getSimpleName() + ",type:" + type + "]:" + msg);
            } else if (component instanceof JTextField) {
                System.out.println("[Comp:" + JTextField.class.getSimpleName() + ",type:" + type + "]:" + msg);
            } else if (component instanceof JTextArea) {
                System.out.println("[Comp:" + JTextArea.class.getSimpleName() + ",type:" + type + "]:" + msg);
            } else {
                System.out.println("[Comp:" + component.getClass().getSimpleName() + ",type:" + type + "]:" + msg);
            }
        } else {
            PopupUtil.showBalloonForComponent(component, msg, messageType, true, null);
        }
    }

    private static String getStackErrMsg(Throwable throwable) {
        return getStackErrMsg(throwable, 3);
    }

    public static String getStackErrMsg(Throwable throwable, int depth) {
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer));
        String[] errs = writer.toString().split("\\n\\t");
        StringBuilder sb = new StringBuilder();
        String arrow = " -> ";
        int i = 0;
        for (String s : errs) {
            if (i > depth)
                break;
            sb.append(s).append(" -> ");
            i++;
        }
        sb.delete(sb.length() - arrow.length(), sb.length());
        return sb.toString();
    }

    private static void print(String msg) {
        System.out.println(msg);
    }
}
