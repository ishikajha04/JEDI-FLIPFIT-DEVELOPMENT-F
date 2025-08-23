package com.flipfit.bean;

/**
 * @author Flipfit Team
 * @description Represents a notification sent to a user in the Flipfit system.
 */
public class FlipfitNotification {
    private int notificationId;
    private int userId;
    private String message;

    /**
     * @method FlipfitNotification
     * @description Default constructor for FlipfitNotification.
     */
    public FlipfitNotification() {
    }

    /**
     * @method FlipfitNotification
     * @parameter notificationId The unique notification ID.
     * @parameter userId The user ID to whom the notification is sent.
     * @parameter message The notification message.
     * @description Constructs a FlipfitNotification with specified details.
     */
    public FlipfitNotification(int notificationId, int userId, String message) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
    }

    /**
     * @method getNotificationId
     * @description Gets the notification ID.
     * @return The unique notification ID.
     */
    public int getNotificationId() {
        return notificationId;
    }

    /**
     * @method setNotificationId
     * @parameter notificationId The unique notification ID to set.
     * @description Sets the notification ID.
     */
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * @method getUserId
     * @description Gets the user ID associated with the notification.
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @method setUserId
     * @parameter userId The user ID to set.
     * @description Sets the user ID for the notification.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @method getMessage
     * @description Gets the notification message.
     * @return The notification message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @method setMessage
     * @parameter message The notification message to set.
     * @description Sets the notification message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @method pushNotification
     * @description Pushes the notification to the user.
     */
    public void pushNotification() {
        // Method implementation goes here
    }
}