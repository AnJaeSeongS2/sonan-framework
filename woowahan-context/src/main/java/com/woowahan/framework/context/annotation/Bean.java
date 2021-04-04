package com.woowahan.framework.context.annotation;

import java.lang.annotation.*;

/**
 * @see {@link Component} 와는 다르게, 직접 작성하는 Type이 아닌 외부 lib의 Type을 bean으로 등록한다.
 * Method 에 Bean annotation을 추가해 등록할 수 있다.
 * BeanIdentifier는
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
}
