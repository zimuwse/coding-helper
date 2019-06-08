package org.muzi.open.helper.test;

import org.muzi.open.helper.config.db.MysqlOperation;
import org.muzi.open.helper.model.db.Table;
import org.muzi.open.helper.model.db.TableField;
import org.muzi.open.helper.model.db.TableIndex;
import org.muzi.open.helper.model.java.JavaBean;
import org.muzi.open.helper.model.java.JavaField;
import org.muzi.open.helper.model.java.JavaMapper;
import org.muzi.open.helper.service.tplengine.velocity.VelocityTplEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: muzi
 * @time: 2019-06-07 17:45
 * @description:
 */
public class TestVelocityEngine {

    public static void main(String[] args) throws Exception {
        Table table = new Table();
        table.setName("api_user");
        table.setComment("用户表");
        List<JavaField> javaFields = build(new String[]{"id", "name", "date"}, new String[]{"int", "varchar", "timestamp"});
        List<TableIndex> indices = index();
        JavaBean javaBean = new JavaBean(table, "org.lr.demo", "api_", "Entity", javaFields, "muzi", "2019-06-07");
        //JavaMapper javaMapper = new JavaMapper(javaBean, "org.lr.mapper", "muzi", "2019-06-01", "api_", "Mapper", indices);
        //javaBean.setUseLombok(true);
        VelocityTplEngine engine = new VelocityTplEngine();
        String res = engine.render("/templates/vm/JavaBean.vm", javaBean);
        //String res2 = engine.render("/templates/vm/MybatisMapper.vm", javaMapper);
        System.out.println(res);
    }


    private static List<JavaField> build(String[] fields, String[] type) {
        List<TableField> tableFields = new ArrayList<>();
        int i = 0;
        for (String field : fields) {
            TableField tableField = new TableField();
            tableField.setName(field);
            tableField.setType(type[i]);
            tableField.setComment(field);
            tableField.setNotNull(true);
            tableField.setUnsigned(true);
            i++;
            tableFields.add(tableField);
        }
        MysqlOperation operation = new MysqlOperation(null);
        return operation.toJavaFields(tableFields);
    }

    private static List<TableIndex> index() {
        TableIndex index = new TableIndex("k_id", true, "id");
        TableIndex index2 = new TableIndex("k_name", false, "name");
        return Arrays.asList(index, index2);
    }

}
