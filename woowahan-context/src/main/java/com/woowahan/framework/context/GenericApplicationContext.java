package com.woowahan.framework.context;

import com.woowahan.util.annotation.NotNull;
import com.woowahan.framework.context.bean.BeanHolder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * ApplicationContext의 기본 구현체.
 * T는 ApplicationContext를 저장할 vendor의 ContextHolder이다. (ex: ServletContext)
 *
 * Created by Jaeseong on 2021/03/31
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class GenericApplicationContext<T> implements ApplicationContext, BeanHolder {
    private ApplicationContext parent;
    private ConcurrentHashMap<String, Object> beanMap;

    /**
     * ApplicationContext를 저장하는 vendor별 contextHolder
     */
    private T contextHolder;

    /**
     * vendor의 contextHolder에서 applicationContext를 추출하는 function.
     */
    private Function<T, ApplicationContext> getterRootApplicationContext;

    /**
     * @param parent
     * @param contextHolder vendor별 applicationContext를 저장할 객체.
     * @param getterRootApplicationContext contextHolder로부터 ApplicationContext를 추출하는 Function
     */
    public GenericApplicationContext(ApplicationContext parent, @NotNull T contextHolder, @NotNull Function<T, ApplicationContext> getterRootApplicationContext) {
        this.parent = parent;
        this.contextHolder = contextHolder;
        this.getterRootApplicationContext = getterRootApplicationContext;
    }


    @Override
    @NotNull
    public Object getBean(@NotNull String name) {
        //TODO
        return null;
    }

    /**
     * applicationContext를 보관중인 vendor의 contextHolder를 반환.
     *
     * @return applicationContext를 보관중인 vendor의 contextHolder를 반환. ex) ServletContext
     */
    @NotNull
    public T getContextHolder() {
        return this.contextHolder;
    }

    @Override
    @NotNull
    public ApplicationContext getParent() {
        return this.parent;
    }

    @Override
    public ApplicationContext getRoot() {
        return this.getterRootApplicationContext.apply(this.contextHolder);
    }
}
