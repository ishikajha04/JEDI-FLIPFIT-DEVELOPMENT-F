package com.flipfit.exception;

/**
 * Base exception class for all FlipFit application exceptions
 */
public class FlipfitException extends RuntimeException {

    private final String errorCode;

    public FlipfitException(String message) {
        super(message);
        this.errorCode = "FF-GENERIC-ERROR";
    }

    public FlipfitException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public FlipfitException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
