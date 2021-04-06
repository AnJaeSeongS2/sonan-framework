package com.woowahan.util.reflect.throwable;

/**
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class NoSuchMethodRuntimeException extends ReflectiveOperationRuntimeException {

    public NoSuchMethodRuntimeException(String message) {
        super(message);
    }
}
