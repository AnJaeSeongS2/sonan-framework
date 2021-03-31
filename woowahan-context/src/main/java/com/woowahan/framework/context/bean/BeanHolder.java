package com.woowahan.framework.context.bean;

import com.woowahan.util.annotation.NotNull;

/**
 * BeanHolder는 식별되는 name에 해당하는 bean이 있으면 그것을 반환, 아니면 생성한다.
 *
 * Created by Jaeseong on 2021/03/31
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@FunctionalInterface
public interface BeanHolder {

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>This method allows a BeanHolder to be used as a replacement for the
     * Singleton or Prototype design pattern. Callers may retain references to
     * returned objects in the case of Singleton beans.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * <p>Will ask the parent BeanHolder if the bean cannot be found in this BeanHolder instance.
     * @param name the name of the bean to retrieve
     * @return an instance of the bean
     */
    @NotNull
    Object getBean(String name);
}
