package com.woowahan.framework.context.annotation;

import java.lang.annotation.*;

/**
 * Bean으로 등록할 대상에 붙인다.
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
}
