package org.muzi.open.helper.model.java;

import java.util.Set;

/**
 * @author: li.rui
 * @time: 2019-08-02 17:47
 * @description:
 */
public class SpringControllerBean extends JavaClass {
    //Service or Controller
    //@Service
    //@RestController @GetMapping @PostMapping
    private String annotation;
    private Set<MapperMethod> methods;
    private JavaBean javaBean;
    private JavaMapper javaMapper;
    private SpringServiceBean service;
    private boolean setType;


    public SpringControllerBean(TableToJavaPreference preference, JavaBean javaBean, JavaMapper javaMapper, Set<MapperMethod> methods, SpringServiceBean service) {
        this.javaBean = javaBean;
        this.javaMapper = javaMapper;
        this.methods = methods;
        this.service = service;
        this.annotation = "@RestController\r\n" +
                "@RequestMapping(\"/" + javaBean.getCamelName() + "\")";
        addImport("org.springframework.beans.factory.annotation.Autowired");
        addImport("org.springframework.web.bind.annotation.GetMapping");
        addImport("org.springframework.web.bind.annotation.PostMapping");
        addImport("org.springframework.web.bind.annotation.RequestMapping");
        addImport("org.springframework.web.bind.annotation.RestController");
    }

    public Set<MapperMethod> getMethods() {
        return methods;
    }
}
