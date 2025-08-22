package com.flipfit.exception;

/**
 * Exception thrown when a transaction fails
 */
public class TransactionFailureException extends FlipfitException {

    private static final String ERROR_CODE = "FF-TRANS-001";

    public TransactionFailureException(String message) {
        super(ERROR_CODE, message);
    }

    public TransactionFailureException(String message, Throwable cause) {
        super(ERROR_CODE, message, cause);
    }

    public TransactionFailureException(String transactionId, String reason) {
        super(ERROR_CODE, "Transaction " + transactionId + " failed: " + reason);
    }
}
