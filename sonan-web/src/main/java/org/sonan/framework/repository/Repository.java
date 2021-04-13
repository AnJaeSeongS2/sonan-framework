package org.sonan.framework.repository;

import org.sonan.framework.throwable.FailedDeleteException;
import org.sonan.framework.throwable.FailedGetException;
import org.sonan.framework.throwable.FailedPostException;
import org.sonan.framework.throwable.FailedPutException;

/**
 * Repository 최상위 인터페이스.
 * CRUD작업을 할 수 있는 저장소.
 *
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public interface Repository<ElementClazz> {

    void post(ElementClazz element) throws FailedPostException;

    void put(ElementClazz element) throws FailedPutException;

    ElementClazz delete(Object elementId) throws FailedDeleteException;

    ElementClazz get(Object elementId) throws FailedGetException;

    boolean contains(Object elementId) throws FailedGetException;
}
