package org.sonan.util.reflect.throwable;

/**
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class NoSuchFieldRuntimeException extends ReflectiveOperationRuntimeException {

    public NoSuchFieldRuntimeException(String message) {
        super(message);
    }
}