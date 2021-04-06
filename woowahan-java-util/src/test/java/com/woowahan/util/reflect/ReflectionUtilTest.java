package com.woowahan.util.reflect;

import org.junit.jupiter.api.Test;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/05
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class ReflectionUtilTest {


    /**
     * ********************************************************************************
     * Field Utils Test
     * *********************************************************************************
     */
    @Test
    public void getField() throws IllegalAccessException, NoSuchFieldException {
        Parent parent = new Parent();
        Child child = new Child();
        assertEquals("a", ReflectionUtil.getField(parent, "a"));
        assertEquals("a", ReflectionUtil.getField(child, "a"));
        assertEquals("c", ReflectionUtil.getField(child, "c"));
        assertThrows(NoSuchFieldException.class, () -> ReflectionUtil.getField(parent, "c"));
        assertThrows(NoSuchFieldException.class, () -> ReflectionUtil.getField(parent, "b"));
        assertThrows(NoSuchFieldException.class, () -> ReflectionUtil.getField(child, "b"));
    }

    @Test
    public void getFieldAnyway() throws IllegalAccessException, NoSuchFieldException {
        Parent parent = new Parent();
        Child child = new Child();
        assertEquals("a", ReflectionUtil.getFieldAnyway(parent, "a"));
        assertEquals("a", ReflectionUtil.getFieldAnyway(child, "a"));
        assertEquals("c", ReflectionUtil.getFieldAnyway(child, "c"));
        assertEquals("d", ReflectionUtil.getFieldAnyway(child, "d"));
        assertEquals("b", ReflectionUtil.getFieldAnyway(child, "b"));
        assertThrows(NoSuchFieldException.class, () -> assertEquals("c", ReflectionUtil.getFieldAnyway(parent, "c")));
    }

    @Test
    public void setField() throws IllegalAccessException, NoSuchFieldException {
        Parent parent = new Parent();
        Child child = new Child();
        ReflectionUtil.setField(parent, "a", "aa");
        ReflectionUtil.setField(child, "a", "aa");
        ReflectionUtil.setField(child, "c", "cc");
        assertThrows(NoSuchFieldException.class, () -> ReflectionUtil.setField(parent, "c", "cc"));
        assertThrows(NoSuchFieldException.class, () -> ReflectionUtil.setField(parent, "b", "bb"));
        assertThrows(NoSuchFieldException.class, () -> ReflectionUtil.setField(child, "b", "bb"));

        assertEquals("aa", ReflectionUtil.getField(parent, "a"));
        assertEquals("aa", ReflectionUtil.getField(child, "a"));
        assertEquals("cc", ReflectionUtil.getField(child, "c"));
    }

    @Test
    public void setFieldAnyway() throws IllegalAccessException, NoSuchFieldException {
        Parent parent = new Parent();
        Child child = new Child();
        ReflectionUtil.setFieldAnyway(parent, "a", "aa");
        ReflectionUtil.setFieldAnyway(child, "a", "aa");
        ReflectionUtil.setFieldAnyway(child, "c", "cc");
        ReflectionUtil.setFieldAnyway(child, "d", "dd");
        ReflectionUtil.setFieldAnyway(child, "b", "bb");
        assertThrows(NoSuchFieldException.class, () -> ReflectionUtil.setFieldAnyway(parent, "c", "cc"));


        assertEquals("aa", ReflectionUtil.getFieldAnyway(parent, "a"));
        assertEquals("aa", ReflectionUtil.getFieldAnyway(child, "a"));
        assertEquals("cc", ReflectionUtil.getFieldAnyway(child, "c"));
        assertEquals("dd", ReflectionUtil.getFieldAnyway(child, "d"));
        assertEquals("bb", ReflectionUtil.getFieldAnyway(child, "b"));
    }

    @Test
    void findFieldAnyway() throws NoSuchFieldException {
        assertEquals(Child.class, ReflectionUtil.getFieldMetaAnyway(Child.class, (field) -> field.getName().equals("d")).getDeclaringClass());
        assertEquals(Parent.class, ReflectionUtil.getFieldMetaAnyway(Child.class, (field) -> field.getName().equals("b")).getDeclaringClass());
    }

    @Test
    void getFieldMetaWithAnnotation() throws NoSuchFieldException {
        assertEquals("field", ReflectionUtil.getFieldMetaAnyway(TestClassWithFieldAnnotation.class, (field) -> {
            for (Annotation declaredAnnotation : field.getDeclaredAnnotations()) {
                if (declaredAnnotation instanceof TestFieldAnnotation) {
                    return true;
                }
            }
            return false;
        }).getName());
    }


    /**
     * ********************************************************************************
     * Method Utils Test
     * *********************************************************************************
     */
    @Test
    public void invokeMethod() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Parent parent = new Parent();
        Child child = new Child();
        assertEquals("a", ReflectionUtil.invokeMethod(parent, "getA"));
        assertThrows(NoSuchMethodException.class, () -> ReflectionUtil.invokeMethod(parent, "getC"));
        assertEquals("a", ReflectionUtil.invokeMethod(child, "getA"));
        assertEquals("c", ReflectionUtil.invokeMethod(child, "getC"));

        assertThrows(NoSuchMethodException.class, () -> ReflectionUtil.invokeMethod(parent, "getB"));
        assertThrows(NoSuchMethodException.class, () -> ReflectionUtil.invokeMethod(child, "getB"));
    }

    @Test
    public void invokeMethodAnyway() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        TestClass obj = new TestClass();
        assertEquals("successMethod", ReflectionUtil.invokeMethodAnyway(obj, "method"));
        assertThrows(NoSuchMethodException.class, () -> ReflectionUtil.invokeMethodAnyway(obj, "method", new Class<?>[]{String.class, String.class}, "a", "b"));
        assertEquals("successMethodab", ReflectionUtil.invokeMethodAnyway(obj, "methodManyParams", new Class<?>[]{String.class, String.class}, "a", "b"));
        assertEquals("sucessMethodOnAnnotation", ReflectionUtil.invokeMethodAnyway(AnnotationOnClass.class.getDeclaredAnnotations()[0], "method"));

        Parent parent = new Parent();
        Child child = new Child();
        assertEquals("a", ReflectionUtil.invokeMethodAnyway(parent, "getA"));
        assertThrows(NoSuchMethodException.class, () -> ReflectionUtil.invokeMethodAnyway(parent, "getC"));
        assertEquals("a", ReflectionUtil.invokeMethodAnyway(child, "getA"));
        assertEquals("c", ReflectionUtil.invokeMethodAnyway(child, "getC"));

        assertEquals("b", ReflectionUtil.invokeMethodAnyway(parent, "getB"));
        assertEquals("b", ReflectionUtil.invokeMethodAnyway(child, "getB"));
    }

    @Test
    public void invokeStaticMethod() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        assertEquals("a", ReflectionUtil.invokeStaticMethod(Parent.class, "genA"));
        assertThrows(NoSuchMethodException.class, () -> ReflectionUtil.invokeStaticMethod(Parent.class, "genC"));
        assertEquals("a", ReflectionUtil.invokeStaticMethod(Child.class, "genA"));
        assertEquals("c", ReflectionUtil.invokeStaticMethod(Child.class, "genC"));

        assertThrows(NoSuchMethodException.class, () -> ReflectionUtil.invokeStaticMethod(Parent.class, "genB"));
        assertThrows(NoSuchMethodException.class, () -> ReflectionUtil.invokeStaticMethod(Child.class, "genB"));
    }

    @Test
    public void invokeStaticMethodAnyway() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        assertEquals("a", ReflectionUtil.invokeStaticMethodAnyway(Parent.class, "genA"));
        assertThrows(NoSuchMethodException.class, () -> ReflectionUtil.invokeStaticMethodAnyway(Parent.class, "genC"));
        assertEquals("a", ReflectionUtil.invokeStaticMethodAnyway(Child.class, "genA"));
        assertEquals("c", ReflectionUtil.invokeStaticMethodAnyway(Child.class, "genC"));

        assertEquals("b", ReflectionUtil.invokeStaticMethodAnyway(Parent.class, "genB"));
        assertEquals("b", ReflectionUtil.invokeStaticMethodAnyway(Child.class, "genB"));
    }

    @Test
    void findMethodAnyway() throws NoSuchMethodException {
        assertEquals("getD", ReflectionUtil.getMethodMetaAnyway(Child.class, (method) -> method.getName().equals("getD")).getName());
        assertEquals("genB", ReflectionUtil.getMethodMetaAnyway(Child.class, (method) -> method.getName().equals("genB")).getName());
    }

    @Test
    void getMethodMetaHasAnnotation() throws NoSuchMethodException {
        assertEquals("setBean", ReflectionUtil.getMethodMetaAnyway(TestClassHasMethodWithAnnotation.class, (method) -> {
            for (Annotation declaredAnnotation : method.getDeclaredAnnotations()) {
                if (declaredAnnotation instanceof TestMethodAnnotation) {
                    return true;
                }
            }
            return false;
        }).getName());
    }



    /**
     * ********************************************************************************
     * Constructor Utils Test
     * *********************************************************************************
     */

    @Test
    void getPrivateConstructor() throws NoSuchMethodException {
        assertFalse(ReflectionUtil.getConstructorMetaAnyway(TestClassHasPrivateConstructorWithAnnotation.class, (ctor) -> !ctor.isAccessible()).isAccessible());
    }


    @Test
    void getAnnotatedConstructor() throws NoSuchMethodException {
        assertEquals(String.class, ReflectionUtil.getConstructorMetaAnyway(TestClassHasPrivateConstructorWithAnnotation.class, (ctor) -> {
            for (Annotation declaredAnnotation : ctor.getDeclaredAnnotations()) {
                if (declaredAnnotation instanceof TestConstructorAnnotation) {
                    return true;
                }
            }
            return false;
        }).getParameterTypes()[0]);
    }

    @Test
    void newInstance() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        assertThrows(Throwable.class, () -> ReflectionUtil.newInstance(TestClassHasPrivateConstructorWithAnnotation.class, new Class[]{String.class}, new Object[]{"test"}));
        assertEquals("third", ((TestClassHasPrivateConstructorWithAnnotation) ReflectionUtil.newInstance(TestClassHasPrivateConstructorWithAnnotation.class, new Class[]{String.class, Object.class, Object.class}, new Object[]{"testInput", "second", "third"})).third);
        assertEquals("third", ((TestClassHasPrivateConstructorWithAnnotation) ReflectionUtil.newInstance(TestClassHasPrivateConstructorWithAnnotation.class, new Class[]{String.class, Object.class, Object.class}, new Object[]{"testInput", null, "third"})).third);
        assertEquals("childBean4Input", ((TestChildClassHasPrivateConstructorWithAnnotation) ReflectionUtil.newInstance(TestChildClassHasPrivateConstructorWithAnnotation.class, new Class[]{String.class, Object.class, Object.class}, new Object[]{"testInput", "childBean4Input", "5"})).bean4);
    }

    @Test
    void newInstanceAnyway() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        assertEquals("childBean4", ((TestChildClassHasPrivateConstructorWithAnnotation) ReflectionUtil.newInstanceAnyway(TestChildClassHasPrivateConstructorWithAnnotation.class, new Class[]{String.class}, new Object[]{"testInput"})).bean4);
        assertThrows(NoSuchMethodException.class, () -> ReflectionUtil.newInstanceAnyway(TestChildClassHasPrivateConstructorWithAnnotation.class, new Class[]{String.class, Object.class}, new Object[]{"testInput", "second"}));
    }
}

