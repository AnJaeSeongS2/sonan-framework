package com.woowahan.framework.context.annotation;

import java.lang.annotation.*;

/**
 * @see {@link BeanRegistrable} BeanRegistrable에 포함된다.
 *
 * String value() return beanName
 * String identifyClassCanonicalName() is for support regist as parent class's object.
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Configuration {
    /**
     * @return beanName
     */
    String value() default "";
    String identifyClassCanonicalName() default "";
}
