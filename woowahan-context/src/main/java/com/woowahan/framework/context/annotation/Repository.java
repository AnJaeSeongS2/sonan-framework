package com.woowahan.framework.context.annotation;


import java.lang.annotation.*;

/**
 * Bean으로 등록될 대상에 붙인다.
 * 해당 Annotation은 Repository로서 등록될 Bean Class에 붙인다.
 * 각종 vendor는 이 Repository Bean을 지원해야할 것이다.
 *
 * String value() return beanName
 * String identifyClassCanonicalName() is for support regist as parent class's object.
 * @see {@link BeanRegistrable} BeanRegistrable에 포함된다.
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Repository {
    /**
     * @return beanName
     */
    String value() default "";
    String identifyClassCanonicalName() default "";
}
