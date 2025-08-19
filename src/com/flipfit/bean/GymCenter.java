package com.flipfit.bean;

public class GymCenter {
    private String gymId;
    private String gymName;
    private String ownerEmail;
    private String address;
    private int slotCount;
    private int seatsPerSlotCount;
    private boolean isVerified;

    public GymCenter() {

    }

    public GymCenter(String gymId, String gymName, String ownerEmail, String address, int slotCount, int seatsPerSlotCount, boolean isVerified) {
        this.gymId = gymId;
        this.gymName = gymName;
        this.ownerEmail = ownerEmail;
        this.address = address;
        this.slotCount = slotCount;
        this.seatsPerSlotCount = seatsPerSlotCount;
        this.isVerified = isVerified;
    }

    public String getGymId() {
        return gymId;
    }

    public void setGymId(String gymId) {
        this.gymId = gymId;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSlotCount() {
        return slotCount;
    }

    public void setSlotCount(int slotCount) {
        this.slotCount = slotCount;
    }

    public int getSeatsPerSlotCount() {
        return seatsPerSlotCount;
    }

    public void setSeatsPerSlotCount(int seatsPerSlotCount) {
        this.seatsPerSlotCount = seatsPerSlotCount;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }


    public String toString() {
        String s = "Gym Id : " + this.gymId +
                "\nGym Name : " + this.gymName +
                "\nGym Owner Email : " + this.getOwnerEmail() +
                "\nGym Address : " + this.getAddress() +
                "\nGym Slotcount : " + this.getSlotCount() +
                "\nSeat per slot count : " + this.getSeatsPerSlotCount() +
                "\nVerification : " + (this.isVerified() ? "Yes" : "No");
        return s;
    }
}