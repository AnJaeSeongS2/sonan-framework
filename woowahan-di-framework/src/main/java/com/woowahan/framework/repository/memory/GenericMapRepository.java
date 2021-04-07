package com.woowahan.framework.repository.memory;

import com.woowahan.framework.throwable.FailedDeleteException;
import com.woowahan.framework.throwable.FailedGetException;
import com.woowahan.framework.throwable.FailedPostException;
import com.woowahan.framework.throwable.FailedPutException;
import com.woowahan.framework.web.annotation.model.Id;
import com.woowahan.util.reflect.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO: com.woowahan.framework.context.repository를 module화 할 것.
 * Repository의 지원 vendor중에, memory에 저장하는 기본 구현체이다.
 *
 * ElementClazz 는 repo에 저장할 element의 Model 클래스이다.
 * @see com.woowahan.framework.web.annotation.model.Id 가 ElementClazz 클래스의 Field의에 붙어있다면, 해당 field를 Id로 활용한다.
 * com.woowahan.framework.web.annotation.model.Id 가 없다면, ElementClazz 객체의 hashCode 값을 id로 활용한다.
 *
 * @Thread-safe
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJGenericMapRepositoryaeSeongS2
 */
public class GenericMapRepository<ElementClazz> {
    private Map<Object, ElementClazz> dataMap;

    public GenericMapRepository() {
        dataMap = new ConcurrentHashMap();
    }

    public void post(ElementClazz element) throws FailedPostException {
        if (element == null) {
            throw new FailedPostException("cannot post null element.");
        }

        Object id;
        try {
            id = ReflectionUtil.getFieldAnyway(element, (field) -> {
                for (Annotation declaredAnnotation : field.getDeclaredAnnotations()) {
                    if (declaredAnnotation instanceof Id) {
                        return true;
                    }
                }
                return false;
            });
        } catch (NoSuchFieldException e) {
            // Id annotation으로 지정된 field가 없는 케이스. hashCode를 id로 사용하게된다.
            id = element.hashCode();
        } catch (Throwable e) {
            throw new FailedPostException("element id is not exists on this element.", e);
        }
        if (id == null) {
            throw new FailedPostException("this element's element id is null.");
        }

        if (dataMap.containsKey(id)) {
            throw new FailedPostException(String.format("this element already exists on Repository. element id : %s",id));
        }
        dataMap.put(id, element);
    }

    public void put(ElementClazz element) throws FailedPutException {
        if (element == null) {
            throw new FailedPutException("cannot put null element.");
        }

        Object id;
        try {
            id = ReflectionUtil.getFieldAnyway(element, (field) -> {
                for (Annotation declaredAnnotation : field.getDeclaredAnnotations()) {
                    if (declaredAnnotation instanceof Id) {
                        return true;
                    }
                }
                return false;
            });
        } catch (Throwable e) {
            throw new FailedPutException("element id is not exists on this element.", e);
        }
        if (id == null) {
            throw new FailedPutException("this element's element id is null.");
        }

        if (!dataMap.containsKey(id)) {
            throw new FailedPutException(String.format("this element id is not exists on Repository. element id : %s",id));
        }

        dataMap.put(id, element);
    }


    public ElementClazz delete(Object elementId) throws FailedDeleteException {
        if (elementId == null) {
            throw new FailedDeleteException("cannot delete element by null element id.");
        }

        if (!dataMap.containsKey(elementId)) {
            throw new FailedDeleteException(String.format("not exists element about this element id : %s", elementId));
        }

        return dataMap.remove(elementId);
    }

    public ElementClazz get(Object elementId) throws FailedGetException {
        if (elementId == null) {
            throw new FailedGetException("cannot get element by null element id.");
        }

        if (!dataMap.containsKey(elementId)) {
            throw new FailedGetException(String.format("not exists element about this element id : %s", elementId));
        }

        return dataMap.get(elementId);
    }
}
