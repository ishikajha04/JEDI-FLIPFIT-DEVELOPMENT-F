package com.flipfit.exception;

/**
 * Exception thrown when a slot is not found in the system
 */
public class SlotNotFoundException extends FlipfitException {

    private static final String ERROR_CODE = "FF-SLOT-001";

    public SlotNotFoundException(String message) {
        super(ERROR_CODE, message);
    }

    public SlotNotFoundException(String message, Throwable cause) {
        super(ERROR_CODE, message, cause);
    }

    public SlotNotFoundException(String slotId, String gymCenterId) {
        super(ERROR_CODE, "Slot with ID " + slotId + " not found at gym center " + gymCenterId);
    }
}
