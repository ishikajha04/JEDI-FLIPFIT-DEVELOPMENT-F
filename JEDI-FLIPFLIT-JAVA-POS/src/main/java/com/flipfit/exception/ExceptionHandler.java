package com.flipfit.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to handle exceptions in a consistent manner throughout the application
 */
public class ExceptionHandler {

    private static final Logger logger = Logger.getLogger(ExceptionHandler.class.getName());

    /**
     * Handles exceptions by logging them and returning a user-friendly message
     *
     * @param exception The exception to handle
     * @return A user-friendly error message
     */
    public static String handleException(Exception exception) {
        if (exception instanceof FlipfitException) {
            FlipfitException flipfitException = (FlipfitException) exception;
            logger.log(Level.WARNING,
                       "FlipFit Error [" + flipfitException.getErrorCode() + "]: " + flipfitException.getMessage(),
                       flipfitException);
            return formatUserFriendlyMessage(flipfitException);
        } else {
            logger.log(Level.SEVERE, "Unexpected error occurred", exception);
            return "An unexpected error occurred. Please try again later.";
        }
    }

    /**
     * Formats a user-friendly message based on the type of exception
     *
     * @param exception The FlipfitException to format
     * @return A user-friendly error message
     */
    private static String formatUserFriendlyMessage(FlipfitException exception) {
        if (exception instanceof UserNotFoundException) {
            return "We couldn't find the user you're looking for. Please check the details and try again.";
        } else if (exception instanceof RegistrationNotDoneException) {
            return "Registration is incomplete. Please complete the registration process before proceeding.";
        } else if (exception instanceof PaymentNotDoneException) {
            return "Payment has not been completed. Please complete payment to confirm your booking.";
        } else if (exception instanceof SlotNotFoundException) {
            return "The requested slot could not be found. It may have been removed or is no longer available.";
        } else if (exception instanceof BookingNotConfirmedException) {
            return "Your booking is not confirmed yet. Please check the status in your dashboard.";
        } else if (exception instanceof TransactionFailureException) {
            return "Your transaction could not be processed. Please try again or use a different payment method.";
        } else {
            return "An error occurred: " + exception.getMessage();
        }
    }
}
