package org.sonan.util.reflect.throwable;

/**
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class ReflectiveOperationRuntimeException extends RuntimeException{
    public ReflectiveOperationRuntimeException() {
    }

    public ReflectiveOperationRuntimeException(String message) {
        super(message);
    }

    public ReflectiveOperationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectiveOperationRuntimeException(Throwable cause) {
        super(cause);
    }

    public ReflectiveOperationRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
