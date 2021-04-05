package com.woowahan.framework.container.throwable;

/**
 * container 기동에 실패했다.
 *
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class BootingFailException extends Exception {

    /**
     * @param msg the detail message
     */
    public BootingFailException(String msg) {
        super(msg);
    }

    /**
     * @param msg the detail message
     * @param cause the root cause
     */
    public BootingFailException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
