package com.woowahan.util.classloader;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * first-child loading rule on basePackage.
 * first-parent loading rule out basePackage.
 *
 * just for setting BeanDefinitions.
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class FirstChildOnBasePackageClassLoader extends URLClassLoader {
    private final String basePackage;

    private FirstChildOnBasePackageClassLoader(URL[] urls) {
        super(urls);
        throw new RuntimeException("no use this constructor");
    }

    private FirstChildOnBasePackageClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
        throw new RuntimeException("no use this constructor");
    }

    public FirstChildOnBasePackageClassLoader(URL[] urls, ClassLoader parent, String basePackage) {
        super(urls, parent);
        if (basePackage == null) {
            this.basePackage = "";
        } else {
            this.basePackage = basePackage;
        }
    }

    /**
     * first-child loading rule on basePackage.
     * first-parent loading rule out basePackage.
     * @param name class canonical name
     * @param resolve
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass == null) {
            if (name.startsWith(this.basePackage)) {
                // first-child load
                try {
                    loadedClass = findClass(name);
                } catch (ClassNotFoundException e) {
                    // use parent class loader
                    loadedClass = super.loadClass(name, false);
                }
            } else {
                // fisrt-parent load
                try {
                    // use parent class loader
                    loadedClass = super.loadClass(name, false);
                } catch (ClassNotFoundException e) {
                    loadedClass = findClass(name);
                }
            }
        }
        if (resolve) {
            resolveClass(loadedClass);
        }
        return loadedClass;
    }
}
