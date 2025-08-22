package com.flipfit.exception;

/**
 * Exception thrown when a registration process is not completed
 */
public class RegistrationNotDoneException extends FlipfitException {

    private static final String ERROR_CODE = "FF-REG-001";

    public RegistrationNotDoneException(String message) {
        super(ERROR_CODE, message);
    }

    public RegistrationNotDoneException(String message, Throwable cause) {
        super(ERROR_CODE, message, cause);
    }

    public RegistrationNotDoneException(String userId, String userType) {
        super(ERROR_CODE, "Registration not completed for " + userType + " with ID " + userId);
    }
}
