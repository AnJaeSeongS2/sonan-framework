package org.sonan.framework.web.throwable;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class FailedRouteException extends Exception {
    public FailedRouteException(String message) {
        super(message);
    }

    public FailedRouteException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedRouteException(Throwable cause) {
        super(cause);
    }
}
