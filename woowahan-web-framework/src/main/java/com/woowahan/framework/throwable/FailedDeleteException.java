package com.woowahan.framework.throwable;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class FailedDeleteException extends FailedRestException {
    public FailedDeleteException(String message) {
        super(message);
    }

    public FailedDeleteException(Throwable cause) {
        super(cause);
    }

    public FailedDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
