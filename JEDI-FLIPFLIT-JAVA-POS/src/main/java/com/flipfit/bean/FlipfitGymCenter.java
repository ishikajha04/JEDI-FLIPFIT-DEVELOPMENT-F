package com.flipfit.bean;

public class FlipfitGymCenter {
    private int centerId;
    private int ownerId;
    private String name;
    private String location;
    private String address;
    private boolean isApproved;

    public FlipfitGymCenter() {
    }

    public FlipfitGymCenter(int centerId, int ownerId, String name, String location, String address) {
        this.centerId = centerId;
        this.ownerId = ownerId;
        this.name = name;
        this.location = location;
        this.address = address;
        this.isApproved = false; // Default to not approved
    }

    // Getters and Setters
    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    @Override
    public String toString() {
        return "FlipfitGymCenter{" +
                "centerId=" + centerId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", isApproved=" + isApproved +
                '}';
    }
}