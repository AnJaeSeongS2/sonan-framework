package org.sonan.framework.context.bean;

import org.sonan.util.annotation.Nullable;

import java.util.Objects;

/**
 * BeanIdentifier은 Bean을 식별하는 식별자이다.
 *
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanIdentifier {
    private String classCanonicalName;
    private String beanName;

    public BeanIdentifier(String classCanonicalName, @Nullable String beanName) {
        this.classCanonicalName = classCanonicalName;
        if (beanName == null) {
            this.beanName = "";
        } else {
            this.beanName = beanName;
        }
    }

    public String getBeanName() {
        return beanName;
    }

    public String getClassCanonicalName() {
        return classCanonicalName;
    }

    public String genId() {
        return classCanonicalName + ":" + beanName;
    }

    @Override
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
