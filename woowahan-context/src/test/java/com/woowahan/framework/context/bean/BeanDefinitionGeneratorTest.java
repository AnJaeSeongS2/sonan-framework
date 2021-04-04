package com.woowahan.framework.context.bean;

import com.google.common.collect.Lists;
import com.woowahan.framework.context.bean.throwable.BeanDefinitionNotGeneratedException;
import com.woowahan.framework.context.beanInOfBasePackage.ConfigurationTest;
import com.woowahan.framework.context.beanOutOfBasePackage.ControllerOutOfPackageTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class BeanDefinitionGeneratorTest {

    @Test
    void get() throws BeanDefinitionNotGeneratedException {
        BeanDefinitionGenerator generator = new BeanDefinitionGenerator((URLClassLoader) Thread.currentThread().getContextClassLoader(), "com.woowahan.framework.context.beanInOfBasePackage");
        assertTrue(generator.get().contains(new BeanDefinition("com.woowahan.framework.context.beanInOfBasePackage.ConfigurationTest", null, null)));
        assertTrue(generator.get().contains(new BeanDefinition("com.woowahan.framework.context.beanInOfBasePackage.ControllerInOfBasePackageTest", null, null)));
        assertTrue(generator.get().contains(new BeanDefinition("com.woowahan.framework.context.beanInOfBasePackage.ServiceTest", null, null)));
        assertFalse(generator.get().contains(new BeanDefinition("com.woowahan.framework.context.beanOutOfBasePackage.ControllerOutOfPackageTest", null, null)));

    }
}