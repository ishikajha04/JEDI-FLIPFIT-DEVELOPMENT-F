package com.flipfit.bean;

/**
 * @author Flipfit Team
 * @description Represents a scheduler for notifications and slots in the Flipfit system.
 */
public class FlipfitScheduler {
    private int schedulerId;
    private int userId;
    private String message;
    private FlipfitSlot slot;

    /**
     * @method FlipfitScheduler
     * @description Default constructor for FlipfitScheduler.
     */
    public FlipfitScheduler() {
    }

    /**
     * @method FlipfitScheduler
     * @parameter schedulerId The unique scheduler ID.
     * @parameter userId The user ID associated with the scheduler.
     * @parameter message The message for the scheduler.
     * @parameter slot The slot associated with the scheduler.
     * @description Constructs a FlipfitScheduler with specified details.
     */
    public FlipfitScheduler(int schedulerId, int userId, String message, FlipfitSlot slot) {
        this.schedulerId = schedulerId;
        this.userId = userId;
        this.message = message;
        this.slot = slot;
    }

    /**
     * @method getSchedulerId
     * @description Gets the scheduler ID.
     * @return The unique scheduler ID.
     */
    public int getSchedulerId() {
        return schedulerId;
    }

    /**
     * @method setSchedulerId
     * @parameter schedulerId The unique scheduler ID to set.
     * @description Sets the scheduler ID.
     */
    public void setSchedulerId(int schedulerId) {
        this.schedulerId = schedulerId;
    }

    /**
     * @method getUserId
     * @description Gets the user ID associated with the scheduler.
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @method setUserId
     * @parameter userId The user ID to set.
     * @description Sets the user ID for the scheduler.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @method getMessage
     * @description Gets the scheduler message.
     * @return The scheduler message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @method setMessage
     * @parameter message The message to set for the scheduler.
     * @description Sets the scheduler message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @method getSlot
     * @description Gets the slot associated with the scheduler.
     * @return The slot object.
     */
    public FlipfitSlot getSlot() {
        return slot;
    }

    /**
     * @method setSlot
     * @parameter slot The slot to set for the scheduler.
     * @description Sets the slot for the scheduler.
     */
    public void setSlot(FlipfitSlot slot) {
        this.slot = slot;
    }
}