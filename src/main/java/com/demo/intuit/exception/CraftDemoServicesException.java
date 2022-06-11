package com.demo.intuit.exception;

public class CraftDemoServicesException extends RuntimeException {
    public CraftDemoServicesException(String message) {
        super(message);
    }

    public CraftDemoServicesException(String message, Throwable cause) {
        super(message, cause);
    }

    public CraftDemoServicesException(Throwable cause) {
        super(cause);
    }
}
