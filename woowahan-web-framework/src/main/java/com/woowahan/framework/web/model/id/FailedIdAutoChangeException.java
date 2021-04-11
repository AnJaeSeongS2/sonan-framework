package com.woowahan.framework.web.model.id;

/**
 * Created by Jaeseong on 2021/04/09
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class FailedIdAutoChangeException extends Exception {
    public FailedIdAutoChangeException(String message) {
        super(message);
    }

    public FailedIdAutoChangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedIdAutoChangeException(Throwable cause) {
        super(cause);
    }
}
