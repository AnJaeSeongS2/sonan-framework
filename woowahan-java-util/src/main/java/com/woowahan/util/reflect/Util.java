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
public class Util {

    public static Object invokeStaticMethod(Class<?> target, String methodName, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return target.getMethod(methodName, methodParamTypes).invoke(null, argsForInvoke);
    }

    public static Object invokeStaticMethod(Class<?> target, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeStaticMethod(target, methodName, new Class[0], new Object[0]);
    }

    public static Object invokeMethod(Object target, String methodName, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        if (target instanceof Annotation) {
            method = ((Annotation) target).annotationType().getMethod(methodName, methodParamTypes);
        } else {
            method = target.getClass().getMethod(methodName, methodParamTypes);
        }
        return method.invoke(target, argsForInvoke);
    }

    public static Object invokeMethod(Object target, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethod(target, methodName, new Class[0], new Object[0]);
    }

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

    /**
     * weakness to multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     * but, this method exists for Test logics.
     * @param target
     * @param methodName
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static synchronized Object invokeMethodAnyway(Object target, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethodAnyway(target, methodName, new Class[0], new Object[0]);
    }

    /**
     * weakness to multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     * but, this method exists for Test logics.
     * @param target
     * @param methodName
     * @param methodParamTypes
     * @param argsForInvoke
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static synchronized Object invokeMethodAnyway(Object target, String methodName, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = findMethodAnyway(target.getClass(), methodName, methodParamTypes);
        boolean savedAccessible = method.isAccessible();
        method.setAccessible(true);
        Object result = method.invoke(target, argsForInvoke);
        method.setAccessible(savedAccessible);
        return result;
    }

    /**
     * weakness to multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     * but, this method exists for Test logics.
     * @param target
     * @param methodName
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static synchronized Object invokeStaticMethodAnyway(Class<?> target, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeStaticMethodAnyway(target, methodName, new Class[0], new Object[0]);
    }

    /**
     * weakness to multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     * but, this method exists for Test logics.
     * @param target
     * @param methodName
     * @param methodParamTypes
     * @param argsForInvoke
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static synchronized Object invokeStaticMethodAnyway(Class<?> target, String methodName, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = findMethodAnyway(target, methodName, methodParamTypes);
        boolean savedAccessible = method.isAccessible();
        method.setAccessible(true);
        Object result = method.invoke(null, argsForInvoke);
        method.setAccessible(savedAccessible);
        return result;
    }

    public static Method findMethodAnyway(Class<?> target, String methodName, Class<?>[] methodParamTypes) throws NoSuchMethodException {
        if (target == null) {
            throw new NoSuchMethodException("methodName : " + methodName + " methodParamTypes : " + methodParamTypes.toString());
        }
        Method method;
        try {
            method = target.getMethod(methodName, methodParamTypes);
        } catch (NoSuchMethodException e) {
            method = findMethodWithUnAccessible(target, methodName, methodParamTypes);
        }
        return method;
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

    private static Method findMethodWithUnAccessible(Class<?> target, String methodName, Class<?>[] methodParamTypes) throws NoSuchMethodException {
        try {
            return target.getDeclaredMethod(methodName, methodParamTypes);
        } catch (NoSuchMethodException e) {
            if (target.getSuperclass() == null)
                throw e;
            return findMethodWithUnAccessible(target.getSuperclass(), methodName, methodParamTypes);
        }
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


    /**
     * target에서 matcher에 매칭되는 method가 존재하면 반환. superclass도 search한다.
     * @param target
     * @param filter
     * @return
     * @throws NoSuchFieldException
     */
    public static Method findMethodAnyway(Class<?> target, Predicate<Method> filter) throws NoSuchMethodException {
        if (target == null) {
            throw new NoSuchMethodException("target : " + target);
        }
        for (Method declaredMethod : target.getDeclaredMethods()) {
            if (filter.test(declaredMethod)) {
                return declaredMethod;
            }
        }
        return findMethodAnyway(target.getSuperclass(), filter);
    }
}