@Target({ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@interface TestConstructorAnnotation {
}

class TestClassHasPrivateConstructorWithAnnotation {
    public String bean;
    public Object second;
    public Object third;

    public TestClassHasPrivateConstructorWithAnnotation(String bean, Object second, Object third) {
        this.bean = bean;
        this.second = second;
        this.third = third;
    }

    private TestClassHasPrivateConstructorWithAnnotation(String bean, Object second) {
        this.bean = bean;
        this.second = second;
    }

    @TestConstructorAnnotation
    private TestClassHasPrivateConstructorWithAnnotation(String bean) {
        this.bean = bean;
    }

    public TestClassHasPrivateConstructorWithAnnotation() {
    }
}

class TestChildClassHasPrivateConstructorWithAnnotation extends TestClassHasPrivateConstructorWithAnnotation{
    public String bean;
    public Object bean4;
    public Object bean5;

    public TestChildClassHasPrivateConstructorWithAnnotation(String bean, Object bean4, Object bean5) {
        this.bean = bean;
        this.bean4 = bean4;
        this.bean5 = bean5;
    }

    @TestConstructorAnnotation
    private TestChildClassHasPrivateConstructorWithAnnotation(String bean) {
        this.bean = bean;
        this.bean4 = "childBean4";
    }

    private TestChildClassHasPrivateConstructorWithAnnotation() {
    }
}


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface TestMethodAnnotation {
}

class TestClassHasMethodWithAnnotation {
    private String bean;

    @TestMethodAnnotation
    public void setBean(String bean) {
        this.bean = bean;
    }
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface TestFieldAnnotation {

}

class TestClassWithFieldAnnotation {

    private String fieldBefore;
    private String fieldBefore2;
    private String fieldBefore3;
    @TestFieldAnnotation
    private String field;

    private String fieldAfter4;

    TestClassWithFieldAnnotation() {
        this.field = "test";
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