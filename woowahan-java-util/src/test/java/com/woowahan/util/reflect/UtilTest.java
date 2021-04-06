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
    public void getField() throws IllegalAccessException, NoSuchFieldException {
        Parent parent = new Parent();
        Child child = new Child();
        assertEquals("a", UtilField.getField(parent, "a"));
        assertEquals("a", UtilField.getField(child, "a"));
        assertEquals("c", UtilField.getField(child, "c"));
        assertThrows(NoSuchFieldException.class, () -> UtilField.getField(parent, "c"));
        assertThrows(NoSuchFieldException.class, () -> UtilField.getField(parent, "b"));
        assertThrows(NoSuchFieldException.class, () -> UtilField.getField(child, "b"));
    }

    @Test
    public void getFieldAnyway() throws IllegalAccessException, NoSuchFieldException {
        Parent parent = new Parent();
        Child child = new Child();
        assertEquals("a", UtilField.getFieldAnyway(parent, "a"));
        assertEquals("a", UtilField.getFieldAnyway(child, "a"));
        assertEquals("c", UtilField.getFieldAnyway(child, "c"));
        assertEquals("d", UtilField.getFieldAnyway(child, "d"));
        assertEquals("b", UtilField.getFieldAnyway(child, "b"));
        assertThrows(NoSuchFieldException.class, () -> assertEquals("c", UtilField.getFieldAnyway(parent, "c")));
    }

    @Test
    public void setField() throws IllegalAccessException, NoSuchFieldException {
        Parent parent = new Parent();
        Child child = new Child();
        UtilField.setField(parent, "a", "aa");
        UtilField.setField(child, "a", "aa");
        UtilField.setField(child, "c", "cc");
        assertThrows(NoSuchFieldException.class, () -> UtilField.setField(parent, "c", "cc"));
        assertThrows(NoSuchFieldException.class, () -> UtilField.setField(parent, "b", "bb"));
        assertThrows(NoSuchFieldException.class, () -> UtilField.setField(child, "b", "bb"));

        assertEquals("aa", UtilField.getField(parent, "a"));
        assertEquals("aa", UtilField.getField(child, "a"));
        assertEquals("cc", UtilField.getField(child, "c"));
    }

    @Test
    public void setFieldAnyway() throws IllegalAccessException, NoSuchFieldException {
        Parent parent = new Parent();
        Child child = new Child();
        UtilField.setFieldAnyway(parent, "a", "aa");
        UtilField.setFieldAnyway(child, "a", "aa");
        UtilField.setFieldAnyway(child, "c", "cc");
        UtilField.setFieldAnyway(child, "d", "dd");
        UtilField.setFieldAnyway(child, "b", "bb");
        assertThrows(NoSuchFieldException.class, () -> UtilField.setFieldAnyway(parent, "c", "cc"));


        assertEquals("aa", UtilField.getFieldAnyway(parent, "a"));
        assertEquals("aa", UtilField.getFieldAnyway(child, "a"));
        assertEquals("cc", UtilField.getFieldAnyway(child, "c"));
        assertEquals("dd", UtilField.getFieldAnyway(child, "d"));
        assertEquals("bb", UtilField.getFieldAnyway(child, "b"));
    }

    @Test
    void findFieldAnyway() throws NoSuchFieldException {
        assertEquals(Child.class, UtilField.findFieldAnyway(Child.class, (field) -> field.getName().equals("d")).getDeclaringClass());
        assertEquals(Parent.class, UtilField.findFieldAnyway(Child.class, (field) -> field.getName().equals("b")).getDeclaringClass());
    }
}

class Parent {
    public String a = "a";
    private String b = "b";

    public String getA() {
        return a;
    }

    private String getB() {
        return b;
    }

    public static String genA() {
        return "a";
    }

    private static String genB() {
        return "b";
    }
}

class Child extends Parent {
    public String c = "c";
    private String d = "d";

    public String getC() {
        return c;
    }

    private String getD() {
        return d;
    }

    public static String genC() {
        return "c";
    }

    public static String genD() {
        return "d";
    }
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