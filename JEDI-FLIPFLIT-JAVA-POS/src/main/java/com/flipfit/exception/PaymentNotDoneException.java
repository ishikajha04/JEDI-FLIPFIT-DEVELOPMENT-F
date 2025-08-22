package com.flipfit.exception;

/**
 * Exception thrown when a payment is not completed
 */
public class PaymentNotDoneException extends FlipfitException {

    private static final String ERROR_CODE = "FF-PAY-001";

    public PaymentNotDoneException(String message) {
        super(ERROR_CODE, message);
    }

    public PaymentNotDoneException(String message, Throwable cause) {
        super(ERROR_CODE, message, cause);
    }

}
