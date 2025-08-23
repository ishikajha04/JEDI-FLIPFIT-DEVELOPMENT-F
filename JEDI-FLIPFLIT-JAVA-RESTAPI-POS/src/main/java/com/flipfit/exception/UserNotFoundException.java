package com.flipfit.exception;

/**
 * Exception thrown when a user is not found in the system
 */
public class UserNotFoundException extends FlipfitException {

    private static final String ERROR_CODE = "FF-USER-001";

    public UserNotFoundException(String message) {
        super(ERROR_CODE, message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(ERROR_CODE, message, cause);
    }

    public UserNotFoundException(String userId, String userType) {
        super(ERROR_CODE, userType + " with ID " + userId + " not found");
    }
}
