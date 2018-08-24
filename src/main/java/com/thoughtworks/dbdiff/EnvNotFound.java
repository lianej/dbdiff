package com.thoughtworks.dbdiff;

public class EnvNotFound extends RuntimeException {

    public EnvNotFound() {
    }

    public EnvNotFound(String message) {
        super(message);
    }

    public EnvNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public EnvNotFound(Throwable cause) {
        super(cause);
    }

    public EnvNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
