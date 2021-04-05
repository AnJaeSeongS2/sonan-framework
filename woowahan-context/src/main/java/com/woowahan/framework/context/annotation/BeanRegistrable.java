package com.woowahan.framework.context.annotation;

import com.woowahan.framework.context.bean.throwable.BeanException;
import com.woowahan.util.reflect.Util;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Bean으로 등록될 수 있는 Annotation들에게 붙여야 한다.
 * 등록된 annotation들은 String을 반환하는 value() method를 구현해야하며, 반환값으로 beanName을 의미하는 값을 제공해야한다.
 *
 * @see {@link Bean}
 * @see {@link Configuration}
 * @see {@link Controller}
 * @see {@link Service}
 * Created by Jaeseong on 2021/04/05
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public enum BeanRegistrable {
//    BEAN(Bean.class),
    CONFIGURATION(Configuration.class),
    CONTROLLER(Controller.class),
    SERVICE(Service.class);

    private static Set<Class<? extends Annotation>> annotationTypeSetCache;
    private static Map<ElementType, Set<Class<? extends Annotation>>> annoationTypeUsingElementTypeMapCache;
    private Class<? extends Annotation> beanRegistrableAnnotationType;

    BeanRegistrable(Class<? extends Annotation> registrable) {
        this.beanRegistrableAnnotationType = registrable;
    }

    public Class<? extends Annotation> getBeanRegistrableAnnotationType() {
        return this.beanRegistrableAnnotationType;
    }

    public static boolean contains(Annotation annotation) {
        return getBeanRegistrableAnnotationTypeSet().contains(annotation.annotationType());
    }

    public static Set<Class<? extends Annotation>> getBeanRegistrableAnnotationTypeSet() {
        if (annotationTypeSetCache == null) {
            annotationTypeSetCache = Arrays.stream(BeanRegistrable.values())
                .map(beanRegistrable -> beanRegistrable.getBeanRegistrableAnnotationType())
                .collect(Collectors.toSet());
        }
        return annotationTypeSetCache;
    }

    public static Map<ElementType, Set<Class<? extends Annotation>>> getBeanRegistrableAnnotationTypeUsingElementTypeMap() {
        if (annoationTypeUsingElementTypeMapCache == null) {
            annoationTypeUsingElementTypeMapCache = new HashMap();
            Arrays.stream(BeanRegistrable.values())
            .forEach(beanRegistrable -> {
                for (Annotation innerAnno : beanRegistrable.getBeanRegistrableAnnotationType().getDeclaredAnnotations()) {
                    if (innerAnno.annotationType().getCanonicalName().equals(Target.class.getCanonicalName())) {
                        try {
                            for (ElementType type: (ElementType[]) getValueFromAnnotation(innerAnno)) {
                                if (!annoationTypeUsingElementTypeMapCache.containsKey(type)) {
                                    annoationTypeUsingElementTypeMapCache.put(type, new HashSet<>());
                                }
                                annoationTypeUsingElementTypeMapCache.get(type).add(beanRegistrable.getBeanRegistrableAnnotationType());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        return annoationTypeUsingElementTypeMapCache;
    }

    public static Set<Class<? extends Annotation>> getBeanRegistrableAnnotationTypeSet(ElementType type) {
        Set result = getBeanRegistrableAnnotationTypeUsingElementTypeMap().get(type);
        return result == null ? new HashSet() : result;
    }

    public static String getBeanName(Annotation annotation) throws BeanException {
        if (contains(annotation)) {
            try {
                // beanName
                return (String) getValueFromAnnotation(annotation);
            } catch (Exception e) {
                throw new BeanException("BeanRegistrable is not correct. check your BeanRegistrable.", e);
            }
        }
        throw new BeanException("no matched annotation to register BeanDefinition. annotation: " + annotation.toString());
    }

    private static Object getValueFromAnnotation(Annotation annotation) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return Util.invokeMethod(annotation, "value");
    }
}
