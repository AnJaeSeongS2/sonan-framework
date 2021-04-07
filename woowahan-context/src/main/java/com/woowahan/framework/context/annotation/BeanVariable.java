package com.woowahan.framework.context.annotation;

import java.lang.annotation.*;

/**
 * @see Autowired 가 붙어있는 constructor 내 param에 붙인다.
 * 이 annotation이 붙어있어야만, @see Autowired 처리될 때 해당 param으로 Bean이 binding된다.
 *
 * value = beanName이다.
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanVariable {
    String value() default "";
}
