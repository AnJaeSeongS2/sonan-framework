package com.woowahan.framework.web.protocol;

import com.woowahan.framework.web.throwable.FailedResolveException;

/**
 * 입력으로 주어진 messageBeforeResolve 값을 resolve해서 새로운 Message객체를 반환하는 Resolver이다.
 *
 * Created by Jaeseong on 2021/04/11
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@FunctionalInterface
public interface MessageResolver {
    Message resolve(Message messageBeforeResolve) throws FailedResolveException;
}
