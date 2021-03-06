package org.sonan.framework.context;

import org.sonan.framework.context.annotation.*;
import org.sonan.framework.context.bean.BeanDefinition;
import org.sonan.framework.context.bean.BeanIdentifier;
import org.sonan.framework.context.bean.BeanManager;
import org.sonan.framework.context.bean.lifecycle.ControllerLifecycleInvocation;
import org.sonan.framework.context.bean.throwable.BeanCreationFailedException;
import org.sonan.framework.context.bean.throwable.BeanDefinitionNotRegisteredException;
import org.sonan.framework.context.bean.throwable.BeanNotFoundException;
import org.sonan.logback.support.Markers;
import org.sonan.util.annotation.Nullable;
import org.sonan.util.reflect.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
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
    private static final String REGIST_WITHOUT_ANNOTATION = "REGIST_WITHOUT_ANNOTATION";

    @Nullable
    private final ApplicationContext parent;
    // Service, Controller 들의 classCanonicalName 이 key로 쓰인다.
    // "" key에는 Annotation기반 외의 방법으로 등록된 beanDefintion이 들어간다.
    private final Map<String, Set<BeanDefinition>> beanDefs;
    private final Map<BeanIdentifier, BeanDefinition> beanIdToDef;
    private final ClassLoader beanClassLoader;

    // refresh 대상 ////////////
    private Map<BeanIdentifier, Object> singletonBeans;
    private List<Object> singletonBeansControllerLifecycleInvocations;
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
        if (logger.isTraceEnabled(Markers.LIFE_CYCLE.get()))
            logger.trace(Markers.LIFE_CYCLE.get(), "try init GenericApplicationContext...");
        this.parent = parent;
        this.contextHolder = contextHolder;
        this.getterRootApplicationContext = getterRootApplicationContext;

        this.beanDefs = new ConcurrentHashMap<>();
        this.beanDefs.put(REGIST_WITHOUT_ANNOTATION, ConcurrentHashMap.newKeySet());
        for (BeanRegistrable value : BeanRegistrable.values()) {
            this.beanDefs.put(value.getBeanRegistrableAnnotationType().getCanonicalName(), ConcurrentHashMap.newKeySet());
        }
        this.beanIdToDef = new ConcurrentHashMap<>();
        this.singletonBeans = new ConcurrentHashMap<>();
        this.singletonBeansControllerLifecycleInvocations = new CopyOnWriteArrayList<>();

        this.beanClassLoader = Thread.currentThread().getContextClassLoader();
        if (this.parent == null) {
            // rootApplicationContext
            BeanManager.getInstance().initBeanHolder(this);
            BeanManager.getInstance().initBeanDefinitionRegistry(this);
        }
        if (logger.isDebugEnabled(Markers.LIFE_CYCLE.get()))
            logger.debug(Markers.LIFE_CYCLE.get(), "success init GenericApplicationContext as " + this);
    }

    @Override
    public Object getBean(BeanIdentifier id) throws BeanNotFoundException {
        if (logger.isTraceEnabled(Markers.LIFE_CYCLE.get()))
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
        if (logger.isTraceEnabled(Markers.LIFE_CYCLE.get()))
            logger.trace(Markers.LIFE_CYCLE.get(), "success getBean as " + bean);
        return bean;
    }

    @Override
    public synchronized void register(BeanDefinition definition) throws BeanDefinitionNotRegisteredException {
        if (beanIdToDef.containsKey(definition.getId())) {
            throw new BeanDefinitionNotRegisteredException(definition.getId().toString(), definition.toString());
        }

        Set<BeanDefinition> beanDefSet = this.beanDefs.get(definition.getBeanRegistableAnnotationCanonicalName() == null ? REGIST_WITHOUT_ANNOTATION : definition.getBeanRegistableAnnotationCanonicalName());
        if (beanDefSet == null) {
            if (logger.isWarnEnabled(Markers.LIFE_CYCLE.get()))
                logger.warn(Markers.LIFE_CYCLE.get(), String.format("not supported BeanDefinition. show %s. current BeanDefinition : %s.", BeanRegistrable.class.getCanonicalName(), definition));
            return;
        }
        beanDefSet.add(definition);
        this.beanIdToDef.put(definition.getId(), definition);
        if (logger.isDebugEnabled(Markers.LIFE_CYCLE.get()))
            logger.debug(Markers.LIFE_CYCLE.get(), String.format("finish register BeanDefinition. current BeanDefinition : %s", definition));
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

    private Object createBean(BeanDefinition definition) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if (logger.isTraceEnabled(Markers.LIFE_CYCLE.get()))
            logger.trace(Markers.LIFE_CYCLE.get(), "try createBean...");

        Class<?> beanClazz = this.beanClassLoader.loadClass(definition.getBeanCreationClassCanonicalName());

        // if has Constructor @Autowired annotation -> param has annotation BeanVariable -> check it -> getBean() all BeanVariable and injection that param.
        List<Object> boundObjects = new ArrayList<>();

        Constructor ctorFound = null;
        try {
            ctorFound = ReflectionUtil.getConstructorMetaAnyway(beanClazz, (ctor) -> {
                for (Annotation annotation : ctor.getAnnotations()) {
                    if (annotation instanceof Autowired) {
                        return true;
                    }
                }
                return false;
            });
        } catch (NoSuchMethodException e) {
            if (logger.isDebugEnabled(Markers.MESSAGE.get()))
                logger.debug(Markers.MESSAGE.get(), e.getMessage());
        }

        Object bean = null;
        if (ctorFound == null) {
            bean = beanClazz.newInstance();
        } else {
            Class<?>[] paramClasses = ctorFound.getParameterTypes();
            Annotation[][] paramAnnos =  ctorFound.getParameterAnnotations();
            for (int i = 0; i < paramClasses.length; i++) {
                String beanName = null;
                for (Annotation paramAnnotation : paramAnnos[i]) {
                    if (paramAnnotation instanceof BeanVariable) {
                        beanName = ((BeanVariable) paramAnnotation).value();
                        break;
                    }
                }
                if (beanName == null) {
                    // BeanVariable이 아닌 케이스의 param.
                    // constructor이므로 null을 제공.
                    boundObjects.add(null);
                } else {
                    try {
                        boundObjects.add(getBean(new BeanIdentifier(paramClasses[i].getCanonicalName(), beanName)));
                    } catch (BeanNotFoundException e) {
                        if (logger.isInfoEnabled(Markers.LIFE_CYCLE.get()))
                            logger.info(Markers.LIFE_CYCLE.get(), e.getMessage());
                        boundObjects.add(null);
                    }
                }
            }

            bean = ctorFound.newInstance(boundObjects.toArray());
        }

        if (definition.isSingleton() && bean instanceof ControllerLifecycleInvocation) {
            singletonBeansControllerLifecycleInvocations.add(bean);
        }
        if (logger.isTraceEnabled(Markers.LIFE_CYCLE.get()))
            logger.trace(Markers.LIFE_CYCLE.get(), "success createBean as " + bean);
        if (definition.isSingleton() && Controller.class.getCanonicalName().equals(definition.getBeanRegistableAnnotationCanonicalName())) {
            for (Object beanWantToInvocation: singletonBeansControllerLifecycleInvocations) {
                try {
                    ReflectionUtil.invokeMethod(beanWantToInvocation, "invokeAfterControllerCreation", new Class[]{Object.class}, new Object[]{bean});
                } catch (Exception e) {
                    if (logger.isDebugEnabled(Markers.LIFE_CYCLE.get()))
                        logger.error(Markers.LIFE_CYCLE.get(), String.format("ControllerLifecycleInvocation failed. beanWantToInvocation : %s, beanCurrentCreation : %s", beanWantToInvocation, bean), e);
                }
            }
        }

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
     *
     * pre-initialize 순서 BeanKind: Service -> Etc ...
     *
     * @see org.sonan.framework.context.annotation.Scope Singleton: 새로 생김.
     */
    public void refreshInstances() throws BeanCreationFailedException {
        this.singletonBeans = new ConcurrentHashMap<>();
        this.singletonBeansControllerLifecycleInvocations = new CopyOnWriteArrayList<>();

        for (BeanRegistrable beanRegistrable : BeanRegistrable.getBeanRegistrableByOrder()) {
            createSingletonBeans(beanRegistrable.getBeanRegistrableAnnotationType().getCanonicalName());
        }
        createSingletonBeans(REGIST_WITHOUT_ANNOTATION);
    }

    private void createSingletonBeans(String beanDefsKey) {
        beanDefs.get(beanDefsKey).forEach(beanDef -> {
            try {
                if (beanDef.isSingleton())
                    getBean(beanDef);
            } catch (BeanCreationFailedException e) {
                if (logger.isWarnEnabled(Markers.LIFE_CYCLE.get()))
                    logger.warn(Markers.LIFE_CYCLE.get(), String.format("Failed BeanCreation as Singletion. current BeanDefinition : %s", beanDef));
            }
        });
    }
}
