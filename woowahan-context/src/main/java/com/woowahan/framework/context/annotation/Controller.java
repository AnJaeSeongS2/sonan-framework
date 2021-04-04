package com.woowahan.framework.context.annotation;

import java.lang.annotation.*;

/**
 * Bean으로 등록될 대상에 붙인다.
 * 해당 Bean은 web Controller에 대한 명세이다.
 * 이 Bean의 내부에 @see com.woowahan.framework.web.annotation.X annotation기반으로 web Controller에 대한 명세를 작성할 수 있다.
 *
 * @see {@link BeanRegistrable} BeanRegistrable에 포함된다.
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String beanName() default "";
}
