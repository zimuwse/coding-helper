package org.muzi.open.helper.model.java;

import org.muzi.open.helper.model.db.TableMethod;
import org.muzi.open.helper.util.StringUtil;

import java.util.*;

/**
 * @author: muzi
 * @time: 2018-05-18 14:30
 * @description:
 */
public class JavaMapper extends JavaClass {
    private JavaBean javaBean;
    private Set<MapperMethod> mapperMethods;
    private Set<TableMethod> tableMethods;


    public JavaMapper(TableToJavaPreference preference, JavaBean javaBean, Set<TableMethod> tableMethods) {
        this.javaBean = javaBean;
        this.tableMethods = tableMethods;
        setAuthor(preference.getAuthor());
        setCreateTime(preference.getCreateTime());
        setPackageName(preference.getMapperPackage());
        if (StringUtil.isEmpty(preference.getMapperSuffix()))
            preference.setMapperSuffix("Mapper");
        String name = StringUtil.removeHead(javaBean.getTable().getName(), preference.getTablePrefix());
        setClzName(StringUtil.upperFirst(StringUtil.camelCase(name)) + preference.getMapperSuffix());
        initImports();
        initMapperMethod();
    }

    private void initMapperMethod() {
        mapperMethods = new TreeSet<>(new Comparator<MapperMethod>() {
            @Override
            public int compare(MapperMethod o1, MapperMethod o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (TableMethod method : tableMethods) {
            JavaMethodType methodType = JavaMethodType.get(method.getMethodType());
            MapperMethod mapperMethod = null;
            switch (methodType) {
                case UPDATE:

                case DELETE:
                case INSERT:
            }
            List<MapperParameter> parameters = new ArrayList<>(method.getTableFields().size());
            for (String fieldName : method.getTableFields()) {
                //MapperParameter parameter = new MapperParameter(fieldName, StringUtil.camelCase(fieldName), );
            }

        }


    }

    private void initImports() {
        addImport(List.class.getName());
        addImport(javaBean.getPackageName() + "." + javaBean.getClzName());
        addImport("org.apache.ibatis.annotations.Param");
    }

    public Set<MapperMethod> getMapperMethods() {
        return mapperMethods;
    }

    public JavaBean getJavaBean() {
        return javaBean;
    }
}
