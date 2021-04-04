package com.woowahan.framework.context.annotation;

import com.woowahan.framework.context.bean.throwable.BeanException;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Jaeseong on 2021/04/05
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class BeanRegistrableTest {

    @Test
    void getBeanName() throws BeanException {
        boolean called = false;
        for (Annotation anno: TestController.class.getDeclaredAnnotations()) {
            if (BeanRegistrable.contains(anno)) {
                assertEquals("abc", BeanRegistrable.getBeanName(anno));
                called = true;
            }
        }
        assertTrue(called);
    }
}

@Controller("abc")
class TestController {
}