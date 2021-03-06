package org.sonan.framework.context.bean;

import org.sonan.framework.context.annotation.Configuration;
import org.sonan.framework.context.annotation.Controller;
import org.sonan.framework.context.annotation.Service;
import org.sonan.framework.context.bean.throwable.BeanDefinitionNotGeneratedException;
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
        BeanDefinitionHolder holder = new BeanDefinitionHolder((URLClassLoader) Thread.currentThread().getContextClassLoader(), "beanInOfBasePackage");
        assertTrue(holder.get().contains(new BeanDefinition("beanInOfBasePackage.TestConfiguration", null, null, Configuration.class.getCanonicalName())));
        assertTrue(holder.get().contains(new BeanDefinition("beanInOfBasePackage.TestControllerInOfBasePackage", null, null, Controller.class.getCanonicalName())));
        assertTrue(holder.get().contains(new BeanDefinition("beanInOfBasePackage.TestService", null, null, Service.class.getCanonicalName())));
        assertFalse(holder.get().contains(new BeanDefinition("beanOutOfBasePackage.TestControllerOutOfPackage", null, null, Controller.class.getCanonicalName())));
        assertTrue(holder.get().contains(new BeanDefinition("org.sonan.framework.context.bean.TestSystemBeanFinder", null, null, Configuration.class.getCanonicalName())));
    }
}