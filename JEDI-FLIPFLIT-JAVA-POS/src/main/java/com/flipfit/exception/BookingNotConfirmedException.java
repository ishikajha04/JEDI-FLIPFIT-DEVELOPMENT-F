package com.flipfit.exception;

/**
 * Exception thrown when a booking is not confirmed
 */
public class BookingNotConfirmedException extends FlipfitException {

    private static final String ERROR_CODE = "FF-BOOK-001";

    public BookingNotConfirmedException(String message) {
        super(ERROR_CODE, message);
    }

    public BookingNotConfirmedException(String message, Throwable cause) {
        super(ERROR_CODE, message, cause);
    }

}
