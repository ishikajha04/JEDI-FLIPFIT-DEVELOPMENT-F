package com.flipfit.bean;

public class FlipfitWaitlist {
    private String waitlistId;
    private String userId;
    private String slotId;
    private int position;

    public String getWaitlistId() {
        return waitlistId;
    }

    public void setWaitlistId(String waitlistId) {
        this.waitlistId = waitlistId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void notifyPromotion() {}
    public void addToWaitlist() {}
    public void removeFromWaitlist() {}
}