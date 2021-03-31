package com.woowahan.framework.context;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jaeseong on 2021/03/31
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@ExtendWith(MockitoExtension.class)
class GenericApplicationContextTest {

    static private ServletContext servletContext;

    //parent
    static private GenericApplicationContext<ServletContext> rootApplicationContext;

    //child
    static private GenericApplicationContext<ServletContext> childApplicationContext;

    @BeforeAll
    static void beforeAll() {
        servletContext = mock(ServletContext.class);
        rootApplicationContext = new GenericApplicationContext<ServletContext>(null, servletContext, (servletContext) ->
            (ApplicationContext) servletContext.getAttribute(ApplicationContext.ROOT_APPLICATION_CONTEXT_ATTRIBUTE_KEY)
        );
        childApplicationContext = new GenericApplicationContext<ServletContext>(rootApplicationContext, servletContext, (servletContext) ->
            (ApplicationContext) servletContext.getAttribute(ApplicationContext.ROOT_APPLICATION_CONTEXT_ATTRIBUTE_KEY)
        );
        when(servletContext.getAttribute(ApplicationContext.ROOT_APPLICATION_CONTEXT_ATTRIBUTE_KEY)).thenReturn(rootApplicationContext);
    }


    @Test
    void getBean() {
        //TODO
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
}