package com.woowahan.framework.web;

import java.lang.reflect.Method;

/**
 * PathVariable 에 대한 정의
 *
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class PathVariableModel {
    private Integer parameterIndex;
    private Method method;
    private String variableName;
    private Class<?> variableClass;
    private Integer variableIndexOnUrl;

    public PathVariableModel(Integer parameterIndex, Method method, String variableName, Class<?> variableClass, Integer variableIndexOnUrl) {
        this.parameterIndex = parameterIndex;
        this.method = method;
        this.variableName = variableName;
        this.variableClass = variableClass;
        this.variableIndexOnUrl = variableIndexOnUrl;
    }

    public Integer getParameterIndex() {
        return parameterIndex;
    }

    public Method getMethod() {
        return method;
    }

    public String getVariableName() {
        return variableName;
    }

    public Class<?> getVariableClass() {
        return variableClass;
    }

    public Integer getVariableIndexOnUrl() {
        return variableIndexOnUrl;
    }
}
