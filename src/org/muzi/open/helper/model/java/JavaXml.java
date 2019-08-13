package org.muzi.open.helper.model.java;

import org.muzi.open.helper.util.StringUtil;

import java.util.List;
import java.util.Set;

/**
 * @author: muzi
 * @time: 2018-05-23 11:24
 * @description:
 */
public class JavaXml {

    private JavaMapper mapper;

    public JavaXml(JavaMapper mapper) {
        this.mapper = mapper;

    }

    public String getMapperName() {
        return mapper.getPackageName() + "." + mapper.getClzName();
    }

    public String getBeanName() {
        return mapper.getJavaBean().getPackageName() + "." + mapper.getJavaBean().getClzName();
    }

    public String getMapName() {
        return StringUtil.lowerFirst(mapper.getJavaBean().getClzName()) + "Map";
    }

    public List<JavaField> getJavaFields() {
        return mapper.getJavaBean().getFields();
    }

    public Set<MapperMethod> getMapperMethods() {
        return mapper.getMapperMethods();
    }

    public String getTableName() {
        return mapper.getJavaBean().getTable().getName();
    }

}
