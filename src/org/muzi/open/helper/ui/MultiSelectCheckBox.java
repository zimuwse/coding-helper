package org.muzi.open.helper.ui;

import org.muzi.open.helper.util.PopUtil;
import org.muzi.open.helper.util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: muzi
 * @time: 2018-05-24 19:43
 * @description:
 */
public class MultiSelectCheckBox<T> extends BaseUI {
    private static final long serialVersionUID = 1965460912074016683L;
    private JPanel panel;
    private JPanel wrapper;
    private JScrollPane jsp;
    private JTextField txtFilter;
    private JButton btnAll;
    private JButton btnReverse;
    private JButton btnSure;
    private JButton btnCancel;
    private IMultiSelect<T> select;
    private List<T> options;


    public MultiSelectCheckBox(IMultiSelect<T> select) {
        this.select = select;
        this.setTitle(select.getMultiSelectTitle());
        this.options = select.getMultiSelectOptions();
    }

    @Override
    protected JPanel panel() {
        return panel;
    }

    @Override
    protected void initUI() {
        super.initUI();
        paintOptions(options);
    }

    @Override
    protected void initBind() {
        super.initBind();
        txtFilter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    doFilter();
            }
        });

        btnAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAll(false);
            }
        });
        btnReverse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAll(true);
            }
        });
        btnSure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doConfirm();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
    }

    private void doFilter() {
        paintOptions(filter(txtFilter.getText()));
    }

    private void doAll(boolean reverse) {
        if (null == options)
            return;
        Component[] components = wrapper.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (reverse)
                    checkBox.setSelected(!checkBox.isSelected());
                else
                    checkBox.setSelected(true);
            }
        }
    }

    private void doConfirm() {
        if (null == options) {
            return;
        }
        List<T> list = new ArrayList<>();
        Component[] components = wrapper.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    list.add(select.build(checkBox));
                }
            }
        }
        if (list.isEmpty()) {
            PopUtil.info(btnSure, "no item selected");
        } else {
            select.onMultiSelectResult(list);
            close();
        }
    }

    private void paintOptions(List<T> list) {
        if (null == list || list.isEmpty())
            return;
        wrapper.removeAll();
        int size = list.size();
        GridLayout layout = new GridLayout(size, 1);
        wrapper.setLayout(layout);
        for (T t : list) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setText(select.getMultiSelectKey(t));
            checkBox.setToolTipText(select.getMultiSelectValue(t));
            wrapper.add(checkBox);
        }
        jsp.getVerticalScrollBar().setBlockIncrement(10);
        jsp.getVerticalScrollBar().setUnitIncrement(10);
        jsp.getVerticalScrollBar().setValue(0);
        jsp.updateUI();
    }

    private List<T> filter(String filter) {
        if (StringUtil.isEmpty(filter))
            return options;
        List<T> filterList = new ArrayList<>();
        for (T t : options) {
            if (select.getMultiSelectKey(t).contains(filter) || select.getMultiSelectValue(t).contains(filter))
                filterList.add(t);
        }
        return filterList;
    }


}
