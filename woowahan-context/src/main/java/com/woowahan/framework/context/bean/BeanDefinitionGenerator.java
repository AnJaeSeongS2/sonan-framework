package com.woowahan.framework.context.bean;

import com.woowahan.util.annotation.Nullable;
import org.reflections.Reflections;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.List;

/**
 * Spring의 ResourceLoader를 통한 class loading없이 bean meata를 취득하여 BeanDefinition을 applicationContext에 관리시키는 것을 돕는 매커니즘을 별도 classLoader를 통해 쉬운 구현으로 간다.
 * 별도 ClassLoader는 basePackage 하위 모든 class를 읽고 해당 class들 중, Bean으로 등록될 member만 BeanDefinition으로 제공한다.
 *
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanDefinitionGenerator {

    /**
     * parent > beanMetaClassLoader 관계.
     */
    private final URLClassLoader parentCL;
    private final FirstChildOnBasePackageClassLoader beanMetaClassLoader;
    private final Reflections beanMetaFinder;
    private final String basePackage;

    public BeanDefinitionGenerator(@Nullable URLClassLoader parentCL, @Nullable String basePackage) {
        if (basePackage == null) {
            this.basePackage = "";
        } else {
            this.basePackage = basePackage;
        }

        if (parentCL == null) {
            this.parentCL = (URLClassLoader) Thread.currentThread().getContextClassLoader();
        } else {
            this.parentCL = parentCL;
        }
        this.beanMetaClassLoader = new FirstChildOnBasePackageClassLoader(parentCL.getURLs(), parentCL, this.basePackage);
        this.beanMetaFinder = new Reflections(this.basePackage, this.beanMetaClassLoader);
    }

    public List<BeanDefinition> get() {
        return null;
    }
}

/**
 * first-child loading rule on basePackage.
 * first-parent loading rule out basePackage.
 * no resolveClass. (only want to get ClassObject)
 *
 * just for setting BeanDefinitions.
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class FirstChildOnBasePackageClassLoader extends URLClassLoader {
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
     * no resolveClass. (only want to get ClassObject)
     * @param name class canonical name
     * @param resolve always no resolve (true -> converted to false)
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
                    // use parent class loader without resolve (for performance)
                    loadedClass = super.loadClass(name, false);
                }
            } else {
                // fisrt-parent load
                try {
                    // use parent class loader without resolve (for performance)
                    loadedClass = super.loadClass(name, false);
                } catch (ClassNotFoundException e) {
                    loadedClass = findClass(name);
                }
            }
        }
        // no resolve (for performance)
        return loadedClass;
    }
}