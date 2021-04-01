package com.woowahan.framework.context;

import com.woowahan.framework.context.bean.BeanDefinition;
import com.woowahan.framework.context.bean.BeanIdentifier;
import com.woowahan.framework.context.bean.throwable.BeanDefinitionNotRegisteredException;
import com.woowahan.framework.context.bean.throwable.BeanException;
import com.woowahan.framework.context.bean.throwable.BeanFailedCreationException;
import com.woowahan.util.annotation.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * ApplicationContext의 기본 구현체.
 * T는 ApplicationContext를 저장할 vendor의 ContextHolder이다. (ex: ServletContext)
 * 현 ApplicationContext내에서 BeanDefinition을 관리한다.
 * bean 생성도 ApplicationContext가 가진 beanClassLoader를 통해서 진행한다.
 *
 * @thread-safe
 * Created by Jaeseong on 2021/03/31
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class GenericApplicationContext<T> extends ApplicationContext {
    private final ApplicationContext parent;

    @NotNull
    private final Map<BeanIdentifier, Object> singletonBeans;
    @NotNull
    private final Map<BeanIdentifier, BeanDefinition> beanIdToDef;
    @NotNull
    private final Set<BeanDefinition> beanDefs;

    /**
     * ApplicationContext를 저장하는 vendor별 contextHolder
     */
    @NotNull
    private final T contextHolder;

    @NotNull
    private final ClassLoader beanClassLoader;

    /**
     * vendor의 contextHolder에서 applicationContext를 추출하는 function.
     */
    private final Function<T, ApplicationContext> getterRootApplicationContext;

    /**
     * @param parent
     * @param contextHolder vendor별 applicationContext를 저장할 객체.
     * @param getterRootApplicationContext contextHolder로부터 ApplicationContext를 추출하는 Function
     */
    public GenericApplicationContext(ApplicationContext parent, @NotNull T contextHolder, @NotNull Function<T, ApplicationContext> getterRootApplicationContext) {
        this.parent = parent;
        this.contextHolder = contextHolder;
        this.getterRootApplicationContext = getterRootApplicationContext;

        this.beanDefs = Collections.newSetFromMap(new ConcurrentHashMap<BeanDefinition, Boolean>());
        this.beanIdToDef = new ConcurrentHashMap<BeanIdentifier, BeanDefinition>();
        this.singletonBeans = new ConcurrentHashMap<BeanIdentifier, Object>();

        this.beanClassLoader = Thread.currentThread().getContextClassLoader();
    }

    @Override
    @NotNull
    public Object getBean(@NotNull BeanIdentifier id) {
        if (beanIdToDef.containsKey(id)) {
            // id에 해당하는 definition이 등록돼있는 케이스.

            Object bean;
            BeanDefinition definition = beanIdToDef.get(id);
            switch (definition.getScope()) {
                case Singleton:
                    if (singletonBeans.containsKey(id)) {
                        return singletonBeans.get(id);
                    }
                    //TODO: createBean
                    bean = new RuntimeException("TODO: createBean");
                    singletonBeans.put(id, bean);
                    break;
                case Prototype:
                default:
                    // create bean always.
                    //TODO: createBean
                    bean = new RuntimeException("TODO: createBean");
                    break;
            }
            return bean;
        } else {
            // id에 해당하는 definition이 등록돼있지 않은 케이스.

            if (getParent() == null) {
                throw new BeanFailedCreationException("The ApplicationContext is not have BeanDefinition matched with current BeanIdentifier. current ApplicationContext: " + toString() + ", current BeanIdentifier: " + id.toString());
            }
            return getParent().getBean(id);
        }
    }

    @Override
    synchronized public void register(BeanDefinition definition) throws BeanException {
        if (beanIdToDef.containsKey(definition.getId())) {
            throw new BeanDefinitionNotRegisteredException(definition.getId().toString(), definition.toString());
        }

        beanDefs.add(definition);
        beanIdToDef.put(definition.getId(), definition);
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
    public ApplicationContext getParent() {
        return this.parent;
    }

    @Override
    public ApplicationContext getRoot() {
        return this.getterRootApplicationContext.apply(this.contextHolder);
    }
}
