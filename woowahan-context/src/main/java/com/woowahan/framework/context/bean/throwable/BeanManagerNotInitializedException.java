package com.woowahan.framework.context.bean.throwable;

/**
 * BeanManager가 아직 초기화되지 않았다.
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BeanManagerNotInitializedException extends Exception {
    public BeanManagerNotInitializedException(String message) {
        super(message);
    }

    public BeanManagerNotInitializedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanManagerNotInitializedException(Throwable cause) {
        super(cause);
    }
}
