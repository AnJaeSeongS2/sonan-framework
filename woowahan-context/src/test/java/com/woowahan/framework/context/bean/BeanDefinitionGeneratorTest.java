package com.woowahan.framework.context.bean;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class BeanDefinitionGeneratorTest {

    @Test
    void get() {
        BeanDefinitionGenerator generator = new BeanDefinitionGenerator((URLClassLoader) Thread.currentThread().getContextClassLoader(), "com.woowahan.framework.context.beanInOfBasePackage");
        List<BeanDefinition> expected = Arrays.asList(
                new BeanDefinition("com.woowahan.framework.context.beanInOfBasePackage.ConfigurationTest", null, null),
                new BeanDefinition("com.woowahan.framework.context.beanInOfBasePackage.ServiceTest", null, null),
                new BeanDefinition("com.woowahan.framework.context.beanInOfBasePackage.ControllerTest", null, null)
        );
        assertEquals(expected, generator.get());

    }
}