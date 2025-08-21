package com.flipfit.bean;

public class FlipfitScheduler {
    private int schedulerId;
    private int userId;
    private String message;
    private FlipfitSlot slot;

    public FlipfitScheduler() {
    }

    public FlipfitScheduler(int schedulerId, int userId, String message, FlipfitSlot slot) {
        this.schedulerId = schedulerId;
        this.userId = userId;
        this.message = message;
        this.slot = slot;
    }

    public int getSchedulerId() {
        return schedulerId;
    }

    public void setSchedulerId(int schedulerId) {
        this.schedulerId = schedulerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FlipfitSlot getSlot() {
        return slot;
    }

    public void setSlot(FlipfitSlot slot) {
        this.slot = slot;
    }

    public void updateSchedule() {
    }

    public void checkSchedule() {
    }
}