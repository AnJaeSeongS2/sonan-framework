package org.sonan.framework.repository.memory;

import org.sonan.framework.repository.RepositoryAccessibleAll;
import org.sonan.framework.throwable.FailedDeleteException;
import org.sonan.framework.throwable.FailedGetException;
import org.sonan.framework.throwable.FailedPostException;
import org.sonan.framework.throwable.FailedPutException;
import org.sonan.framework.web.annotation.model.Id;
import org.sonan.framework.web.annotation.model.IdAutoChangeableIfExists;
import org.sonan.framework.web.model.id.IdAutoChanger;
import org.sonan.framework.web.model.id.IdExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * TODO: org.sonan.framework.context.repository를 module화 할 것.
 * Repository의 지원 vendor중에, memory에 저장하는 기본 구현체이다.
 *
 * ElementClazz 는 repo에 저장할 element의 Model 클래스이다.
 * @see org.sonan.framework.web.annotation.model.Id 가 ElementClazz 클래스의 Field의에 붙어있다면, 해당 field를 Id로 활용한다.
 * org.sonan.framework.web.annotation.model.Id 가 없다면, ElementClazz 객체의 hashCode 값을 id로 활용한다.
 *
 * data가 삭제됐더라도, 이후 그 data의 id는 다른 생성에 재사용되지 않는다. (차후 로깅 처리 편의를 위해)
 *
 * @Thread-safe
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJGenericMapRepositoryaeSeongS2
 */
@SuppressWarnings("unchecked")
public class GenericMapRepository<ElementClazz> implements RepositoryAccessibleAll<ElementClazz> {
    private static final Logger logger = LoggerFactory.getLogger(GenericMapRepository.class);

    private Map<Object, ElementClazz> dataMap;
    private Object latestPostedId = null;

    public GenericMapRepository() {
        dataMap = new ConcurrentHashMap();
    }

    /**
     * id에 해당하는 data가 이미 repo에 있을 경우, 실패한다.
     * 만약 element 의 id가 IdAutoChanger annotation을 단 field였다면, 이미 id에 해당하는 data가 있더라도 id관련된 change로직을 돌려 재 시도 한다.
     *
     * @param element
     * @throws FailedPostException
     */
    public void post(ElementClazz element) throws FailedPostException {
        if (element == null) {
            throw new FailedPostException("cannot post null element.");
        }
        Object id = null;
        try {
            Map.Entry<Object, Field> idAndField = IdExtractor.getIdAndField(element);
            id = idAndField.getKey();
            if (id == null) {
                id = latestPostedId;
                IdExtractor.setId(element, latestPostedId);
            }

            for (Annotation annotation : idAndField.getValue().getAnnotations()) {
                if (annotation instanceof IdAutoChangeableIfExists) {
                    while (id == null || contains(id)) {
                        // id값이 없거나, 이미 id에 매칭되는 data가 존재하는 경우, id를 바꿔 다시 post요청을 시도한다.
                        if (logger.isWarnEnabled())
                            logger.warn(String.format("this Model's id is null or id already contains on repo, so try to change id automatically. before id : %s", id));
                        if (element instanceof IdAutoChanger) {
                            id = ((IdAutoChanger) element).changeIdAuto();
                        } else {
                            if (logger.isWarnEnabled())
                                logger.warn(String.format("this Model is not inherit idAutoChanger. so, id is not changed. element's class : %s", element.getClass()));
                            break;
                        }
                    }
                    latestPostedId = id;
                    break;
                }
                if (annotation instanceof Id) {
                    id = idAndField.getKey();
                    latestPostedId = id;
                    break;
                }
            }
        } catch (NoSuchFieldException e) {
            // Id | IdAutoChangeableIfExists annotation으로 지정된 field가 없는 케이스. hashCode를 id로 사용하게된다.
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
            id = IdExtractor.getId(element);
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

    public boolean contains(Object elementId) {
        return dataMap.containsKey(elementId);
    }

    /**
     * TODO: GenericMapRepository입장에서 전체 element를 보여주는 기능은 Map을 List로 재 가공하므로 퍼포먼스 문제가 있다. GenericListRepository 추가 생성 요망.
     * @return
     */
    public List<ElementClazz> getAll() throws FailedGetException {
        return dataMap.values().stream().collect(Collectors.toList());
    }

    public void deleteAll() throws FailedDeleteException {
        dataMap.clear();
    }
}
