package com.flipfit.bean;

import java.time.LocalDate;
import java.util.List;

public class FlipfitCustomer extends FlipfitUser {
    private int customerId;

    public FlipfitCustomer() {
        super();
    }

    public FlipfitCustomer(String name, String email, String phoneNumber, String password, int customerId) {
        super(name, email, phoneNumber, password);
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "FlipfitCustomer{" +
                "customerId=" + customerId +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                '}';
    }
}