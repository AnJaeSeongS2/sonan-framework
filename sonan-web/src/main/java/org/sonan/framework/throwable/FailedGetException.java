package org.sonan.framework.throwable;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class FailedGetException extends FailedRestException {
    public FailedGetException(String message) {
        super(message);
    }

    public FailedGetException(Throwable cause) {
        super(cause);
    }

    public FailedGetException(String message, Throwable cause) {
        super(message, cause);
    }
}
