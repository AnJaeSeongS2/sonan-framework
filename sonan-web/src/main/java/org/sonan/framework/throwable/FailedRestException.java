package org.sonan.framework.throwable;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class FailedRestException extends RepositoryException {
    public FailedRestException(String message) {
        super(message);
    }

    public FailedRestException(Throwable cause) {
        super(cause);
    }

    public FailedRestException(String message, Throwable cause) {
        super(message, cause);
    }
}
