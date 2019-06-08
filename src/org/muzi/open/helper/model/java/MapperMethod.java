package org.muzi.open.helper.model.java;

import java.util.List;

/**
 * @author: muzi
 * @time: 2018-05-22 19:30
 * @description:
 */
public class MapperMethod {
    private String type;
    private String name;
    private String returnType;
    private List<MapperParameter> parameters;

    public MapperMethod(String type, String name, String returnType, List<MapperParameter> parameters) {
        this.type = type;
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getReturnType() {
        return returnType;
    }

    public List<MapperParameter> getParameters() {
        return parameters;
    }
}
