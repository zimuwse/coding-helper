package org.muzi.open.helper.ui;

import com.intellij.ide.util.BrowseFilesListener;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author: li.rui
 * @time: 2019-06-18 20:08
 * @description:
 */
public class TestUI extends BaseUI {
    private JTextField textField1;
    private JButton button1;
    private JPanel panel;
    private JButton button2;
    private JTextField textField2;
    private JTextField textField3;

    @Override
    protected JPanel panel() {
        return panel;
    }

    @Override
    protected void initBind() {
        FileChooserDescriptor fileChooserDescriptor1 = new FileChooserDescriptor(true, true, true, true, true, true);
        FileChooserDescriptor fileChooserDescriptor2 = new FileChooserDescriptor(true, true, true, false, false, false);
        BrowseFilesListener browseFilesListener1 = new BrowseFilesListener(textField1, "title1", "description1", fileChooserDescriptor1);
        BrowseFilesListener browseFilesListener2 = new BrowseFilesListener(textField1, "title2", "description2", fileChooserDescriptor2);
        button1.addActionListener(browseFilesListener1);
        button2.addActionListener(browseFilesListener2);

        textField1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    browseFilesListener1.actionPerformed(null);
                }
            }
        });

        textField2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    browseFilesListener2.actionPerformed(null);
                }
            }
        });

        textField3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    browseFilesListener2.actionPerformed(null);
                }
            }
        });

    }
}
