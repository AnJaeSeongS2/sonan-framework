package com.woowahan.util.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

/**
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class UtilMethod {

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

    private static Method findMethodWithUnAccessible(Class<?> target, String methodName, Class<?>[] methodParamTypes) throws NoSuchMethodException {
        try {
            return target.getDeclaredMethod(methodName, methodParamTypes);
        } catch (NoSuchMethodException e) {
            if (target.getSuperclass() == null)
                throw e;
            return findMethodWithUnAccessible(target.getSuperclass(), methodName, methodParamTypes);
        }
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
