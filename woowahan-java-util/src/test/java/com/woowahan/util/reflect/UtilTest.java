package com.woowahan.util.reflect;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/05
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class UtilTest {

    @Test
    void invokeMethodAnyway() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        TestClass obj = new TestClass();
        assertEquals("successMethod", Util.invokeMethodAnyway(obj, "method"));
        assertThrows(NoSuchMethodException.class, () -> Util.invokeMethodAnyway(obj, "method", new Class<?>[]{String.class, String.class}, "a", "b"));
        assertEquals("successMethodab", Util.invokeMethodAnyway(obj, "methodManyParams", new Class<?>[]{String.class, String.class}, "a", "b"));
        assertEquals("sucessMethodOnAnnotation", Util.invokeMethodAnyway(AnnotationOnClass.class.getDeclaredAnnotations()[0], "method"));
    }

    class TestClass {
        private String method() {
            return "successMethod";
        }

        private String methodManyParams(String a, String b) {
            return "successMethod" + a + b;
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface TestRuntimeAnnotation {
        String method() default "sucessMethodOnAnnotation";
    }

    @TestRuntimeAnnotation
    class AnnotationOnClass {

    }
}