package com.woowahan.framework.context.bean.throwable;

/**
 * Bean 생성에 실패할 때 발생.
 *
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanFailedCreationException extends RuntimeException{

    /**
     * @param msg the detail message
     */
    public BeanFailedCreationException(String msg) {
        super(msg);
    }

    /**
     * @param msg the detail message
     * @param cause the root cause
     */
    public BeanFailedCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
