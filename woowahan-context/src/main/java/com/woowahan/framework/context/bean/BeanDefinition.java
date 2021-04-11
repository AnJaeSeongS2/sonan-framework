package com.woowahan.framework.context.bean;

import com.woowahan.util.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * Bean에 대한 정의.
 *
 * Bean의 scope, class object, name등에 대한 정보이다.
 * @see BeanGenerator 에서 Bean이 생성될 것이다.
 *
 * Class<?> 나 Annotation을 사용하지 않고 canonicalName을 사용하는 이유는, Meta 취득용 ClassLoader를 별도로 두고 있기 때문에, ClassLoader가 다른 것을 염두해 뒀기 때문.
 * annotation 기반 프로그래밍 모델을 지원하지만, 향후 xml이나 별도 config file기반 개발도 염두해두므로 beanRegistableAnnotationCanonicalName 는 nullable이다.
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanDefinition {
    private BeanIdentifier id;
    private Scope scope;
    @Nullable // beanKind
    private String beanRegistableAnnotationCanonicalName;
    // 실제 생성되는 class의 name.
    private String beanCreationClassCanonicalName;

    public BeanDefinition(BeanIdentifier id) {
        this(id, null, null);
    }


    public BeanDefinition(BeanIdentifier id, @Nullable Scope scope) {
        this(id, scope, null);
    }

    public BeanDefinition(BeanIdentifier id, @Nullable Scope scope, @Nullable String beanRegistableAnnotationCanonicalName) {
        this(id, scope, beanRegistableAnnotationCanonicalName, id.getClassCanonicalName());
    }

    public BeanDefinition(BeanIdentifier id, @Nullable Scope scope, @Nullable String beanRegistableAnnotationCanonicalName, String beanCreationClassCanonicalName) {
        this.id = id;
        if (scope == null) {
            this.scope = Scope.SINGLETON;
        } else {
            this.scope = scope;
        }

        if (beanRegistableAnnotationCanonicalName != null) {
            this.beanRegistableAnnotationCanonicalName = beanRegistableAnnotationCanonicalName;
        }
        this.beanCreationClassCanonicalName = beanCreationClassCanonicalName;
    }

    public BeanDefinition(String beanClassCannonicalName) {
        this(new BeanIdentifier(beanClassCannonicalName, null));
    }

    public BeanDefinition(String beanClassCannonicalName, @Nullable String beanName, @Nullable Scope scope) {
        this(new BeanIdentifier(beanClassCannonicalName, beanName), scope);
    }

    public BeanDefinition(String beanClassCannonicalName, @Nullable String beanName, @Nullable Scope scope, @Nullable String beanRegistableAnnotationCanonicalName) {
        this(new BeanIdentifier(beanClassCannonicalName, beanName), scope, beanRegistableAnnotationCanonicalName);
    }
    public BeanDefinition(String beanClassCannonicalName, @Nullable String beanName, @Nullable Scope scope, @Nullable String beanRegistableAnnotationCanonicalName, String beanCreationClassCanonicalName) {
        this(new BeanIdentifier(beanClassCannonicalName, beanName), scope, beanRegistableAnnotationCanonicalName, beanCreationClassCanonicalName);
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
        return id.equals(that.id) && scope == that.scope && beanRegistableAnnotationCanonicalName == that.beanRegistableAnnotationCanonicalName;
    }

    @Override
    public String toString() {
        return String.format("BeanKind : %s, BeanId : %s, BeanScope : %s", beanRegistableAnnotationCanonicalName != null ? beanRegistableAnnotationCanonicalName.substring(beanRegistableAnnotationCanonicalName.lastIndexOf(".") + 1) : beanRegistableAnnotationCanonicalName, id.toString(), scope.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scope, beanRegistableAnnotationCanonicalName);
    }

    public String getBeanRegistableAnnotationCanonicalName() {
        return beanRegistableAnnotationCanonicalName;
    }

    public String getBeanCreationClassCanonicalName() {
        return beanCreationClassCanonicalName;
    }
}
