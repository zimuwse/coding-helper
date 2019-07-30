package org.muzi.open.helper.ui;

/**
 * @author: li.rui
 * @time: 2019-07-29 20:48
 * @description:
 */
public interface MapUIResult<K, V> {
    void addMapUIResult(K key, V val);

    V getMapUIResult(K key);
}
