package org.sonan.util.classloader;

import org.sonan.util.classloader.inBasePackage.TestClassIn;
import org.sonan.util.classloader.outBasePackage.TestClassOut;
import org.junit.jupiter.api.Test;

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

        String canonicalName = TestClassIn.class.getCanonicalName();
        Class<?> parentClass = parentCL.loadClass(canonicalName);
        Class<?> childClass = childLoader.loadClass(canonicalName);

        assertNotNull(parentClass);
        assertNotNull(childClass);
        assertNotEquals(parentClass, childClass);
    }

    @Test
    void loadClassInAndOutOfBasePackage() throws ClassNotFoundException {
        ClassLoader parentCL = Thread.currentThread().getContextClassLoader();
        FirstChildOnBasePackageClassLoader childLoader = new FirstChildOnBasePackageClassLoader(((URLClassLoader)parentCL).getURLs(), parentCL, "org.sonan.util.classloader.inBasePackage");

        String canonicalName = TestClassOut.class.getCanonicalName();
        Class<?> parentClass = parentCL.loadClass(canonicalName);
        Class<?> childClass = childLoader.loadClass(canonicalName);

        assertNotNull(parentClass);
        assertNotNull(childClass);
        assertEquals(parentClass, childClass);


        canonicalName = TestClassIn.class.getCanonicalName();
        parentClass = parentCL.loadClass(canonicalName);
        childClass = childLoader.loadClass(canonicalName);

        assertNotNull(parentClass);
        assertNotNull(childClass);
        assertNotEquals(parentClass, childClass);
    }
}