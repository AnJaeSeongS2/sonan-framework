package com.woowahan.framework.repository;

import com.woowahan.framework.throwable.FailedDeleteException;
import com.woowahan.framework.throwable.FailedGetException;

import java.util.List;

/**
 * element all 대상으로 진행가능한 api가 추가됨.
 *
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public interface RepositoryAccessibleAll<ElementClazz> extends Repository<ElementClazz> {

    List<ElementClazz> getAll() throws FailedGetException;

    void deleteAll() throws FailedDeleteException;
}
