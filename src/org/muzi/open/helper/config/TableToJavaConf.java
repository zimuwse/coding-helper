package org.muzi.open.helper.config;

import org.muzi.open.helper.model.java.TableToJavaPreference;

/**
 * @author: muzi
 * @time: 2018-05-24 10:06
 * @description:
 */
public class TableToJavaConf extends BaseConf {


    @Override
    public String namespace() {
        return "table-to-java.";
    }

    public TableToJavaPreference getTableToJavaPreference() {
        TableToJavaPreference preference = new TableToJavaPreference();
        process(preference, false);
        return preference;
    }

    public void saveTableToJavaPreference(TableToJavaPreference preference) {
        process(preference, true);
    }
}
