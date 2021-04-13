package org.sonan.framework.context.bean.throwable;

/**
 * beanDefinition을 generate할 때 발생.
 *
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanDefinitionNotGeneratedException extends BeanException {
    private static final String MSG_FORMAT = "The BeanDefinition can not be generated.";

    public BeanDefinitionNotGeneratedException(String... msgParam) {
        super(String.format(MSG_FORMAT, (Object[]) msgParam));
    }

    public BeanDefinitionNotGeneratedException(Throwable cause, String... msgParam) {
        super(String.format(MSG_FORMAT, (Object[]) msgParam), cause);
    }
}
