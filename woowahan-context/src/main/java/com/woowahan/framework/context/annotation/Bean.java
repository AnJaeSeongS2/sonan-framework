package com.woowahan.framework.context.annotation;

import java.lang.annotation.*;

/**
 * TODO: Bean 은 ElementType.METHOD이므로, 차후 확장한다. 지금은 ElementType.TYPE만 고려한다.
 *
 * 직접 작성하는 Type이 아닌 외부 lib의 Type을 bean으로 등록한다.
 * Method 에 Bean annotation을 추가해 등록할 수 있다.
 * @see {@link BeanRegistrable} BeanRegistrable에 포함된다.
 *
 * String value() return beanName
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
    /**
     * @return beanName
     */
    String value() default "";
    String classCanonicalName() default "";
}
