package org.sonan.framework.container.server;

public class WebServerException extends RuntimeException {
    public WebServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
