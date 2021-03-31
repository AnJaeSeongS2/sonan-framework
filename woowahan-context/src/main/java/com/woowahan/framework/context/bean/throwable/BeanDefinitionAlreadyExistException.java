package com.woowahan.framework.context.bean.throwable;

/**
 * beanDefinition을 register할 때 발생.
 *
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanDefinitionAlreadyExistException extends BeanException {
    private static final String MSG_FORMAT = "The BeanDefinition is already exist on BeanDefinitionRegistry. current BeanDefinition tried to register: {0}";

    public BeanDefinitionAlreadyExistException(String... msgParam) {
        super(String.format(MSG_FORMAT, (Object[]) msgParam));
    }

    public BeanDefinitionAlreadyExistException(Throwable cause, String... msgParam) {
        super(String.format(MSG_FORMAT, (Object[]) msgParam), cause);
    }
}
