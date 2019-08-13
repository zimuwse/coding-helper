package org.muzi.open.helper.model.java;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author: muzi
 * @time: 2018-05-18 14:34
 * @description:
 */
public class JavaClass {
    private String author;
    private String createTime;
    private String packageName;
    private String clzName;
    private Set<String> imports = new TreeSet<>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    });
    private String parentClzName;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClzName() {
        return clzName;
    }

    public void setClzName(String clzName) {
        this.clzName = clzName;
    }

    public Set<String> getImports() {
        return imports;
    }

    public void addImports(Set<String> imports) {
        this.imports.addAll(imports);
    }

    public void addImport(String _import) {
        this.imports.add(_import);
    }

    public String getParentClzName() {
        return parentClzName;
    }

    public void setParentClzName(String parentClzName) {
        this.parentClzName = parentClzName;
    }
}
