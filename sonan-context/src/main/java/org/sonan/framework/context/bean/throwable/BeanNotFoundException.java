package org.sonan.framework.context.bean.throwable;

/**
 * bean을 못찾을 때 발생.
 *
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanNotFoundException extends BeanException {
    private static final String MSG_FORMAT = "The bean is not found. current BeanIdentifier used in the search: {0}";

    public BeanNotFoundException(String... msgParam) {
        super(String.format(MSG_FORMAT, (Object[]) msgParam));
    }

    public BeanNotFoundException(Throwable cause, String... msgParam) {
        super(String.format(MSG_FORMAT, (Object[]) msgParam), cause);
    }
}
