package com.woowahan.framework.container.annotation;

import java.lang.annotation.*;

/**
 * 이 annotation을 활용한 Class의 main은 Container-bootstrapper동작을 통해 vendor container와 함께 부팅되는 app이다.
 * framework에서 관리될 bean들은 이 annotation이 쓰인 Class의 package를 basePackage로써, componentScan이 진행되어 BeanDefinition이 등록돼 관리된다.
 *
 *
 * Created by Jaeseong on 2021/04/05
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ContainerBootApplication {
}
