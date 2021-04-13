package org.sonan.framework.throwable;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class FailedPutException extends FailedRestException {
    public FailedPutException(String message) {
        super(message);
    }

    public FailedPutException(Throwable cause) {
        super(cause);
    }

    public FailedPutException(String message, Throwable cause) {
        super(message, cause);
    }
}
