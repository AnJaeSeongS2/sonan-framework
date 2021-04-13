package org.sonan.framework.context.bean.lifecycle;

/**
 * Bean들이 추가될 때 동작할 handler이다.
 * ex: Service1 implements ControllerLifecycleInvocation 이면, 아무 Bean이나 생성될 때 Service1의 invokeAfterBeanCreation이 수행된다.
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public interface BeanLifecycleInvocation {
    void invokeAfterBeanCreation(Object createdBean);
}
