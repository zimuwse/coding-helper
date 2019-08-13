package org.muzi.open.helper.model.java;

import org.muzi.open.helper.model.db.TableMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: li.rui
 * @time: 2019-08-02 19:59
 * @description:
 */
public class JavaMethod extends TableMethod {
    private String returnType;
    private List<JavaParameter> parameters;

    public JavaMethod(JavaBean javaBean, TableMethod tableMethod) {
        super(tableMethod);
        JavaMethodType methodType = JavaMethodType.get(tableMethod.getMethodType());
        initMethodType(methodType, javaBean, tableMethod);
        this.parameters = null == tableMethod.getTableFields() ? null : new ArrayList<>(tableMethod.getTableFields().size());
        //initParams();
        
    }


    private void initMethodType(JavaMethodType methodType, JavaBean javaBean, TableMethod tableMethod) {
        switch (methodType) {
            case INSERT:
            case DELETE:
            case INSERT_BATCH:
            case UPDATE:
                returnType = "int";
                return;
            case SELECT_LIST:
            case PAGE_QUERY:
                returnType = "List<" + javaBean.getClzName() + ">";
                return;
            case SELECT_ONE:
                returnType = javaBean.getClzName();
                return;
        }
    }
}
