package org.sonan.framework.context.bean;

/**
 * BeanHolder는 name으로 식별되는 bean이 있으면 그것을 반환, 아니면 새로 생성한다.
 * for Singleton Bean
 *
 * Created by Jaeseong on 2021/03/31
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@FunctionalInterface
public interface BeanHolder extends BeanGenerator {
}
