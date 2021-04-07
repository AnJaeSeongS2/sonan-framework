package com.woowahan.framework.context.bean;

import com.woowahan.framework.context.annotation.BeanRegistrable;
import com.woowahan.framework.context.annotation.Configuration;
import com.woowahan.framework.context.annotation.Controller;
import com.woowahan.framework.context.bean.throwable.BeanDefinitionNotGeneratedException;
import com.woowahan.logback.support.Markers;
import com.woowahan.util.annotation.Nullable;
import com.woowahan.util.classloader.FirstChildOnBasePackageClassLoader;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

/**
 * Spring의 ResourceLoader를 통한 class loading없이 bean meta를 취득하여 BeanDefinition을 applicationContext에 관리시키는 것을 돕는 매커니즘을 별도 classLoader를 통해 쉬운 구현으로 간다.
 * 별도 ClassLoader는 basePackage 하위 모든 class를 읽고 해당 class들 중, Bean으로 등록될 member만 BeanDefinition으로 제공한다.
 *
 * TODO: BeanDefinitionHolder 와 BeanDefinitionRegistry 가 역할이 곂치는 것으로 보이는데, 원래의도는 BeanDefinitionHolder 는 초기 부틷도중에 로딩한 meta 관리, BeanDefinitionRegistry는 runtime에 definition을 추가가능하게끔 분리하려 했었던 의도가 있다.
 * TODO: 그러나 runtime 에 definition을 추가하는 것은 framework 안정성을 오히려 떨구는 것으로 생각해 Registry개념을 제거하고 Holder만 유지할 예정.
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanDefinitionHolder {
    private static final Logger logger = LoggerFactory.getLogger(BeanDefinitionHolder.class);

    /**
     * parent > beanMetaClassLoader 관계.
     */
    private final URLClassLoader parentCL;
    private final FirstChildOnBasePackageClassLoader beanMetaClassLoader;
    private final Reflections beanMetaFinder;
    private final String basePackage;

    private final Set<BeanDefinition> beanDefinitionSet;

    public BeanDefinitionHolder(@Nullable URLClassLoader parentCL, @Nullable String basePackage) throws BeanDefinitionNotGeneratedException {
        if (logger.isDebugEnabled())
            logger.debug(Markers.LIFE_CYCLE.get(), "Start create BeanDefinitionHolder");
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
                BeanDefinition definition = genBeanDefinition(clazz);
                beanDefinitionSet.add(definition);
                if (logger.isDebugEnabled())
                    logger.debug(Markers.LIFE_CYCLE.get(), "Created BeanDefinition " + definition.toString());
            }
        } catch (Exception e) {
            throw new BeanDefinitionNotGeneratedException(e);
        }
        if (logger.isDebugEnabled())
            logger.debug(Markers.LIFE_CYCLE.get(), "Created BeanDefinitionHolder.");
    }

    public Set<BeanDefinition> get() throws BeanDefinitionNotGeneratedException {
        return beanDefinitionSet;
    }

    private BeanDefinition genBeanDefinition(Class<?> beanClass) throws BeanDefinitionNotGeneratedException {
        try {
            Scope beanScope = null;
            String beanName = null;
            String beanKind = null;
            for (Annotation anno: beanClass.getDeclaredAnnotations()) {
                if (BeanRegistrable.contains(anno)) {
                    beanName = BeanRegistrable.getBeanName(anno);
                    beanKind = anno.annotationType().getCanonicalName();
                }

                if (anno.annotationType() == com.woowahan.framework.context.annotation.Scope.class) {
                    beanScope = ((com.woowahan.framework.context.annotation.Scope) anno).value();
                }
            }
            return new BeanDefinition(beanClass.getCanonicalName(), beanName, beanScope, beanKind);
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