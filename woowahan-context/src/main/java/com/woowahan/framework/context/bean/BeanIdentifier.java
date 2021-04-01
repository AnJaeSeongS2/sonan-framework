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
    private String classCanonicalName;
    @NotNull
    private String beanName;

    public BeanIdentifier(@NotNull String classCanonicalName, String beanName) {
        this.classCanonicalName = classCanonicalName;
        if (beanName == null) {
            this.beanName = "";
        } else {
            this.beanName = beanName;
        }
    }

    @NotNull
    public String getBeanName() {
        return beanName;
    }

    @NotNull
    public String getClassCanonicalName() {
        return classCanonicalName;
    }

    @NotNull
    public String genId() {
        return classCanonicalName + ":" + beanName;
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
        return Objects.equals(classCanonicalName, beanId.classCanonicalName) && Objects.equals(beanName, beanId.beanName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classCanonicalName, beanName);
    }
}
