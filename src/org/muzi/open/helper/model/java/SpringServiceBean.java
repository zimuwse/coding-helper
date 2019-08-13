package org.muzi.open.helper.model.java;

import org.muzi.open.helper.util.StringUtil;

import java.util.Set;

/**
 * @author: li.rui
 * @time: 2019-08-02 17:47
 * @description:
 */
public class SpringServiceBean extends JavaClass {
    //Service or Controller
    //@Service
    //@RestController @GetMapping @PostMapping
    private String annotation;
    private Set<MapperMethod> methods;
    private JavaBean javaBean;
    private JavaMapper javaMapper;
    private boolean setType;
    private String mapperName;
    private String mapperFieldName;
    private String comment;
    private boolean lombokLog;


    public SpringServiceBean(TableToJavaPreference preference, JavaBean javaBean, JavaMapper javaMapper, Set<MapperMethod> methods) {
        this.javaBean = javaBean;
        this.javaMapper = javaMapper;
        this.methods = methods;
        this.annotation = "@Service";
        this.mapperName = javaMapper.getClzName();
        this.mapperFieldName = StringUtil.lowerFirst(javaMapper.getClzName());
        this.lombokLog = preference.isLombokLog();
        if (lombokLog){
            this.annotation = "@Slf4j\r\n@Service";
        }
        this.comment = javaBean.getTable().getComment();
        setClzName(javaBean.getCamelName() + preference.getSpringServiceSuffix());
        addImports(javaMapper.getImports());
        addImport(javaMapper.getPackageName() + '.' + javaMapper.getClzName());
        addImport("org.springframework.beans.factory.annotation.Autowired");
        addImport("org.springframework.stereotype.Service");
    }

    public Set<MapperMethod> getMethods() {
        return methods;
    }

    public String getMapperName() {
        return mapperName;
    }

    public String getMapperFieldName() {
        return mapperFieldName;
    }

    public String getComment() {
        return comment;
    }

    public boolean isLombokLog() {
        return lombokLog;
    }
}
