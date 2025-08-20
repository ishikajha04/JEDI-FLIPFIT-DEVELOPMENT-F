package com.flipfit.bean;

public class FlipfitGymOwner extends FlipfitUser {
    private int ownerId;
    private boolean isApproved;


    public FlipfitGymOwner() {
        super();
    }

    public FlipfitGymOwner(String name, String email, String phoneNumber, int ownerId, boolean isApproved) {
        super(name, email, phoneNumber);
        this.ownerId = ownerId;
        this.isApproved = isApproved;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public void registerCenter() {
    }

    public void removeCenter() {
    }

    public void createBooking() {
    }
}
