package com.woowahan.framework.web.view;

/**
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class FailedViewResolveException extends Exception {
    public FailedViewResolveException(String message) {
        super(message);
    }

    public FailedViewResolveException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedViewResolveException(Throwable cause) {
        super(cause);
    }
}
