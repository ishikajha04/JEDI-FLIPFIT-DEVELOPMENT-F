package com.flipfit.bean;

import java.util.Date;
import java.util.List;

public class Customer extends User {
    private int customerId;

    public Customer() {
        super();
    }

    public Customer(String name, String email, String phoneNumber, int customerId) {
        super(name, email, phoneNumber);
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void register() {
    }

    public List<GymCenter> viewCenters() {
        return null;
    }

    public List<Slot> viewAvailability(String day, int centerId) {
        return null;
    }

    public Booking bookSlot() {
        return null;
    }

    public void cancelBooking() {
    }

    public List<Booking> viewPlan(Date date) {
        return null;
    }
}