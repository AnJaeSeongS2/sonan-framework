package org.sonan.framework.context.bean.throwable;

/**
 * Bean 생성에 실패할 때 발생.
 *
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanCreationFailedException extends BeanException {

    /**
     * @param msg the detail message
     */
    public BeanCreationFailedException(String msg) {
        super(msg);
    }

    /**
     * @param msg the detail message
     * @param cause the root cause
     */
    public BeanCreationFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
