package com.woowahan.util.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Jaeseong on 2021/04/02
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class Util {

    public static void setField(Object settableObject, String fieldName, Object fieldObject) throws NoSuchFieldException, IllegalAccessException {
        settableObject.getClass().getDeclaredField(fieldName).set(settableObject, fieldObject);
    }

    public static Object getField(Object gettableObject, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return gettableObject.getClass().getDeclaredField(fieldName).get(gettableObject);
    }

    public static Object invokeMethod(Object invokableObject, String methodName, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        if (invokableObject instanceof Annotation) {
            method = ((Annotation) invokableObject).annotationType().getDeclaredMethod(methodName, methodParamTypes);
        } else {
            method = invokableObject.getClass().getDeclaredMethod(methodName, methodParamTypes);
        }
        return method.invoke(invokableObject, argsForInvoke);
    }

    public static Object invokeMethod(Object invokableObject, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        if (invokableObject instanceof Annotation) {
            method = ((Annotation) invokableObject).annotationType().getDeclaredMethod(methodName);
        } else {
            method = invokableObject.getClass().getDeclaredMethod(methodName);
        }
        return method.invoke(invokableObject);
    }

    /**
     * weakness to multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     * but, this method exists for Test logics.
     * @param settableObject
     * @param fieldName
     * @param fieldObject
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static synchronized void setFieldAnyway(Object settableObject, String fieldName, Object fieldObject) throws NoSuchFieldException, IllegalAccessException {
        Field field = settableObject.getClass().getDeclaredField(fieldName);
        boolean savedAccessible = field.isAccessible();
        field.setAccessible(true);
        field.set(settableObject, fieldObject);
        field.setAccessible(savedAccessible);
    }

    /**
     * weakness to multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     * but, this method exists for Test logics.
     * @param gettableObject
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static synchronized Object getFieldAnyway(Object gettableObject, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = gettableObject.getClass().getDeclaredField(fieldName);
        boolean savedAccessible = field.isAccessible();
        field.setAccessible(true);
        Object result = field.get(gettableObject);
        field.setAccessible(savedAccessible);
        return result;
    }

    /**
     * weakness to multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     * but, this method exists for Test logics.
     * @param invokableObject
     * @param methodName
     * @param methodParamTypes
     * @param argsForInvoke
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static synchronized Object invokeMethodAnyway(Object invokableObject, String methodName, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        if (invokableObject instanceof Annotation) {
            method = ((Annotation) invokableObject).annotationType().getDeclaredMethod(methodName, methodParamTypes);
        } else {
            method = invokableObject.getClass().getDeclaredMethod(methodName, methodParamTypes);
        }
        boolean savedAccessible = method.isAccessible();
        method.setAccessible(true);
        Object result = method.invoke(invokableObject, argsForInvoke);
        method.setAccessible(savedAccessible);
        return result;
    }

    /**
     * weakness to multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     * but, this method exists for Test logics.
     * @param invokableObject
     * @param methodName
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static synchronized Object invokeMethodAnyway(Object invokableObject, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        if (invokableObject instanceof Annotation) {
            method = ((Annotation) invokableObject).annotationType().getDeclaredMethod(methodName);
        } else {
            method = invokableObject.getClass().getDeclaredMethod(methodName);
        }
        boolean savedAccessible = method.isAccessible();
        method.setAccessible(true);
        Object result = method.invoke(invokableObject);
        method.setAccessible(savedAccessible);
        return result;
    }
}
