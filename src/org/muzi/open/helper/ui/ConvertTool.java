package org.muzi.open.helper.ui;

import org.muzi.open.helper.service.convert.ConverterType;
import org.muzi.open.helper.service.convert.IConverter;
import org.muzi.open.helper.util.PopUtil;
import org.muzi.open.helper.util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author: muzi
 * @time: 2019-06-04 22:01
 * @description:
 */
public class ConvertTool extends BaseUI {
    private static final long serialVersionUID = 8373529941191667317L;
    private JPanel panel;
    private JTextArea txtInput;
    private JButton btnExecute;
    private JPanel jpanelParams;
    private JButton btnClearInput;
    private JButton btnCopyOutput;
    private JTextArea txtOutput;
    private JPanel jpanelExecuteType;
    private ButtonGroup buttonGroup;

    @Override
    protected JPanel panel() {
        return panel;
    }

    @Override
    protected void initUI() {
        super.initUI();
        setTitle("Common Convert&Transform Tools");
        buttonGroup = new ButtonGroup();
        ConverterType[] converterTypes = ConverterType.values();
        GridLayout layout = new GridLayout((int) Math.ceil(converterTypes.length / 3), 3);
        jpanelExecuteType.setLayout(layout);
        for (ConverterType converterType : converterTypes) {
            JRadioButton radio = new JRadioButton();
            radio.setText(converterType.getName());
            radio.setToolTipText(converterType.getDesc());
            radio.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jpanelParams.removeAll();
                    jpanelParams.updateUI();
                    ConverterType type = ConverterType.byName(e.getActionCommand());
                    IConverter converter = type.getConverter();
                    int paramsCount = converter.getOptionalParamsCount();
                    if (paramsCount > 0) {
                        GridLayout layout = new GridLayout((int) Math.ceil(paramsCount / 3), 3);
                        jpanelParams.setLayout(layout);
                        String[] optionalNames = converter.getOptionalParams();
                        String[] optionalValues = converter.getOptionalParamsValues();
                        int i = 0;
                        for (String name : optionalNames) {
                            JTextField field = new JTextField();
                            field.setToolTipText(name);
                            field.setText(optionalValues[i]);
                            jpanelParams.add(field);
                            i++;
                        }
                        jpanelParams.updateUI();
                    }
                }
            });
            buttonGroup.add(radio);
            jpanelExecuteType.add(radio);
        }
        jpanelExecuteType.updateUI();
    }

    @Override
    protected void initBind() {
        super.initBind();
        bindCopy(btnCopyOutput, txtOutput);
        btnClearInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtInput.setText("");
            }
        });

        txtOutput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    txtInput.setText(txtOutput.getText());
                }
            }
        });

        btnExecute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String convertName = getConvertType();
                if (StringUtil.isEmpty(convertName)) {
                    PopUtil.info(jpanelExecuteType, "please choose convert type.");
                    return;
                }
                ConverterType type = ConverterType.byName(convertName);
                IConverter converter = type.getConverter();
                String[] params = getParams();
                String input = txtInput.getText();
                int check = converter.checkInput(input, params);
                if (check == 0) {
                    PopUtil.info(txtInput, "input is illegal.");
                    return;
                }
                if (check > 0) {
                    int pos = check - 1;
                    String msg = "Param [" + converter.getOptionalParams()[pos] + "] is illegal.";
                    PopUtil.info(jpanelParams.getComponent(pos), msg);
                    return;
                }
                try {
                    txtOutput.setText(converter.getOutput(input, params));
                } catch (Exception e1) {
                    PopUtil.err(txtOutput, e1);
                }
            }
        });
    }

    private String[] getParams() {
        Component[] components = jpanelParams.getComponents();
        if (components.length == 0)
            return null;
        List<String> params = new ArrayList<>(components.length);
        for (Component component : components) {
            if (component instanceof JTextField) {
                params.add(((JTextField) component).getText());
            }
        }
        return params.toArray(new String[0]);
    }

    private String getConvertType() {
        Enumeration<AbstractButton> btns = buttonGroup.getElements();
        while (btns.hasMoreElements()) {
            AbstractButton btn = btns.nextElement();
            if (btn.isSelected())
                return btn.getActionCommand();
        }
        return null;
    }


}
