package com.woowahan.util.reflect;

import com.woowahan.util.reflect.throwable.NoSuchMethodRuntimeException;
import com.woowahan.util.reflect.throwable.ReflectiveOperationRuntimeException;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Created by Jaeseong on 2021/04/02
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class ReflectionUtil {

    /**
     * ********************************************************************************
     * Field Utils
     * *********************************************************************************
     */

    public static void setField(Object settableTarget, String fieldName, Object fieldObject) throws NoSuchFieldException, IllegalAccessException {
        settableTarget.getClass().getField(fieldName).set(settableTarget, fieldObject);
    }

    public static Object getField(Object gettableTarget, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return gettableTarget.getClass().getField(fieldName).get(gettableTarget);
    }

    /**
     * weakness to capsulization, multithread programming
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     *
     * @param target
     * @param fieldName
     * @param fieldObject
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void setFieldAnyway(Object target, String fieldName, Object fieldObject) throws NoSuchFieldException, IllegalAccessException {
        Field field;
        try {
            field = getFieldMetaAnyway(target.getClass(), (innerField) -> innerField.getName().equals(fieldName));
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException(String.format("%s, fieldName : %s", e.getMessage(), fieldName));
        }

        synchronized (field) {
            boolean savedAccessible = field.isAccessible();
            field.setAccessible(true);
            field.set(target, fieldObject);
            field.setAccessible(savedAccessible);
        }
    }

    /**
     * weakness to capsulization, multithread programming
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     *
     * @param target
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getFieldAnyway(Object target, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        try {
            return getFieldAnyway(target, (innerField) -> innerField.getName().equals(fieldName));
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException(String.format("%s, fieldName : %s", e.getMessage(), fieldName));
        }
    }

    /**
     * weakness to capsulization, multithread programming
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     *
     * @param target
     * @return filterForFindFirst
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getFieldAnyway(Object target, Predicate<Field> filterForFindFirst) throws NoSuchFieldException, IllegalAccessException {
        Field field = getFieldMetaAnyway(target.getClass(), filterForFindFirst);

        Object result;
        synchronized (field) {
            boolean savedAccessible = field.isAccessible();
            field.setAccessible(true);
            result = field.get(target);
            field.setAccessible(savedAccessible);
        }
        return result;
    }

    /**
     * target 에서 filterForFindFirst 에 매칭되는 field가 존재하면 반환. superclass도 search한다.
     * @param target
     * @param filterForFindFirst
     * @return
     * @throws NoSuchFieldException
     */
    public static Field getFieldMetaAnyway(Class<?> target, Predicate<Field> filterForFindFirst) throws NoSuchFieldException {
        try {
            for (Field declaredField : target.getDeclaredFields()) {
                if (filterForFindFirst.test(declaredField)) {
                    return declaredField;
                }
            }
            if (target.getSuperclass() == null) {
                throw new NoSuchFieldException("target : " + target);
            }
            return getFieldMetaAnyway(target.getSuperclass(), filterForFindFirst);
        } catch (ReflectiveOperationRuntimeException e) {
            throw new NoSuchFieldException(e.getMessage());
        } catch (Throwable e) {
            throw new NoSuchFieldException("target : " + target);
        }
    }

    /**
     * ********************************************************************************
     * Method Utils
     * *********************************************************************************
     */

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
     * weakness to capsulization, multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     *
     * @param target
     * @param methodName
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object invokeMethodAnyway(Object target, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethodAnyway(target, methodName, new Class[0], new Object[0]);
    }

    /**
     * weakness to capsulization, multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     *
     * @param target
     * @param methodName
     * @param methodParamTypes
     * @param argsForInvoke
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object invokeMethodAnyway(Object target, String methodName, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethodAnyway(target, (innerMethod) -> {
            if (innerMethod.getName().equals(methodName) && Arrays.equals(methodParamTypes, innerMethod.getParameterTypes())) {
                if (!Modifier.isStatic(innerMethod.getModifiers())) {
                    return true;
                }
                throw new NoSuchMethodRuntimeException(String.format("method : [%s] is not found at target : [%s]", methodName, target.getClass()));
            }
            return false;
        }, methodParamTypes, argsForInvoke);
    }

    /**
     * weakness to capsulization, multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     *
     * @param target
     * @param filterForFindFirst
     * @param methodParamTypes
     * @param argsForInvoke
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object invokeMethodAnyway(Object target, Predicate<Method> filterForFindFirst, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = getMethodMetaAnyway(target.getClass(), filterForFindFirst);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException(String.format("%s, methodName : %s, methodParamTypes : %s", e.getMessage(), filterForFindFirst, methodParamTypes));
        }

        Object result;
        synchronized (method) {
            boolean savedAccessible = method.isAccessible();
            method.setAccessible(true);
            result = method.invoke(target, argsForInvoke);
            method.setAccessible(savedAccessible);
        }
        return result;
    }

    public static Object invokeStaticMethod(Class<?> target, String methodName, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return target.getMethod(methodName, methodParamTypes).invoke(null, argsForInvoke);
    }

    public static Object invokeStaticMethod(Class<?> target, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeStaticMethod(target, methodName, new Class[0], new Object[0]);
    }

    /**
     * weakness to capsulization, multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     *
     * @param target
     * @param methodName
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object invokeStaticMethodAnyway(Class<?> target, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeStaticMethodAnyway(target, methodName, new Class[0], new Object[0]);
    }

    /**
     * weakness to capsulization, multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     *
     * @param target
     * @param methodName
     * @param methodParamTypes
     * @param argsForInvoke
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object invokeStaticMethodAnyway(Class<?> target, String methodName, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeStaticMethodAnyway(target, (innerMethod) -> {
            if (innerMethod.getName().equals(methodName) && Arrays.equals(methodParamTypes, innerMethod.getParameterTypes())) {
                if (Modifier.isStatic(innerMethod.getModifiers())) {
                    return true;
                }
                throw new NoSuchMethodRuntimeException(String.format("static method : [%s] is not found at target : [%s].", methodName, target));
            }
            return false;
        }, methodParamTypes, argsForInvoke);
    }

    /**
     * weakness to capsulization, multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     *
     * @param target
     * @param filterForFindFirst
     * @param methodParamTypes
     * @param argsForInvoke
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object invokeStaticMethodAnyway(Class<?> target, Predicate<Method> filterForFindFirst, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = getMethodMetaAnyway(target, filterForFindFirst);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException(String.format("%s, methodName : %s, methodParamTypes : %s", e.getMessage(), filterForFindFirst, methodParamTypes));
        }

        Object result;
        synchronized (method) {
            boolean savedAccessible = method.isAccessible();
            method.setAccessible(true);
            result = method.invoke(null, argsForInvoke);
            method.setAccessible(savedAccessible);
        }
        return result;
    }

    /**
     * target에서 filterForFindFirst 에 매칭되는 method가 존재하면 반환. superclass도 search한다.
     * @param target
     * @param filterForFindFirst
     * @return
     * @throws NoSuchFieldException
     */
    public static Method getMethodMetaAnyway(Class<?> target, Predicate<Method> filterForFindFirst) throws NoSuchMethodException {
        try {
            for (Method declaredMethod : target.getDeclaredMethods()) {
                if (filterForFindFirst.test(declaredMethod)) {
                    return declaredMethod;
                }
            }
            if (target.getSuperclass() == null) {
                throw new NoSuchMethodException("target : " + target);
            }
            return getMethodMetaAnyway(target.getSuperclass(), filterForFindFirst);
        } catch (ReflectiveOperationRuntimeException e) {
            throw new NoSuchMethodException(e.getMessage());
        } catch (Throwable e) {
            throw new NoSuchMethodException("target : " + target);
        }
    }

    /**
     * ********************************************************************************
     * Constructor Utils
     * *********************************************************************************
     */

    public static Object newInstance(Class<?> target, Class<?>[] methodParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        return target.getConstructor(methodParamTypes).newInstance(argsForInvoke);
    }

    public static Object newInstance(Class<?> target) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        return newInstance(target, new Class[0], new Object[0]);
    }

    /**
     * weakness to capsulization, multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     *
     * @param target
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object newInstanceAnyway(Class<?> target) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        return newInstanceAnyway(target, new Class[0], new Object[0]);
    }

    /**
     * weakness to capsulization, multithread programming.
     * so, jdk 1.9 deprecated about setAccessible(boolean).
     *
     * @param target
     * @param constructorParamTypes
     * @param argsForInvoke
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object newInstanceAnyway(Class<?> target, Class<?>[] constructorParamTypes, Object... argsForInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Constructor ctor;
        try {
            ctor = getConstructorMetaAnyway(target, (innerConstructor) -> {
                if (Arrays.equals(constructorParamTypes, innerConstructor.getParameterTypes())) {
                    return true;
                }
                return false;
            });
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException(String.format("%s, constructorParamTypes : %s", e.getMessage(), constructorParamTypes));
        }

        Object result;
        synchronized (ctor) {
            boolean savedAccessible = ctor.isAccessible();
            ctor.setAccessible(true);
            result = ctor.newInstance(argsForInvoke);
            ctor.setAccessible(savedAccessible);
        }
        return result;
    }

    /**
     * target에서 filterForFindFirst 에 매칭되는 method가 존재하면 반환. superclass는 search하지 않는다. superclass로 반환해봤자 casting할 수 없다.
     * @param target
     * @param filterForFindFirst
     * @return
     * @throws NoSuchFieldException
     */
    public static Constructor getConstructorMetaAnyway(Class<?> target, Predicate<Constructor> filterForFindFirst) throws NoSuchMethodException {
        try {
            for (Constructor declared : target.getDeclaredConstructors()) {
                if (filterForFindFirst.test(declared)) {
                    return declared;
                }
            }
        } catch (ReflectiveOperationRuntimeException e) {
            throw new NoSuchMethodException(e.getMessage());
        } catch (Throwable e) {
            throw new NoSuchMethodException("target : " + target);
        }
        throw new NoSuchMethodException("target : " + target);
    }
}
