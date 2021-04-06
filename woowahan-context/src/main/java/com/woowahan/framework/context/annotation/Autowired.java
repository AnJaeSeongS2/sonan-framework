package com.woowahan.framework.context.annotation;

import java.lang.annotation.*;

/**
 * 이 annotation이 붙어있는 대상에 Bean이 주입된다.
 *
 * ElementType.CONSTRUCTOR ->
 *
 * value() 는 beanName 들을 의미한다.
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
// original target
//@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})

// 우선 적용 target
@Target({ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String[] value() default "";
}