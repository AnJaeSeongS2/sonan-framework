package com.woowahan.framework.context;

import com.woowahan.framework.context.bean.BeanDefinition;
import com.woowahan.framework.context.bean.BeanIdentifier;
import com.woowahan.framework.context.bean.Scope;
import com.woowahan.framework.context.bean.throwable.BeanDefinitionNotRegisteredException;
import com.woowahan.framework.context.bean.throwable.BeanNotFoundException;
import com.woowahan.util.reflect.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Jaeseong on 2021/03/31
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@ExtendWith(MockitoExtension.class)
class GenericApplicationContextTest {
    private ServletContext servletContext;

    //parent
    private GenericApplicationContext<ServletContext> rootApplicationContext;

    //child
    private GenericApplicationContext<ServletContext> childApplicationContext;

    @BeforeEach
    void beforeEach() {
        servletContext = mock(ServletContext.class);
        rootApplicationContext = new GenericApplicationContext<ServletContext>(null, servletContext, (servletContext) ->
            (ApplicationContext) servletContext.getAttribute(ApplicationContext.ROOT_APPLICATION_CONTEXT_ATTRIBUTE_KEY)
        );
        childApplicationContext = new GenericApplicationContext<ServletContext>(rootApplicationContext, servletContext, (servletContext) ->
            (ApplicationContext) servletContext.getAttribute(ApplicationContext.ROOT_APPLICATION_CONTEXT_ATTRIBUTE_KEY)
        );
        Mockito.lenient().when(servletContext.getAttribute(ApplicationContext.ROOT_APPLICATION_CONTEXT_ATTRIBUTE_KEY)).thenReturn(rootApplicationContext);
    }

    @Test
    void getContextHolder() {
        assertEquals(servletContext, rootApplicationContext.getContextHolder());
        assertEquals(servletContext, childApplicationContext.getContextHolder());
    }

    @Test
    void getParent() {
        assertEquals(null, rootApplicationContext.getParent());
        assertEquals(rootApplicationContext, childApplicationContext.getParent());
    }

    @Test
    void getRoot() {
        assertEquals(rootApplicationContext, rootApplicationContext.getRoot());
        assertEquals(rootApplicationContext, childApplicationContext.getRoot());
    }

    @Test
    void register() {
        BeanIdentifier id = new BeanIdentifier("java.lang.String", null);
        BeanDefinition definition1 = new BeanDefinition(id, Scope.SINGLETON);
        BeanDefinition definition2 = new BeanDefinition(id, Scope.PROTOTYPE);
        assertDoesNotThrow(() -> rootApplicationContext.register(definition1));
        assertThrows(BeanDefinitionNotRegisteredException.class, () -> rootApplicationContext.register(definition2));


        BeanIdentifier idOther = new BeanIdentifier("java.lang.String", "other");
        BeanDefinition definitionOther = new BeanDefinition(idOther, Scope.SINGLETON);
        assertDoesNotThrow(() -> rootApplicationContext.register(definitionOther));
    }

    @Test
    void getBeanNoBeanDefinition() {
        BeanIdentifier id = new BeanIdentifier("java.lang.RuntimeException", null);
        assertThrows(BeanNotFoundException.class, () -> rootApplicationContext.getBean(id));
    }

    @Test
    void createBean() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BeanIdentifier id = new BeanIdentifier("java.lang.RuntimeException", null);
        BeanDefinition definition = new BeanDefinition("java.lang.RuntimeException", null, Scope.SINGLETON);
        assertDoesNotThrow(() -> rootApplicationContext.register(definition));

        assertNotNull(Util.invokeMethodAnyway(rootApplicationContext, "createBean", new Class<?>[]{BeanDefinition.class}, definition));
    }

    @Test
    void getBeanPrototype() {
        assertDoesNotThrow(() -> {
            BeanIdentifier id = new BeanIdentifier("java.lang.RuntimeException", null);
            BeanDefinition definition = new BeanDefinition(id, Scope.PROTOTYPE);
            assertDoesNotThrow(() -> rootApplicationContext.register(definition));
            Object prototype1 = rootApplicationContext.getBean(id);
            Object prototype2 = rootApplicationContext.getBean(id);
            assertFalse(prototype1 == prototype2);

            Object prototype3 = childApplicationContext.getBean(id);
            assertFalse(prototype1 == prototype3);
        });
    }

    @Test
    void getBeanSingleton() {
        assertDoesNotThrow(() -> {
            BeanIdentifier id = new BeanIdentifier("java.lang.RuntimeException", null);
            BeanDefinition definition = new BeanDefinition(id, Scope.SINGLETON);
            assertDoesNotThrow(() -> rootApplicationContext.register(definition));
            Object singleton1 = rootApplicationContext.getBean(id);
            Object singleton2 = rootApplicationContext.getBean(id);
            assertTrue(singleton1 == singleton2);

            Object singleton3 = childApplicationContext.getBean(id);
            assertTrue(singleton1 == singleton3);
        });
    }
}