package org.muzi.open.helper.ui;

import javax.swing.*;

/**
 * @author: muzi
 * @time: 2018-05-25 11:38
 * @description:
 */
public class Progress extends BaseUI {
    private static final long serialVersionUID = 5031438913781965243L;
    private JPanel panel;
    private JProgressBar progress;
    private JTextArea txtOutput;
    private JScrollPane jsp;

    @Override
    protected JPanel panel() {
        return panel;
    }

    public Progress(String title) {
        setTitle(title);
    }

    public void append(String s) {
        txtOutput.append(s + "\n");
        JScrollBar bar = jsp.getVerticalScrollBar();
        bar.setValue(bar.getMaximum());
    }

    public void updateProgress(int val, String text) {
        progress.setString(text);
        progress.setValue(val);
    }

    public void changeTitle(String title) {
        setTitle(title);
    }

    public void finish(long time) {
        append("[TASK FINISHED IN " + time + " ms]");
        /*
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                close();
            }
        }, 5000);
        */
    }
}
