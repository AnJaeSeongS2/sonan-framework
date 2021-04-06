package com.woowahan.util.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class UtilMethodTest {

    @Test
    public void invokeMethod() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Parent parent = new Parent();
        Child child = new Child();
        assertEquals("a", UtilMethod.invokeMethod(parent, "getA"));
        assertThrows(NoSuchMethodException.class, () -> UtilMethod.invokeMethod(parent, "getC"));
        assertEquals("a", UtilMethod.invokeMethod(child, "getA"));
        assertEquals("c", UtilMethod.invokeMethod(child, "getC"));

        assertThrows(NoSuchMethodException.class, () -> UtilMethod.invokeMethod(parent, "getB"));
        assertThrows(NoSuchMethodException.class, () -> UtilMethod.invokeMethod(child, "getB"));
    }

    @Test
    public void invokeMethodAnyway() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        TestClass obj = new TestClass();
        assertEquals("successMethod", UtilMethod.invokeMethodAnyway(obj, "method"));
        assertThrows(NoSuchMethodException.class, () -> UtilMethod.invokeMethodAnyway(obj, "method", new Class<?>[]{String.class, String.class}, "a", "b"));
        assertEquals("successMethodab", UtilMethod.invokeMethodAnyway(obj, "methodManyParams", new Class<?>[]{String.class, String.class}, "a", "b"));
        assertEquals("sucessMethodOnAnnotation", UtilMethod.invokeMethodAnyway(AnnotationOnClass.class.getDeclaredAnnotations()[0], "method"));

        Parent parent = new Parent();
        Child child = new Child();
        assertEquals("a", UtilMethod.invokeMethodAnyway(parent, "getA"));
        assertThrows(NoSuchMethodException.class, () -> UtilMethod.invokeMethodAnyway(parent, "getC"));
        assertEquals("a", UtilMethod.invokeMethodAnyway(child, "getA"));
        assertEquals("c", UtilMethod.invokeMethodAnyway(child, "getC"));

        assertEquals("b", UtilMethod.invokeMethodAnyway(parent, "getB"));
        assertEquals("b", UtilMethod.invokeMethodAnyway(child, "getB"));
    }

    @Test
    public void invokeStaticMethod() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        assertEquals("a", UtilMethod.invokeStaticMethod(Parent.class, "genA"));
        assertThrows(NoSuchMethodException.class, () -> UtilMethod.invokeStaticMethod(Parent.class, "genC"));
        assertEquals("a", UtilMethod.invokeStaticMethod(Child.class, "genA"));
        assertEquals("c", UtilMethod.invokeStaticMethod(Child.class, "genC"));

        assertThrows(NoSuchMethodException.class, () -> UtilMethod.invokeStaticMethod(Parent.class, "genB"));
        assertThrows(NoSuchMethodException.class, () -> UtilMethod.invokeStaticMethod(Child.class, "genB"));
    }

    @Test
    public void invokeStaticMethodAnyway() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        assertEquals("a", UtilMethod.invokeStaticMethodAnyway(Parent.class, "genA"));
        assertThrows(NoSuchMethodException.class, () -> UtilMethod.invokeStaticMethodAnyway(Parent.class, "genC"));
        assertEquals("a", UtilMethod.invokeStaticMethodAnyway(Child.class, "genA"));
        assertEquals("c", UtilMethod.invokeStaticMethodAnyway(Child.class, "genC"));

        assertEquals("b", UtilMethod.invokeStaticMethodAnyway(Parent.class, "genB"));
        assertEquals("b", UtilMethod.invokeStaticMethodAnyway(Child.class, "genB"));
    }

    @Test
    void findMethodAnyway() throws NoSuchMethodException {
        assertEquals("getD", UtilMethod.findMethodAnyway(Child.class, (method) -> method.getName().equals("getD")).getName());
        assertEquals("genB", UtilMethod.findMethodAnyway(Child.class, (method) -> method.getName().equals("genB")).getName());
    }
}