package com.flipfit.bean;

public class Notification {

    private String notificationId;
    private String userId;
    private String message;
    private boolean isRead;
    private String date;

    public Notification() {
        super();
    }

    public Notification(String notificationId, String userId, String message) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
    }

    // Getters and setters for all private fields

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void pushNotification() {
    }
}
