package com.woowahan.framework.context.bean.throwable;

/**
 * beanDefinition을 register할 때 id가 같은 것이 이미 등록돼 있으면 fail.
 *
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanDefinitionNotRegisteredException extends BeanException {
    private static final String MSG_FORMAT = "The BeanDefinition is can not registered to BeanDefinitionRegistry as same BeanIdentifier. current BeanDefinition: {0}, BeanDefinitionRegistry's BeanDefinition: {1}";

    public BeanDefinitionNotRegisteredException(String... msgParam) {
        super(String.format(MSG_FORMAT, (Object[]) msgParam));
    }

    public BeanDefinitionNotRegisteredException(Throwable cause, String... msgParam) {
        super(String.format(MSG_FORMAT, (Object[]) msgParam), cause);
    }
}
