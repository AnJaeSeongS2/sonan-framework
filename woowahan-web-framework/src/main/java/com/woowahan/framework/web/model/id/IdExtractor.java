package com.woowahan.framework.web.model.id;

import com.woowahan.framework.web.annotation.model.IdAutoChangeableIfExists;
import com.woowahan.framework.web.annotation.model.Id;
import com.woowahan.util.reflect.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.Map;

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
                    if (declaredAnnotation instanceof Id || declaredAnnotation instanceof IdAutoChangeableIfExists) {
                        return true;
                    }
                }
                return false;
            });
        } catch (Exception e) {
            return (Integer) targetModel.hashCode();
        }
    }

    public static void setId(Object targetModel, Object id) {
        try {
            Field idField = ReflectionUtil.getFieldMetaAnyway(targetModel.getClass(), (field) -> {
                for (Annotation declaredAnnotation : field.getDeclaredAnnotations()) {
                    if (declaredAnnotation instanceof Id || declaredAnnotation instanceof IdAutoChangeableIfExists) {
                        return true;
                    }
                }
                return false;
            });
            ReflectionUtil.setFieldAnyway(targetModel, idField, id);
        } catch (Exception e) {
        }
    }

    public static Map.Entry<Object, Field> getIdAndField(Object targetModel) throws IllegalAccessException, NoSuchFieldException {
        Field field = getIdField(targetModel);
        Object id = ReflectionUtil.getFieldAnyway(targetModel, field);
        return new AbstractMap.SimpleEntry<>(id, field);

    }

    public static Class<?> getIdClass(Object targetModel) {
        return getIdClass(targetModel.getClass());
    }

    public static Class<?> getIdClass(Class<?> targetModelClass) {
        try {
            return getIdField(targetModelClass).getType();
        } catch (NoSuchFieldException e) {
            return Integer.class;
        }
    }

    public static Field getIdField(Object targetModel) throws NoSuchFieldException {
        return getIdField(targetModel.getClass());
    }

    public static Field getIdField(Class<?> targetModelClass) throws NoSuchFieldException {
        return ReflectionUtil.getFieldMetaAnyway(targetModelClass, (field) -> {
            for (Annotation declaredAnnotation : field.getDeclaredAnnotations()) {
                if (declaredAnnotation instanceof Id || declaredAnnotation instanceof IdAutoChangeableIfExists) {
                    return true;
                }
            }
            return false;
        });
    }

}
