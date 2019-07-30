package org.muzi.open.helper.ui;

import org.muzi.open.helper.model.PopException;
import org.muzi.open.helper.model.java.JavaMethodType;
import org.muzi.open.helper.util.PopUtil;
import org.muzi.open.helper.util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: li.rui
 * @time: 2019-07-30 13:54
 * @description:
 */
public class MethodPrefixConfig extends BaseUI {
    private static final long serialVersionUID = 7582501590332487558L;
    private JPanel panel;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JPanel wrapper;
    private UIMapConfig<String, String> uiMapConfig;

    @Override
    protected JPanel panel() {
        return panel;
    }

    public MethodPrefixConfig(UIMapConfig<String, String> uiMapConfig) throws HeadlessException {
        this.uiMapConfig = uiMapConfig;
    }

    @Override
    protected void initUI() {
        super.initUI();
        GridLayout layout = new GridLayout(JavaMethodType.values().length, 2);
        wrapper.setLayout(layout);
        Map<String, String> map = uiMapConfig.getDefaultConfig();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            wrapper.add(new JLabel(entry.getKey() + " Method Prefix"));
            JTextField textField = new JTextField(entry.getValue());
            textField.setToolTipText(entry.getKey());
            wrapper.add(textField);
        }
    }

    @Override
    protected void initBind() {
        super.initBind();
        bindCancel(btnCancel, MethodPrefixConfig.this);

        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    doConfirm();
                } catch (Exception e1) {
                    PopUtil.err(btnConfirm, e1.getMessage());
                }
            }
        });
    }

    private void doConfirm() {
        try {
            Map<String, String> map = new HashMap<>();
            Component[] components = wrapper.getComponents();
            for (Component component : components) {
                if (component instanceof JTextField) {
                    JTextField text = (JTextField) component;
                    String prefix = text.getText();
                    if (StringUtil.isEmpty(prefix))
                        throw new PopException(text, "method prefix can't be empty");
                    map.put(text.getToolTipText(), text.getText());
                }
            }
            uiMapConfig.onUIMapConfigFinish(map);
            close();
        } catch (PopException e) {
            PopUtil.err(e.getComponent(), e.getMessage());
        }
    }

}
