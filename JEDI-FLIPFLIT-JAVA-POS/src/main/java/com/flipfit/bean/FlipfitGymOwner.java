package com.flipfit.bean;

public class FlipfitGymOwner extends FlipfitUser {
    private int ownerId;
    private boolean isApproved;

    public FlipfitGymOwner() {
        super();
    }

    public FlipfitGymOwner(String name, String email, String phoneNumber, String password, int ownerId) {
        super(name, email, phoneNumber, password);
        this.ownerId = ownerId;
        this.isApproved = false; // Default to not approved
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

    @Override
    public String toString() {
        return "FlipfitGymOwner{" +
                "ownerId=" + ownerId +
                ", isApproved=" + isApproved +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                '}';
    }
}
