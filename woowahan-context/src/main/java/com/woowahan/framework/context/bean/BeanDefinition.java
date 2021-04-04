package com.woowahan.framework.context.bean;

import com.woowahan.util.annotation.Nullable;

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
    private BeanIdentifier id;
    private Scope scope;

    public BeanDefinition(BeanIdentifier id, @Nullable Scope scope) {
        this.id = id;
        if (scope == null) {
            this.scope = Scope.SINGLETON;
        } else {
            this.scope = scope;
        }
    }

    public BeanDefinition(String beanClassCannonicalName, @Nullable String beanName, @Nullable Scope scope) {
        this.id = new BeanIdentifier(beanClassCannonicalName, beanName);
        if (scope == null) {
            this.scope = Scope.SINGLETON;
        } else {
            this.scope = scope;
        }
    }

    public boolean isPrototype() {
        return Scope.PROTOTYPE.equals(scope);
    }

    public boolean isSingleton() {
        return Scope.SINGLETON.equals(scope);
    }

    public boolean isSameScope(Scope scope) {
        return this.scope.equals(scope);
    }

    public boolean isSameId(BeanIdentifier id) {
        return this.id.equals(id);
    }

    public BeanIdentifier getId() {
        return id;
    }

    public Scope getScope() {
        return scope;
    }

    public String getBeanClassCanonicalName() {
        return id.getClassCanonicalName();
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
