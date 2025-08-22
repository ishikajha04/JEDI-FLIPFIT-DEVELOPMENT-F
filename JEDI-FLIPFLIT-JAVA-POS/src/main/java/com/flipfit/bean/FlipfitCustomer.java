package com.flipfit.bean;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Flipfit Team
 * @description Represents a customer in the Flipfit system, extending FlipfitUser with customer-specific properties.
 */
public class FlipfitCustomer extends FlipfitUser {
    private int customerId;

    /**
     * @method FlipfitCustomer
     * @description Default constructor for FlipfitCustomer.
     */
    public FlipfitCustomer() {
        super();
    }

    /**
     * @method FlipfitCustomer
     * @parameter name The name of the customer.
     * @parameter email The email address of the customer.
     * @parameter phoneNumber The phone number of the customer.
     * @parameter password The password for the customer account.
     * @parameter customerId The unique ID for the customer.
     * @description Constructs a FlipfitCustomer with specified details.
     */
    public FlipfitCustomer(String name, String email, String phoneNumber, String password, int customerId) {
        super(name, email, phoneNumber, password);
        this.customerId = customerId;
    }

    /**
     * @method getCustomerId
     * @description Gets the customer ID.
     * @return The unique customer ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @method setCustomerId
     * @parameter customerId The unique customer ID to set.
     * @description Sets the customer ID.
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