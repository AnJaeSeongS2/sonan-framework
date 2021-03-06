package org.sonan.framework.context.annotation;

import java.lang.annotation.*;

/**
 * Bean으로 등록될 대상에 붙인다.
 * 이 annotation은 bussiness로직에 대한 명세이다.
 * Component annotation과 기능상엔 차이가 없으나, 정의를 구분한다.
 *
 * String value() return beanName
 * String identifyClassCanonicalName() is for support regist as parent class's object.
 * @see {@link BeanRegistrable} BeanRegistrable에 포함된다.
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    /**
     * @return beanName
     */
    String value() default "";
    String identifyClassCanonicalName() default "";
}
