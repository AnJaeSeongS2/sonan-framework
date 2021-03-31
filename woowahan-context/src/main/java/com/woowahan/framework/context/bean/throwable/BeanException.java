package com.woowahan.framework.context.bean.throwable;

/**
 * Bean 관련 RuntimeException
 *
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanException extends RuntimeException{

    /**
     * @param msg the detail message
     */
    public BeanException(String msg) {
        super(msg);
    }

    /**
     * @param msg the detail message
     * @param cause the root cause
     */
    public BeanException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
