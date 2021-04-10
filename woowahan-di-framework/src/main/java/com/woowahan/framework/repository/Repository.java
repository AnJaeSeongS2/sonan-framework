package com.woowahan.framework.repository;

import com.woowahan.framework.throwable.FailedDeleteException;
import com.woowahan.framework.throwable.FailedGetException;
import com.woowahan.framework.throwable.FailedPostException;
import com.woowahan.framework.throwable.FailedPutException;

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
