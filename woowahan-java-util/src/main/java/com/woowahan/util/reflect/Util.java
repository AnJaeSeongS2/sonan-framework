package com.woowahan.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Jaeseong on 2021/04/02
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class Util {

    public static Object invokeMethod(Object invokableObject, String methodName, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = invokableObject.getClass().getDeclaredMethod("createBean", methodParamTypes);
        method.setAccessible(true);
        return method.invoke(invokableObject, argsForInvoke);
    }
}
