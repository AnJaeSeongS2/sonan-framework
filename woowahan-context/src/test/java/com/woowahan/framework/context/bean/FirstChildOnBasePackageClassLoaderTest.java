package com.woowahan.framework.context.bean;

import com.woowahan.framework.context.beanInOfBasePackage.ControllerInOfBasePackageTest;
import com.woowahan.framework.context.beanOutOfBasePackage.ControllerOutOfPackageTest;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.net.URLClassLoader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class FirstChildOnBasePackageClassLoaderTest {

    @Test
    void loadClassDiffWithParent() throws ClassNotFoundException {
        ClassLoader parentCL = Thread.currentThread().getContextClassLoader();
        FirstChildOnBasePackageClassLoader childLoader = new FirstChildOnBasePackageClassLoader(((URLClassLoader)parentCL).getURLs(), parentCL, "");

        String canonicalName = BeanIdentifierTest.class.getCanonicalName();
        Class<?> parentClass = parentCL.loadClass(canonicalName);
        Class<?> childClass = childLoader.loadClass(canonicalName);

        assertNotNull(parentClass);
        assertNotNull(childClass);
        assertNotEquals(parentClass, childClass);
    }

    @Test
    void loadClassInAndOutOfBasePackage() throws ClassNotFoundException {
        ClassLoader parentCL = Thread.currentThread().getContextClassLoader();
        FirstChildOnBasePackageClassLoader childLoader = new FirstChildOnBasePackageClassLoader(((URLClassLoader)parentCL).getURLs(), parentCL, "com.woowahan.framework.context.beanInOfBasePackage");

        String canonicalName = ControllerOutOfPackageTest.class.getCanonicalName();
        Class<?> parentClass = parentCL.loadClass(canonicalName);
        Class<?> childClass = childLoader.loadClass(canonicalName);

        assertNotNull(parentClass);
        assertNotNull(childClass);
        assertEquals(parentClass, childClass);


        canonicalName = ControllerInOfBasePackageTest.class.getCanonicalName();
        parentClass = parentCL.loadClass(canonicalName);
        childClass = childLoader.loadClass(canonicalName);

        assertNotNull(parentClass);
        assertNotNull(childClass);
        assertNotEquals(parentClass, childClass);
    }
}