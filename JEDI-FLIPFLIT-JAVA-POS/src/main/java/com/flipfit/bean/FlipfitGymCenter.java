package com.flipfit.bean;

public class GymCenter {
    private int ownerId;
    private int id;
    private String name;
    private String location;

    public GymCenter() {
    }

    public GymCenter(int ownerId, int id, String name, String location) {
        this.ownerId = ownerId;
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void slotDisabled() {
    }

    public void addSlot() {
    }
}