package org.muzi.open.helper.test;

import org.muzi.open.helper.model.java.TableToJavaPreference;
import org.muzi.open.helper.ui.DatabaseTableList;

/**
 * @author: li.rui
 * @time: 2019-07-23 23:21
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        TableToJavaPreference preference = new TableToJavaPreference();
        preference.setDriverType("MYSQL-DEBUG");
        preference.setMethodDelete("deleteBy");
        preference.setMethodQueryOne("getBy");
        preference.setMethodQueryList("getListBy");
        preference.setMethodInsert("add");
        //new ChooseMethod(preference, "order").show(600, 600);
        new DatabaseTableList(preference).show(600, 600);
    }
}
