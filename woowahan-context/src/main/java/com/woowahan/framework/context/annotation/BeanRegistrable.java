package com.woowahan.framework.context.annotation;

import java.lang.annotation.*;
import java.util.Arrays;

/**
 * Bean으로 등록될 수 있는 Annotation들에게 붙여야 한다.
 *
 * @see {@link Bean}
 * @see {@link Configuration}
 * @see {@link Controller}
 * @see {@link Service}
 * Created by Jaeseong on 2021/04/05
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public enum BeanRegistrable {
    BEAN(Bean.class),
    CONFIGURATION(Configuration.class),
    CONTROLLER(Controller.class),
    SERVICE(Service.class);

    private Class<? extends Annotation> registrable;
    BeanRegistrable(Class<? extends Annotation> registrable) {
        this.registrable = registrable;
    }

    public Class<? extends Annotation> getRegistrable() {
        return this.registrable;
    }

    public static Class<? extends Annotation>[] getRegistrables() {
        return (Class<? extends Annotation>[]) Arrays.stream(BeanRegistrable.values())
            .map((beanRegistrable) -> beanRegistrable.getRegistrable())
            .toArray();
    }
}
