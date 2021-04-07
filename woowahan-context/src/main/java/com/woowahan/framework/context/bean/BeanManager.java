package com.woowahan.framework.context.bean;

import com.woowahan.framework.context.bean.throwable.BeanException;
import com.woowahan.framework.context.bean.throwable.BeanNotFoundException;
import com.woowahan.logback.support.Markers;
import com.woowahan.util.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * framework 외부에서 Bean을 얻을 수 있게 지원.
 *
 * 본래 BeanHolder와 BeanDefinitionRegistry를 구분지어 관리할 생각이었다.
 * 현재 ApplicationContext 두 기능을 한번에 맡고있따.
 *
 * BeanManager입장에서는 ApplicationContext엔 관심이 없고 Bean에만 관심있으니 BeanHolder, BeanDefinitionRegistry로 관리한다.
 *
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanManager {
    private static final Logger logger = LoggerFactory.getLogger(BeanManager.class);
    private static final BeanManager instance = new BeanManager();
    private BeanHolder beanHolder;
    private BeanDefinitionRegistry beanDefinitionRegistry;

    public static BeanManager getInstance() {
        return instance;
    }

    private BeanManager() {
    }

    public void initBeanHolder(BeanHolder beanHolder) {
        this.beanHolder = beanHolder;
        if (logger.isInfoEnabled())
            logger.info(Markers.LIFE_CYCLE.get(), String.format("Initalized BeanManager. beanHolder : %s", beanHolder));
    }

    public void initBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        if (logger.isInfoEnabled())
            logger.info(Markers.LIFE_CYCLE.get(), String.format("Initalized BeanManager. beanDefinitionRegistry : %s", beanDefinitionRegistry));
    }

    public boolean IsInitialized() {
        return beanHolder != null && beanDefinitionRegistry != null;
    }

    public Object getBean(Class<?> beanClazz, @Nullable String beanName) throws BeanNotFoundException {
        return beanHolder.getBean(new BeanIdentifier(beanClazz.getCanonicalName(), beanName));
    }

    public Object getBean(BeanIdentifier beanId) throws BeanNotFoundException {
        return beanHolder.getBean(beanId);
    }

    public void register(BeanDefinition definition) throws BeanException {
        beanDefinitionRegistry.register(definition);
    }
}
