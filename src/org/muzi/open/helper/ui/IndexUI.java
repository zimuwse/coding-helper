package org.muzi.open.helper.ui;

import com.intellij.openapi.project.Project;
import org.muzi.open.helper.util.CmdUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author: muzi
 * @time: 2018-05-18 18:40
 * @description:
 */
public class IndexUI extends BaseUI {
    private static final long serialVersionUID = -7677010821627993463L;
    private JPanel panel;
    private JButton btnDB2Java;
    private JLabel txtLinkToGitHub;
    private JButton btnConvertTool;
    private JButton btnComingSoon;

    public IndexUI() throws HeadlessException {
    }

    public IndexUI(Project project) throws HeadlessException {
        super(project);
    }

    @Override
    protected JPanel panel() {
        return panel;
    }

    @Override
    protected void initUI() {
        this.setTitle("Coding Helper");
    }

    @Override
    protected void initBind() {
        btnDB2Java.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableToJava(project()).show(700, 700);
            }
        });

        btnConvertTool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConvertTool().show(660, 600);
            }
        });
        txtLinkToGitHub.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CmdUtil.openBrowser("https://github.com/zimuwse");
            }
        });
    }
}
