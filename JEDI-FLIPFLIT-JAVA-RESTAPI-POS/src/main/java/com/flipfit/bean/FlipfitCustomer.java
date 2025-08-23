package com.flipfit.bean;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a customer in the Flipfit system, extending FlipfitUser with customer-specific properties
 * @author Khushi, Kritika
 * @description This class models a customer user in the Flipfit system, extending the base user class
 * with customer-specific attributes and behavior for booking gym slots
 */
public class FlipfitCustomer extends FlipfitUser {
    private int customerId;

    /**
     * Default constructor for FlipfitCustomer
     * @method FlipfitCustomer
     * @description Creates an empty FlipfitCustomer object with default values
     */
    public FlipfitCustomer() {
        super();
    }

    /**
     * Constructs a FlipfitCustomer with specified details
     * @method FlipfitCustomer
     * @param name The name of the customer
     * @param email The email address of the customer
     * @param phoneNumber The phone number of the customer
     * @param password The password for the customer account
     * @param customerId The unique ID for the customer
     * @description Creates a complete customer profile with all required information
     */
    public FlipfitCustomer(String name, String email, String phoneNumber, String password, int customerId) {
        super(name, email, phoneNumber, password);
        this.customerId = customerId;
    }

    /**
     * Gets the customer ID
     * @method getCustomerId
     * @return The unique customer ID
     * @description Retrieves the system-generated unique identifier for this customer
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID
     * @method setCustomerId
     * @param customerId The unique customer ID to set
     * @description Updates the customer's unique identifier in the system
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @method toString
     * @description Returns a string representation of the customer.
     * @return A string containing customer details.
     */
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