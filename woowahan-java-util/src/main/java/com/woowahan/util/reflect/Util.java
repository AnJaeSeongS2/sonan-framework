package com.woowahan.util.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Jaeseong on 2021/04/02
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class Util {

    public static Object invokeMethodAnyway(Object invokableObject, String methodName, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        if (invokableObject instanceof Annotation) {
            method = ((Annotation) invokableObject).annotationType().getDeclaredMethod(methodName, methodParamTypes);
        } else {
            method = invokableObject.getClass().getDeclaredMethod(methodName, methodParamTypes);
        }
        method.setAccessible(true);
        return method.invoke(invokableObject, argsForInvoke);
    }

    public static Object invokeMethodAnyway(Object invokableObject, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        if (invokableObject instanceof Annotation) {
            method = ((Annotation) invokableObject).annotationType().getDeclaredMethod(methodName);
        } else {
            method = invokableObject.getClass().getDeclaredMethod(methodName);
        }
        method.setAccessible(true);
        return method.invoke(invokableObject);
    }
}
