package com.woowahan.util.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

/**
 * Created by Jaeseong on 2021/04/02
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class UtilField {

    public static void setField(Object settableTarget, String fieldName, Object fieldObject) throws NoSuchFieldException, IllegalAccessException {
        settableTarget.getClass().getField(fieldName).set(settableTarget, fieldObject);
    }

    public static Object getField(Object gettableTarget, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return gettableTarget.getClass().getField(fieldName).get(gettableTarget);
    }

    /**
     * weakness to multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     * but, this method exists for Test logics.
     * @param target
     * @param fieldName
     * @param fieldObject
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static synchronized void setFieldAnyway(Object target, String fieldName, Object fieldObject) throws NoSuchFieldException, IllegalAccessException {
        Field field = findFieldAnyway(target.getClass(), fieldName);
        boolean savedAccessible = field.isAccessible();
        field.setAccessible(true);
        field.set(target, fieldObject);
        field.setAccessible(savedAccessible);
    }

    /**
     * weakness to multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     * but, this method exists for Test logics.
     * @param target
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static synchronized Object getFieldAnyway(Object target, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = findFieldAnyway(target.getClass(), fieldName);
        boolean savedAccessible = field.isAccessible();
        field.setAccessible(true);
        Object result = field.get(target);
        field.setAccessible(savedAccessible);
        return result;
    }

    public static Field findFieldAnyway(Class<?> target, String fieldName) throws NoSuchFieldException {
        if (target == null) {
            throw new NoSuchFieldException("fieldName : " + fieldName);
        }
        Field field;
        try {
            field = target.getField(fieldName);
        } catch (NoSuchFieldException e) {
            field = findFieldWithUnAccessible(target, fieldName);
        }
        return field;
    }

    private static Field findFieldWithUnAccessible(Class<?> target, String fieldName) throws NoSuchFieldException {
        try {
            return target.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (target.getSuperclass() == null)
                throw e;
            return findFieldWithUnAccessible(target.getSuperclass(), fieldName);
        }
    }

    /**
     * target에서 matcher에 매칭되는 field가 존재하면 반환. superclass도 search한다.
     * @param target
     * @param filter
     * @return
     * @throws NoSuchFieldException
     */
    public static Field findFieldAnyway(Class<?> target, Predicate<Field> filter) throws NoSuchFieldException {
        if (target == null) {
            throw new NoSuchFieldException("target : " + target);
        }
        for (Field declaredField : target.getDeclaredFields()) {
            if (filter.test(declaredField)) {
                return declaredField;
            }
        }
        return findFieldAnyway(target.getSuperclass(), filter);
    }
}
