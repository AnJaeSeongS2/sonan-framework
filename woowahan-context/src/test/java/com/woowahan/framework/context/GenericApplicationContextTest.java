package com.woowahan.framework.context;

import com.woowahan.framework.context.annotation.Autowired;
import com.woowahan.framework.context.annotation.BeanVariable;
import com.woowahan.framework.context.annotation.Service;
import com.woowahan.framework.context.bean.BeanDefinition;
import com.woowahan.framework.context.bean.BeanIdentifier;
import com.woowahan.framework.context.bean.BeanManager;
import com.woowahan.framework.context.bean.Scope;
import com.woowahan.framework.context.bean.throwable.BeanCreationFailedException;
import com.woowahan.framework.context.bean.throwable.BeanDefinitionNotRegisteredException;
import com.woowahan.framework.context.bean.throwable.BeanNotFoundException;
import com.woowahan.util.reflect.ReflectionUtil;
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
        BeanIdentifier id = new BeanIdentifier(ClassWithoutBeanRegistrableAnnotation.class.getCanonicalName(), null);
        assertThrows(BeanNotFoundException.class, () -> rootApplicationContext.getBean(id));
    }

    @Test
    void createBean() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BeanIdentifier id = new BeanIdentifier(ClassWithoutBeanRegistrableAnnotation.class.getCanonicalName(), null);
        BeanDefinition definition = new BeanDefinition(ClassWithoutBeanRegistrableAnnotation.class.getCanonicalName(), null, Scope.SINGLETON);
        assertDoesNotThrow(() -> rootApplicationContext.register(definition));

        assertNotNull(ReflectionUtil.invokeMethodAnyway(rootApplicationContext, "createBean", new Class<?>[]{BeanDefinition.class}, definition));
    }

    @Test
    void refreshInstances() throws BeanNotFoundException, BeanCreationFailedException {
        BeanIdentifier id = new BeanIdentifier(Service1.class.getCanonicalName(), null);
        BeanDefinition definition = new BeanDefinition(id, null, Service.class.getCanonicalName());
        assertDoesNotThrow(() -> rootApplicationContext.register(definition));
        Object beforeSingletonBean = rootApplicationContext.getBean(id);

        rootApplicationContext.refreshInstances();
        assertNotEquals(beforeSingletonBean, rootApplicationContext.getBean(id));

    }

    @Test
    void createBeanAutowired() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, BeanCreationFailedException, BeanNotFoundException {
        BeanIdentifier id = new BeanIdentifier(Service1.class.getCanonicalName(), null);
        BeanDefinition definition = new BeanDefinition(id, null, Service.class.getCanonicalName());
        BeanIdentifier id2 = new BeanIdentifier(Service2.class.getCanonicalName(), null);
        BeanDefinition definition2 = new BeanDefinition(id2, null, Service.class.getCanonicalName());
        assertDoesNotThrow(() -> rootApplicationContext.register(definition));
        assertDoesNotThrow(() -> rootApplicationContext.register(definition2));

        rootApplicationContext.refreshInstances();
        Object service1Singleton = rootApplicationContext.getBean(id);
        assertNull(((Service2) rootApplicationContext.getBean(id2)).noBeanVariable);
        assertEquals(service1Singleton, ((Service2) rootApplicationContext.getBean(id2)).service1);
    }

    @Test
    void getBeanPrototype() {
        assertDoesNotThrow(() -> {
            BeanIdentifier id = new BeanIdentifier(ClassWithoutBeanRegistrableAnnotation.class.getCanonicalName(), null);
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
            BeanIdentifier id = new BeanIdentifier(ClassWithoutBeanRegistrableAnnotation.class.getCanonicalName(), null);
            BeanDefinition definition = new BeanDefinition(id, Scope.SINGLETON);
            assertDoesNotThrow(() -> rootApplicationContext.register(definition));
            Object singleton1 = rootApplicationContext.getBean(id);
            Object singleton2 = rootApplicationContext.getBean(id);
            assertTrue(singleton1 == singleton2);

            Object singleton3 = childApplicationContext.getBean(id);
            assertTrue(singleton1 == singleton3);
        });
    }

    @Test
    void BeanManagerGetBean() {
        assertDoesNotThrow(() -> {
            BeanIdentifier id = new BeanIdentifier(ClassWithoutBeanRegistrableAnnotation.class.getCanonicalName(), null);
            BeanDefinition definition = new BeanDefinition(id, Scope.SINGLETON);
            assertDoesNotThrow(() -> rootApplicationContext.register(definition));
            Object singleton1 = BeanManager.getInstance().getBean(id);
            Object singleton2 = BeanManager.getInstance().getBean(id);
            assertTrue(singleton1 == singleton2);
        });

    }
}

class ClassWithoutBeanRegistrableAnnotation {
    public String name;

    public ClassWithoutBeanRegistrableAnnotation() {
        this.name = null;
    }
}

@Service
class Service1 {
    public String name;

    public Service1() {
        this.name = null;
    }

    public Service1(String name) {
        this.name = name;
    }
}

@Service
class Service2 {
    public Service1 service1;
    public Service1 noBeanVariable;

    public Service2() {
        service1 = null;
    }


    @Autowired
    public Service2(@BeanVariable Service1 service1, Service1 noBeanVariable) {
        this.service1 = service1;
        this.noBeanVariable = noBeanVariable;
    }
}