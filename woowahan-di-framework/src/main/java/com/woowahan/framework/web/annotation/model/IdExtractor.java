package com.woowahan.framework.web.annotation.model;

import com.woowahan.util.reflect.ReflectionUtil;

import java.lang.annotation.Annotation;

/**
 * Model으로부터 ID를 추출한다.
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class IdExtractor {
    public static Object getId(Object targetModel) {
        try {
            return ReflectionUtil.getFieldAnyway(targetModel, (field) -> {
                for (Annotation declaredAnnotation : field.getDeclaredAnnotations()) {
                    if (declaredAnnotation instanceof Id) {
                        return true;
                    }
                }
                return false;
            });
        } catch (Exception e) {
            return (Integer) targetModel.hashCode();
        }
    }

    public static Class<?> getIdClass(Object targetModel) {
        return getIdClass(targetModel.getClass());
    }

    public static Class<?> getIdClass(Class<?> targetModelClass) {
        try {
            return ReflectionUtil.getFieldMetaAnyway(targetModelClass, (field) -> {
                for (Annotation declaredAnnotation : field.getDeclaredAnnotations()) {
                    if (declaredAnnotation instanceof Id) {
                        return true;
                    }
                }
                return false;
            }).getType();
        } catch (NoSuchFieldException e) {
            return Integer.class;
        }
    }
}
