package org.muzi.open.helper.model.java;

import org.muzi.open.helper.model.db.TableIndex;
import org.muzi.open.helper.util.JavaUtil;
import org.muzi.open.helper.util.StringUtil;

import java.util.*;

/**
 * @author: muzi
 * @time: 2018-05-18 14:30
 * @description:
 */
public class JavaMapper extends JavaClass {
    private JavaBean bean;
    private Map<String, List<JavaField>> indexMap;
    private Map<String, Boolean> indexType;
    private Set<MapperMethod> mapperMethods;


    public JavaMapper(JavaBean bean, String packageName, String author, String createTime, String prefixToRemove, String suffix, List<TableIndex> indexes) {
        this.bean = bean;
        setAuthor(author);
        setCreateTime(createTime);
        setPackageName(packageName);
        if (StringUtil.isEmpty(suffix))
            suffix = "Mapper";
        String name = StringUtil.removeHead(bean.getTable().getName(), prefixToRemove);
        setClzName(StringUtil.upperFirst(StringUtil.camelCase(name)) + suffix);
        initIndex(bean.getFields(), indexes);
        initImports(bean.getFields(), indexes);
        initMapperMethod();
    }

    private void initMapperMethod() {
        mapperMethods = new TreeSet<>(new Comparator<MapperMethod>() {
            @Override
            public int compare(MapperMethod o1, MapperMethod o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        //根据索引生成方法
        for (Map.Entry<String, List<JavaField>> entry : indexMap.entrySet()) {
            String indexName = entry.getKey();
            List<JavaField> list = entry.getValue();
            String name = getMethodName(list);
            boolean isUnique = indexType.get(indexName);
            String type = "List<" + bean.getClzName() + ">";
            if (isUnique) {
                type = bean.getClzName();
            }
            List<MapperParameter> parameters = new ArrayList<>(list.size());
            for (JavaField field : list) {
                MapperParameter parameter = new MapperParameter(field.getName(), field.getFieldName(), field.getFieldType(), field.getFieldName(), true, field.getComment());
                parameters.add(parameter);
            }
            MapperMethod selectMethod = new MapperMethod("select", name, type, parameters);
            mapperMethods.add(selectMethod);
        }
        MapperParameter byCondition = new MapperParameter("entity", "entity", bean.getClzName(), "entity", false, "table entity");
        mapperMethods.add(new MapperMethod("select", "getByCondition", "List<" + bean.getClzName() + ">", Collections.singletonList(byCondition)));
        MapperParameter insert = new MapperParameter("entity", "entity", bean.getClzName(), "entity", false, "table entity");
        mapperMethods.add(new MapperMethod("insert", "insert", "int", Collections.singletonList(insert)));
        MapperParameter updateById = new MapperParameter("id", "id", bean.getClzName(), "entity", false, "table entity");
        mapperMethods.add(new MapperMethod("update", "updateById", "int", Collections.singletonList(updateById)));

    }

    private String getMethodName(List<JavaField> list) {
        StringBuilder builder = new StringBuilder("getBy");
        for (JavaField field : list)
            builder.append(StringUtil.upperFirst(field.getFieldName())).append("And");
        builder.delete(builder.lastIndexOf("And"), builder.length());
        return builder.toString();
    }

    private void initIndex(List<JavaField> fields, List<TableIndex> indexes) {
        indexMap = new HashMap<>();
        indexType = new HashMap<>();
        for (TableIndex index : indexes) {
            indexType.put(index.getName(), index.isUnique());
            if (indexMap.containsKey(index.getName()))
                continue;
            List<JavaField> indexFields = new ArrayList<>();
            indexMap.put(index.getName(), indexFields);
            for (String fieldName : index.getFields()) {
                for (JavaField field : fields) {
                    if (field.getName().equals(fieldName)) {
                        indexFields.add(field);
                        continue;
                    }
                }
            }
        }

    }

    private void initImports(List<JavaField> fields, List<TableIndex> indexes) {
        Set<String> set = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        set.add(List.class.getName());
        set.add(bean.getPackageName() + "." + bean.getClzName());
        set.add("org.apache.ibatis.annotations.Param");
        for (Map.Entry<String, List<JavaField>> entry : indexMap.entrySet()) {
            for (JavaField field : entry.getValue()) {
                if (JavaUtil.needImport(field.getFieldTypeClz()))
                    set.add(field.getFieldTypeClz().getName());
            }
        }
        setImports(set);
    }

    public Map<String, List<JavaField>> getIndexMap() {
        return indexMap;
    }

    public Set<MapperMethod> getMapperMethods() {
        return mapperMethods;
    }

    public JavaBean getBean() {
        return bean;
    }
}
