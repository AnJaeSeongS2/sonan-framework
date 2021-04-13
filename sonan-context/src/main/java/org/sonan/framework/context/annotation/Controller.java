package org.sonan.framework.context.annotation;

import java.lang.annotation.*;

/**
 * Bean으로 등록될 대상에 붙인다.
 * 이 annotation은 web Controller에 대한 명세이다.
 * 등록될 Bean의 내부에 @see org.sonan.framework.web.annotation.X annotation기반으로 web Controller에 대한 명세를 작성할 수 있다.
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
public @interface Controller {
    /**
     * @return beanName
     */
    String value() default "";
    String identifyClassCanonicalName() default "";
}
