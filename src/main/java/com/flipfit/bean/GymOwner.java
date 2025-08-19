package com.flipfit.bean;

public class GymOwner extends User {
    private int ownerId;
    private boolean isApproved;


    public GymOwner() {
        super();
    }

    public GymOwner(String name, String email, String phoneNumber, int ownerId, boolean isApproved) {
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
