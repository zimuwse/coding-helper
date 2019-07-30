package org.muzi.open.helper.ui;

import java.util.Map;

/**
 * @author: li.rui
 * @time: 2019-07-30 13:08
 * @description:
 */
public interface UIMapConfig<K, V> {

    void onUIMapConfigFinish(Map<K, V> map);

    Map<K, V> getDefaultConfig();
}
