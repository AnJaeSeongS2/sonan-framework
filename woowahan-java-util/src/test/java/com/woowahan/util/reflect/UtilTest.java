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
    public void invokeMethod() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Parent parent = new Parent();
        Child child = new Child();
        assertEquals("a", Util.invokeMethod(parent, "getA"));
        assertThrows(NoSuchMethodException.class, () -> Util.invokeMethod(parent, "getC"));
        assertEquals("a", Util.invokeMethod(child, "getA"));
        assertEquals("c", Util.invokeMethod(child, "getC"));

        assertThrows(NoSuchMethodException.class, () -> Util.invokeMethod(parent, "getB"));
        assertThrows(NoSuchMethodException.class, () -> Util.invokeMethod(child, "getB"));
    }

    @Test
    public void invokeMethodAnyway() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        TestClass obj = new TestClass();
        assertEquals("successMethod", Util.invokeMethodAnyway(obj, "method"));
        assertThrows(NoSuchMethodException.class, () -> Util.invokeMethodAnyway(obj, "method", new Class<?>[]{String.class, String.class}, "a", "b"));
        assertEquals("successMethodab", Util.invokeMethodAnyway(obj, "methodManyParams", new Class<?>[]{String.class, String.class}, "a", "b"));
        assertEquals("sucessMethodOnAnnotation", Util.invokeMethodAnyway(AnnotationOnClass.class.getDeclaredAnnotations()[0], "method"));

        Parent parent = new Parent();
        Child child = new Child();
        assertEquals("a", Util.invokeMethodAnyway(parent, "getA"));
        assertThrows(NoSuchMethodException.class, () -> Util.invokeMethodAnyway(parent, "getC"));
        assertEquals("a", Util.invokeMethodAnyway(child, "getA"));
        assertEquals("c", Util.invokeMethodAnyway(child, "getC"));

        assertEquals("b", Util.invokeMethodAnyway(parent, "getB"));
        assertEquals("b", Util.invokeMethodAnyway(child, "getB"));
    }

    @Test
    public void invokeStaticMethod() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        assertEquals("a", Util.invokeStaticMethod(Parent.class, "genA"));
        assertThrows(NoSuchMethodException.class, () -> Util.invokeStaticMethod(Parent.class, "genC"));
        assertEquals("a", Util.invokeStaticMethod(Child.class, "genA"));
        assertEquals("c", Util.invokeStaticMethod(Child.class, "genC"));

        assertThrows(NoSuchMethodException.class, () -> Util.invokeStaticMethod(Parent.class, "genB"));
        assertThrows(NoSuchMethodException.class, () -> Util.invokeStaticMethod(Child.class, "genB"));
    }

    @Test
    public void invokeStaticMethodAnyway() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        assertEquals("a", Util.invokeStaticMethodAnyway(Parent.class, "genA"));
        assertThrows(NoSuchMethodException.class, () -> Util.invokeStaticMethodAnyway(Parent.class, "genC"));
        assertEquals("a", Util.invokeStaticMethodAnyway(Child.class, "genA"));
        assertEquals("c", Util.invokeStaticMethodAnyway(Child.class, "genC"));

        assertEquals("b", Util.invokeStaticMethodAnyway(Parent.class, "genB"));
        assertEquals("b", Util.invokeStaticMethodAnyway(Child.class, "genB"));
    }

    @Test
    public void getField() throws IllegalAccessException, NoSuchFieldException {
        Parent parent = new Parent();
        Child child = new Child();
        assertEquals("a", Util.getField(parent, "a"));
        assertEquals("a", Util.getField(child, "a"));
        assertEquals("c", Util.getField(child, "c"));
        assertThrows(NoSuchFieldException.class, () -> Util.getField(parent, "c"));
        assertThrows(NoSuchFieldException.class, () -> Util.getField(parent, "b"));
        assertThrows(NoSuchFieldException.class, () -> Util.getField(child, "b"));
    }

    @Test
    public void getFieldAnyway() throws IllegalAccessException, NoSuchFieldException {
        Parent parent = new Parent();
        Child child = new Child();
        assertEquals("a", Util.getFieldAnyway(parent, "a"));
        assertEquals("a", Util.getFieldAnyway(child, "a"));
        assertEquals("c", Util.getFieldAnyway(child, "c"));
        assertEquals("d", Util.getFieldAnyway(child, "d"));
        assertEquals("b", Util.getFieldAnyway(child, "b"));
        assertThrows(NoSuchFieldException.class, () -> assertEquals("c", Util.getFieldAnyway(parent, "c")));
    }

    @Test
    public void setField() throws IllegalAccessException, NoSuchFieldException {
        Parent parent = new Parent();
        Child child = new Child();
        Util.setField(parent, "a", "aa");
        Util.setField(child, "a", "aa");
        Util.setField(child, "c", "cc");
        assertThrows(NoSuchFieldException.class, () -> Util.setField(parent, "c", "cc"));
        assertThrows(NoSuchFieldException.class, () -> Util.setField(parent, "b", "bb"));
        assertThrows(NoSuchFieldException.class, () -> Util.setField(child, "b", "bb"));

        assertEquals("aa", Util.getField(parent, "a"));
        assertEquals("aa", Util.getField(child, "a"));
        assertEquals("cc", Util.getField(child, "c"));
    }

    @Test
    public void setFieldAnyway() throws IllegalAccessException, NoSuchFieldException {
        Parent parent = new Parent();
        Child child = new Child();
        Util.setFieldAnyway(parent, "a", "aa");
        Util.setFieldAnyway(child, "a", "aa");
        Util.setFieldAnyway(child, "c", "cc");
        Util.setFieldAnyway(child, "d", "dd");
        Util.setFieldAnyway(child, "b", "bb");
        assertThrows(NoSuchFieldException.class, () -> Util.setFieldAnyway(parent, "c", "cc"));


        assertEquals("aa", Util.getFieldAnyway(parent, "a"));
        assertEquals("aa", Util.getFieldAnyway(child, "a"));
        assertEquals("cc", Util.getFieldAnyway(child, "c"));
        assertEquals("dd", Util.getFieldAnyway(child, "d"));
        assertEquals("bb", Util.getFieldAnyway(child, "b"));
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