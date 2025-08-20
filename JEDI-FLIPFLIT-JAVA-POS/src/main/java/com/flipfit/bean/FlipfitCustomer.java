package com.flipfit.bean;

import java.util.Date;
import java.util.List;

public class FlipfitCustomer extends FlipfitUser {
    private int customerId;

    public FlipfitCustomer() {
        super();
    }

    public FlipfitCustomer(String name, String email, String phoneNumber, int customerId) {
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

    public List<FlipfitGymCenter> viewCenters() {
        return null;
    }

    public List<FlipfitSlot> viewAvailability(String day, int centerId) {
        return null;
    }

    public FlipfitBooking bookSlot() {
        return null;
    }

    public void cancelBooking() {
    }

    public List<FlipfitBooking> viewPlan(Date date) {
        return null;
    }
}