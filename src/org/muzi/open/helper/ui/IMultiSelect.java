package org.muzi.open.helper.ui;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * @author: muzi
 * @time: 2018-05-24 20:06
 * @description:
 */
public interface IMultiSelect<T, R> {

    String getMultiSelectTitle();

    List<T> getMultiSelectOptions();

    void onMultiSelectResult(List<T> list);

    String getMultiSelectKey(T t);

    String getMultiSelectValue(T t);

    T build(JCheckBox checkBox);

    Map<String, R> getResultMap();
}
