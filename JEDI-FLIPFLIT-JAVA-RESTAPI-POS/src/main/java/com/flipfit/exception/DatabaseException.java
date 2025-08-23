package com.flipfit.exception;

/**
 * @author Flipfit Team
 * @description Exception for database operation failures in the Flipfit system.
 */
public class DatabaseException extends RuntimeException {
    /**
     * @method DatabaseException
     * @parameter message The exception message.
     * @description Constructs a DatabaseException with a message.
     * @exception RuntimeException
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * @method DatabaseException
     * @parameter message The exception message.
     * @parameter cause The cause of the exception.
     * @description Constructs a DatabaseException with a message and cause.
     * @exception RuntimeException
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
