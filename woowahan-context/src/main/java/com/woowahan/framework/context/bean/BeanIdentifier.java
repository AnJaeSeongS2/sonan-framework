package com.woowahan.framework.context.bean;

import com.woowahan.util.annotation.NotNull;

import java.util.Objects;

/**
 * BeanIdentifier은 Bean을 식별하는 식별자이다.
 *
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanIdentifier {
    @NotNull
    private Class<?> classObj;
    @NotNull
    private String name;

    public BeanIdentifier(@NotNull Class<?> classObj, String name) {
        this.classObj = classObj;
        if (name == null) {
            this.name = "";
        } else {
            this.name = name;
        }
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public Class<?> getClassObj() {
        return classObj;
    }

    @NotNull
    public String genId() {
        return classObj.getCanonicalName() + ":" + name;
    }

    @Override
    @NotNull
    public String toString() {
        return genId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeanIdentifier beanId = (BeanIdentifier) o;
        return Objects.equals(classObj, beanId.classObj) && Objects.equals(name, beanId.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classObj, name);
    }
}
