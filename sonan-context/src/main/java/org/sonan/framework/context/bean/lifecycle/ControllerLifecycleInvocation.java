package org.sonan.framework.context.bean.lifecycle;

/**
 * Controller Bean들이 추가될 때 동작할 handler이다.
 * 이 handler는 Singleton Bean에다만 부착가능하다.
 *
 * ex: Service1(singleton) implements ControllerLifecycleInvocation 이면,  Controller1이 생성될 때 Service1의 invokeAfterControllerCreation이 수행된다.
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@FunctionalInterface
public interface ControllerLifecycleInvocation {
    void invokeAfterControllerCreation(Object createdController);

}
