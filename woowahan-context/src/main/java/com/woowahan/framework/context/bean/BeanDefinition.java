package com.woowahan.framework.context.bean;

import com.woowahan.util.annotation.NotNull;

import java.util.Objects;

/**
 * Bean에 대한 정의.
 *
 * Bean의 scope, class object, name등에 대한 정보이다.
 * @see BeanGenerator 에서 Bean이 생성될 것이다.
 *
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanDefinition {
    @NotNull
    private BeanIdentifier id;
    @NotNull
    private Scope scope;

    public BeanDefinition(@NotNull BeanIdentifier id, @NotNull Scope scope) {
        this.id = id;
        this.scope = scope;
    }

    public BeanDefinition(@NotNull Class<?> beanClassObj, String beanName, @NotNull Scope scope) {
        this.id = new BeanIdentifier(beanClassObj, beanName);
        this.scope = scope;
    }

    public boolean isPrototype() {
        return Scope.Prototype.equals(scope);
    }

    public boolean isSingleton() {
        return Scope.Singleton.equals(scope);
    }

    public boolean isSameScope(@NotNull Scope scope) {
        return this.scope.equals(scope);
    }

    public boolean isSameId(@NotNull BeanIdentifier id) {
        return this.id.equals(id);
    }

    @NotNull
    public BeanIdentifier getId() {
        return id;
    }
    @NotNull
    public Scope getScope() {
        return scope;
    }

    @NotNull
    public Class<?> getClassObj() {
        return id.getClassObj();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeanDefinition that = (BeanDefinition) o;
        return id.equals(that.id) && scope == that.scope;
    }

    @Override
    public String toString() {
        return id.toString() + ":" + scope.name();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scope);
    }
}
