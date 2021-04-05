package com.woowahan.framework.context.bean;

import com.woowahan.framework.context.annotation.BeanRegistrable;
import com.woowahan.framework.context.annotation.Configuration;
import com.woowahan.framework.context.bean.throwable.BeanDefinitionNotGeneratedException;
import com.woowahan.util.annotation.Nullable;
import com.woowahan.util.classloader.FirstChildOnBasePackageClassLoader;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

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

    private final Set<BeanDefinition> beanDefinitionSet;

    public BeanDefinitionGenerator(@Nullable URLClassLoader parentCL, @Nullable String basePackage) throws BeanDefinitionNotGeneratedException {
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

        try {
            beanDefinitionSet = new HashSet<>();
            Set<Class<?>> beanRegistrableClasses = new HashSet<>();
            for (Class<? extends Annotation> annotationType : BeanRegistrable.getBeanRegistrableAnnotationTypeSet(ElementType.TYPE)) {
                Set<Class<?>> beanClasses = beanMetaFinder.getTypesAnnotatedWith((Class<? extends Annotation>) beanMetaClassLoader.loadClass(annotationType.getCanonicalName()));
                if (Configuration.class.getCanonicalName().equals(annotationType.getCanonicalName())) {
                    // TODO: Bean annotation부분 등록.
                    // check @Bean annotation on custom Method in Class Type.
                }
                beanRegistrableClasses.addAll(beanClasses);
            }

            for (Class<?> clazz: beanRegistrableClasses) {
                beanDefinitionSet.add(genBeanDefinition(clazz));
            }
        } catch (Exception e) {
            throw new BeanDefinitionNotGeneratedException(e);
        }
    }

    public Set<BeanDefinition> get() throws BeanDefinitionNotGeneratedException {
        return beanDefinitionSet;
    }

    private BeanDefinition genBeanDefinition(Class<?> beanClass) throws BeanDefinitionNotGeneratedException {
        try {
            Scope beanScope = null;
            String beanName = null;
            for (Annotation anno: beanClass.getDeclaredAnnotations()) {
                if (BeanRegistrable.contains(anno)) {
                    beanName = BeanRegistrable.getBeanName(anno);
                }

                if (anno.annotationType() == com.woowahan.framework.context.annotation.Scope.class) {
                    beanScope = ((com.woowahan.framework.context.annotation.Scope) anno).value();
                }
            }
            return new BeanDefinition(beanClass.getCanonicalName(), beanName, beanScope);
        } catch (Exception e) {
            throw new BeanDefinitionNotGeneratedException(e);
        }
    }

//    private void setBeanClassesAboutBeanAnnotationOnConfiguration(Set<Class<?>> configurations, Class<? extends Annotation> configurationAnnotationType) {
//        if (Configuration.class.getCanonicalName().equals(configurationAnnotationType.getCanonicalName())) {
//            // check @Bean annotation on custom Method in Class Type.
//            configurations.forEach((clazz) -> {
//                for (Method declaredMethod : clazz.getDeclaredMethods()) {
//                    for (Annotation declaredAnnotation : declaredMethod.getDeclaredAnnotations()) {
//                        if (Bean.class.getCanonicalName().equals(declaredAnnotation.annotationType().getCanonicalName())) {
//
//                            // TODO @Bean으로 등록될 Method도 Bean으로써 등록되게 기능 구현.
//                        }
//                    }
//                }
//            });
//        }
//    }
}