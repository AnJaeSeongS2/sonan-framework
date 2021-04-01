package com.woowahan.framework.context.bean;

import com.woowahan.framework.context.bean.throwable.BeanException;

/**
 * @see BeanDefinition 을 보관하고 관리하는 주체.
 *
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@FunctionalInterface
public interface BeanDefinitionRegistry {

    /**
     * @see BeanDefinition 을 관리되게 등록한다.
     *
     * @param definition
     * @throws BeanException
     */
    void register(BeanDefinition definition) throws BeanException;
}
