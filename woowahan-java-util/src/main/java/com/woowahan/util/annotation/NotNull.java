package com.woowahan.util.annotation;

import java.lang.annotation.*;

/**
 * 외부 종속성을 추가할 수 없기에 임시로 이 annotation기반으로 개발한다.
 * 현 상태의 Annotation NotNull 은 실제 컴파일에 도움을 주진 않는다.
 *
 * 가독성, 향후 repackage용도로 이를 추가하며 개발한다.
 *
 * Created by Jaeseong on 2021/03/31
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface NotNull {
}
