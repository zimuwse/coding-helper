package org.muzi.open.helper.test;

import org.muzi.open.helper.model.java.JavaMethodType;
import org.muzi.open.helper.ui.MethodPrefixConfig;
import org.muzi.open.helper.ui.UIMapConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: li.rui
 * @time: 2019-07-23 23:21
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        /*TableToJavaPreference preference = new TableToJavaPreference();
        preference.setDriverType("MYSQL-DEBUG");
        preference.setMethodDelete("deleteBy");
        preference.setMethodQueryOne("getBy");
        preference.setMethodQueryList("getListBy");
        preference.setMethodInsert("add");
        //new ChooseMethod(preference, "order").show(600, 600);
        new DatabaseTableList(preference, null).show(600, 600);*/


        new MethodPrefixConfig(new UIMapConfig<String, String>() {
            @Override
            public void onUIMapConfigFinish(Map<String, String> map) {

            }

            @Override
            public Map<String, String> getDefaultConfig() {
                Map<String, String> map = new HashMap<>();
                for (JavaMethodType javaMethodType : JavaMethodType.values()) {
                    map.put(javaMethodType.name(), javaMethodType.getPrefix());
                }
                return map;
            }
        }).show(400, 210);
    }
}
