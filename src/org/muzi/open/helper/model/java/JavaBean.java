package org.muzi.open.helper.model.java;

import org.muzi.open.helper.model.db.Table;
import org.muzi.open.helper.util.JavaUtil;
import org.muzi.open.helper.util.StringUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author: muzi
 * @time: 2018-05-17 18:13
 * @description:
 */
public class JavaBean extends JavaClass {
    private Table table;
    private List<JavaField> fields;
    private boolean useLombok;


    public JavaBean(Table table, String packageName, String prefixToRemove, String beanNameSuffix, List<JavaField> fields, String author, String createTime) {
        this.fields = fields;
        this.table = table;
        if (StringUtil.isEmpty(beanNameSuffix))
            beanNameSuffix = "Entity";
        String name = StringUtil.removeHead(table.getName(), prefixToRemove);
        setClzName(StringUtil.upperFirst(StringUtil.camelCase(name)) + beanNameSuffix);
        Set<String> imports = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        for (JavaField field : fields) {
            if (JavaUtil.needImport(field.getFieldTypeClz())) {
                imports.add(field.getFieldTypeClz().getName());
            }
        }
        setImports(imports);
        setPackageName(packageName);
        setAuthor(author);
        setCreateTime(createTime);
    }

    public Table getTable() {
        return table;
    }

    public List<JavaField> getFields() {
        return fields;
    }

    public boolean getUseLombok() {
        return useLombok;
    }

    public void setUseLombok(boolean useLombok) {
        this.useLombok = useLombok;
    }
}
