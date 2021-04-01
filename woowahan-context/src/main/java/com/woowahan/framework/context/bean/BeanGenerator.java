package com.woowahan.framework.context.bean;

import com.woowahan.framework.context.bean.throwable.BeanNotFoundException;

/**
 * BeanGenerator는 getBean을 통해 생성된 Bean을 반환한다.
 *
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@FunctionalInterface
public interface BeanGenerator {

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * @see BeanIdentifier
     * @param id the BeanIdentifier of the bean to retrieve
     * @return an instance of the bean
     */
    Object getBean(BeanIdentifier id) throws BeanNotFoundException;
}
