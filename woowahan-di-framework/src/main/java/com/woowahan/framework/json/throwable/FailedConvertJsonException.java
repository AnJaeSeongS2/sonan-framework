package com.woowahan.framework.json.throwable;

/**
 * Json <-> Model 사이에 발생.
 *
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class FailedConvertJsonException extends Exception {

    public FailedConvertJsonException(String message) {
        super(message);
    }

    public FailedConvertJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedConvertJsonException(Throwable cause) {
        super(cause);
    }
}
