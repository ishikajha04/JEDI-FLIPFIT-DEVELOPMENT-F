package com.flipfit.bean;

public class Scheduler {
    private int schedulerId;
    private int userId;
    private String message;
    private Slot slot;

    public Scheduler() {
    }

    public Scheduler(int schedulerId, int userId, String message, Slot slot) {
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

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public void updateSchedule() {
    }

    public void checkSchedule() {
    }
}