package org.sonan.framework.throwable;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class FailedPostException extends FailedRestException {
    public FailedPostException(String message) {
        super(message);
    }

    public FailedPostException(Throwable cause) {
        super(cause);
    }

    public FailedPostException(String message, Throwable cause) {
        super(message, cause);
    }
}
