package com.woowahan.framework.container.servlet;

import com.woowahan.framework.context.ApplicationContext;
import com.woowahan.framework.context.GenericApplicationContext;
import com.woowahan.framework.context.bean.BeanDefinitionHolder;
import com.woowahan.framework.context.bean.BeanIdentifier;
import com.woowahan.framework.context.bean.throwable.BeanDefinitionNotGeneratedException;
import com.woowahan.framework.context.bean.throwable.BeanNotFoundException;
import com.woowahan.util.reflect.ReflectionUtil;
import com.woowahan.util.reflect.ReflectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.*;

import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Jaeseong on 2021/04/05
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
class ServletBootstrapperDefaultTest {
    private ServletContext servletContext;
    private ServletRegistration.Dynamic servletRegistration;
    private FilterRegistration.Dynamic filterRegistration;

    //parent
    private GenericApplicationContext<ServletContext> rootApplicationContext;

    //child
    private GenericApplicationContext<ServletContext> childApplicationContext;

    private BeanDefinitionHolder holder;

    @BeforeEach
    void beforeEach() throws BeanDefinitionNotGeneratedException {
        servletContext = mock(ServletContext.class);
        servletRegistration = mock(ServletRegistration.Dynamic.class);
        filterRegistration = mock(FilterRegistration.Dynamic.class);

        rootApplicationContext = new GenericApplicationContext<ServletContext>(null, servletContext, (servletContext) ->
                (ApplicationContext) servletContext.getAttribute(ApplicationContext.ROOT_APPLICATION_CONTEXT_ATTRIBUTE_KEY)
        );
        childApplicationContext = new GenericApplicationContext<ServletContext>(rootApplicationContext, servletContext, (servletContext) ->
                (ApplicationContext) servletContext.getAttribute(ApplicationContext.ROOT_APPLICATION_CONTEXT_ATTRIBUTE_KEY)
        );
        Mockito.lenient().when(servletContext.getAttribute(ApplicationContext.ROOT_APPLICATION_CONTEXT_ATTRIBUTE_KEY)).thenReturn(rootApplicationContext);
        Mockito.lenient().when(servletContext.addServlet(Mockito.anyString(), Mockito.any(Servlet.class))).thenReturn(servletRegistration);
        Mockito.lenient().when(servletContext.addFilter(Mockito.anyString(), Mockito.any(Filter.class))).thenReturn(filterRegistration);

    }

    @Test
    void selfInitialize() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        ServletBootstrapperDefault bootstrapper = new ServletBootstrapperDefault();
        ReflectionUtil.invokeMethodAnyway(bootstrapper, "initBeanDefinitionHolder", new Class<?>[]{URLClassLoader.class, String.class}, (URLClassLoader) Thread.currentThread().getContextClassLoader(), "com.woowahan.framework.container.servlet.beanInOfBasePackage");
        ReflectionUtil.setFieldAnyway(bootstrapper, "rootAppCtx", rootApplicationContext);

        // test target
        ReflectionUtil.invokeMethodAnyway(bootstrapper, "selfInitialize", new Class<?>[]{ServletContext.class}, servletContext);
        GenericApplicationContext<ServletContext> rootAppCtx = (GenericApplicationContext<ServletContext>) ReflectionUtil.getFieldAnyway(bootstrapper, "rootAppCtx");
        assertDoesNotThrow(() -> rootAppCtx.getBean(new BeanIdentifier("com.woowahan.framework.container.servlet.beanInOfBasePackage.ControllerInOfBasePackageTest", null)));
        assertThrows(BeanNotFoundException.class,() -> rootAppCtx.getBean(new BeanIdentifier("com.woowahan.framework.container.servlet.beanOutOfBasePackage.ControllerOutOfBasePackageTest", null)));
    }
}