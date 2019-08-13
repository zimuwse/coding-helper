package org.muzi.open.helper.model.java;

import org.muzi.open.helper.model.db.Table;
import org.muzi.open.helper.util.JavaUtil;
import org.muzi.open.helper.util.StringUtil;

import java.util.List;

/**
 * @author: muzi
 * @time: 2018-05-17 18:13
 * @description:
 */
public class JavaBean extends JavaClass {
    private Table table;
    private List<JavaField> fields;
    private boolean useLombok;
    private String camelName;

    public JavaBean(TableToJavaPreference preference, Table table, List<JavaField> fields) {
        this.fields = fields;
        this.table = table;
        if (StringUtil.isEmpty(preference.getBeanSuffix()))
            preference.setBeanSuffix("Entity");
        String name = StringUtil.removeHead(table.getName(), preference.getTablePrefix());
        this.camelName = StringUtil.camelCase(name);
        setClzName(StringUtil.upperFirst(this.camelName) + preference.getBeanSuffix());
        for (JavaField field : fields) {
            if (JavaUtil.needImport(field.getFieldTypeClz())) {
                addImport(field.getFieldTypeClz().getName());
            }
        }
        setPackageName(preference.getBeanPackage());
        setAuthor(preference.getAuthor());
        setCreateTime(preference.getCreateTime());
        this.useLombok = preference.isLombok();
        if (preference.isLombok()) {
            getImports().add("lombok.Data");
        }
    }

    public Table getTable() {
        return table;
    }

    public List<JavaField> getFields() {
        return fields;
    }

    public String getCamelName() {
        return camelName;
    }

    public boolean getUseLombok() {
        return useLombok;
    }
}
