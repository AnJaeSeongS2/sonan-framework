package com.woowahan.framework.context.annotation;

import java.lang.annotation.*;

/**
 * @see {@link BeanRegistrable} BeanRegistrable에 포함된다.
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Configuration {
    String beanName() default "";
}
