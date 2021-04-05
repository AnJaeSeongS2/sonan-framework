package com.woowahan.framework.context.bean;

import com.woowahan.framework.context.bean.throwable.BeanDefinitionNotGeneratedException;
import org.junit.jupiter.api.Test;

import java.net.URLClassLoader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class BeanDefinitionGeneratorTest {

    @Test
    void get() throws BeanDefinitionNotGeneratedException {
        BeanDefinitionHolder holder = new BeanDefinitionHolder((URLClassLoader) Thread.currentThread().getContextClassLoader(), "com.woowahan.framework.context.beanInOfBasePackage");
        assertTrue(holder.get().contains(new BeanDefinition("com.woowahan.framework.context.beanInOfBasePackage.ConfigurationTest", null, null)));
        assertTrue(holder.get().contains(new BeanDefinition("com.woowahan.framework.context.beanInOfBasePackage.ControllerInOfBasePackageTest", null, null)));
        assertTrue(holder.get().contains(new BeanDefinition("com.woowahan.framework.context.beanInOfBasePackage.ServiceTest", null, null)));
        assertFalse(holder.get().contains(new BeanDefinition("com.woowahan.framework.context.beanOutOfBasePackage.ControllerOutOfPackageTest", null, null)));

    }
}