package org.sonan.framework.context.annotation;

import org.sonan.framework.context.bean.BeanIdentifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 등록될 Bean이 커버칠 영역 (scope)를 정의한다.
 * @see {@link BeanIdentifier} 와 @see {@link Scope} 에 의해 이미 존재한다고 판단되는 Bean인 경우, 존재하는 Bean을 재사용하게 된다.
 * @see org.sonan.framework.context.bean.Scope
 * Created by Jaeseong on 2021/03/31
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
    org.sonan.framework.context.bean.Scope value() default org.sonan.framework.context.bean.Scope.SINGLETON;
}
