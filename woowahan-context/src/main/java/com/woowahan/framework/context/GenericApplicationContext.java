package com.woowahan.framework.context;

import com.woowahan.framework.context.bean.BeanDefinition;
import com.woowahan.framework.context.bean.BeanIdentifier;
import com.woowahan.framework.context.bean.throwable.BeanCreationFailedException;
import com.woowahan.framework.context.bean.throwable.BeanDefinitionNotRegisteredException;
import com.woowahan.framework.context.bean.throwable.BeanNotFoundException;
import com.woowahan.logback.support.Markers;
import com.woowahan.util.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(GenericApplicationContext.class);
    @Nullable
    private final ApplicationContext parent;
    private final Set<BeanDefinition> beanDefs;
    private final Map<BeanIdentifier, BeanDefinition> beanIdToDef;
    private final ClassLoader beanClassLoader;

    // refresh 대상 ////////////
    private Map<BeanIdentifier, Object> singletonBeans;
    ///////////////////////////

    /**
     * ApplicationContext를 저장하는 vendor별 contextHolder
     */
    private final T contextHolder;

    /**
     * vendor의 contextHolder에서 applicationContext를 추출하는 function.
     */
    private final Function<T, ApplicationContext> getterRootApplicationContext;

    /**
     * @param parent
     * @param contextHolder vendor별 applicationContext를 저장할 객체.
     * @param getterRootApplicationContext contextHolder로부터 ApplicationContext를 추출하는 Function
     */
    public GenericApplicationContext(@Nullable ApplicationContext parent, T contextHolder, Function<T, ApplicationContext> getterRootApplicationContext) {
        if (logger.isTraceEnabled())
            logger.trace(Markers.LIFE_CYCLE.get(), "try init GenericApplicationContext...");
        this.parent = parent;
        this.contextHolder = contextHolder;
        this.getterRootApplicationContext = getterRootApplicationContext;

        this.beanDefs = Collections.newSetFromMap(new ConcurrentHashMap<BeanDefinition, Boolean>());
        this.beanIdToDef = new ConcurrentHashMap<BeanIdentifier, BeanDefinition>();
        this.singletonBeans = new ConcurrentHashMap<BeanIdentifier, Object>();

        this.beanClassLoader = Thread.currentThread().getContextClassLoader();
        if (logger.isDebugEnabled())
            logger.debug(Markers.LIFE_CYCLE.get(), "success init GenericApplicationContext as " + this);
    }

    @Override
    public Object getBean(BeanIdentifier id) throws BeanNotFoundException {
        if (logger.isTraceEnabled())
            logger.trace(Markers.LIFE_CYCLE.get(), "try getBean...");
        Object bean;
        if (beanIdToDef.containsKey(id)) {
            // id에 해당하는 definition이 등록돼있는 케이스.
            try {
                bean = getBean(beanIdToDef.get(id));
            } catch (BeanCreationFailedException e) {
                throw new BeanNotFoundException(e, "[INNER]" + e.getMessage());
            }
        } else {
            // id에 해당하는 definition이 등록돼있지 않은 케이스.
            if (getParent() == null) {
                throw new BeanNotFoundException("The ApplicationContext is not have BeanDefinition matched with current BeanIdentifier. current ApplicationContext: " + toString() + ", current BeanIdentifier: " + id.toString());
            }
            bean = getParent().getBean(id);
        }
        if (logger.isTraceEnabled())
            logger.trace(Markers.LIFE_CYCLE.get(), "success getBean as " + bean);
        return bean;
    }

    @Override
    synchronized public void register(BeanDefinition definition) throws BeanDefinitionNotRegisteredException {
        if (beanIdToDef.containsKey(definition.getId())) {
            throw new BeanDefinitionNotRegisteredException(definition.getId().toString(), definition.toString());
        }

        beanDefs.add(definition);
        beanIdToDef.put(definition.getId(), definition);
        if (logger.isTraceEnabled())
            logger.trace(Markers.LIFE_CYCLE.get(), "finish register BeanDefinition as " + definition);
    }

    /**
     * applicationContext를 보관중인 vendor의 contextHolder를 반환.
     *
     * @return applicationContext를 보관중인 vendor의 contextHolder를 반환. ex) ServletContext
     */
    public T getContextHolder() {
        return this.contextHolder;
    }

    @Override
    @Nullable
    public ApplicationContext getParent() {
        return this.parent;
    }

    @Override
    public ApplicationContext getRoot() {
        return this.getterRootApplicationContext.apply(this.contextHolder);
    }

    private Object createBean(BeanDefinition definition) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (logger.isTraceEnabled())
            logger.trace(Markers.LIFE_CYCLE.get(), "try createBean...");
        Object bean = this.beanClassLoader.loadClass(definition.getBeanClassCanonicalName()).newInstance();
        if (logger.isTraceEnabled())
            logger.trace(Markers.LIFE_CYCLE.get(), "success createBean as " + bean);
        return bean;
    }

    private Object getBean(BeanDefinition definition) throws BeanCreationFailedException {
        try {
            Object bean;
            switch (definition.getScope()) {
                case SINGLETON:
                    if (singletonBeans.containsKey(definition.getId())) {
                        return singletonBeans.get(definition.getId());
                    }
                    bean = createBean(definition);
                    singletonBeans.put(definition.getId(), bean);
                    break;
                case PROTOTYPE:
                default:
                    // create bean always.
                    bean = createBean(definition);
                    break;
            }
            return bean;
        } catch (Exception e) {
            throw new BeanCreationFailedException("The ApplicationContext is can't create Bean with this BeanDefinition. current ApplicationContext: " + toString() + ", current BeanIdentifier: " + definition.getId().toString(), e);
        }
    }


    /**
     * 현재 context에서 보관 중인 혹은 새로 보관할 bean들을 definition기반으로 재 할당한다.
     * 기본 사용처는 application 초기 기동시 사용된다.
     * @see com.woowahan.framework.context.annotation.Scope Singleton: 새로 생김.
     */
    public void refreshInstances() throws BeanCreationFailedException {
        this.singletonBeans = new ConcurrentHashMap<BeanIdentifier, Object>();

        for (BeanDefinition beanDef : beanDefs) {
            if (beanDef.isSingleton()) {
                getBean(beanDef);
            }
        }
    }
}
