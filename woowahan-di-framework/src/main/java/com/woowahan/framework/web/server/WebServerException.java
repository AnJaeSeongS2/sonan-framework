package com.woowahan.framework.web.server;

public class WebServerException extends RuntimeException {
    public WebServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
