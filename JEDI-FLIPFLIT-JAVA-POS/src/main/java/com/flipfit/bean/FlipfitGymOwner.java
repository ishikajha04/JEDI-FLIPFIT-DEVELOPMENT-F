package com.flipfit.bean;

/**
 * Represents a gym owner in the Flipfit system, extending FlipfitUser with owner-specific properties and approval status
 * @author Ishita, Shubham
 * @description This class models a gym owner user in the Flipfit system, extending the base user class
 * with owner-specific attributes including approval status for managing gym centers
 */
public class FlipfitGymOwner extends FlipfitUser {
    private int ownerId;
    private boolean isApproved;

    /**
     * Default constructor for FlipfitGymOwner
     * @method FlipfitGymOwner
     * @description Creates an empty FlipfitGymOwner object with default values
     */
    public FlipfitGymOwner() {
        super();
    }

    /**
     * Constructs a FlipfitGymOwner with specified details
     * @method FlipfitGymOwner
     * @param name The name of the gym owner
     * @param email The email address of the gym owner
     * @param phoneNumber The phone number of the gym owner
     * @param password The password for the gym owner account
     * @param ownerId The unique ID for the gym owner
     * @description Creates a complete gym owner profile with all required information, initially set as not approved
     */
    public FlipfitGymOwner(String name, String email, String phoneNumber, String password, int ownerId) {
        super(name, email, phoneNumber, password);
        this.ownerId = ownerId;
        this.isApproved = false; // Default to not approved
    }

    /**
     * Gets the owner ID
     * @method getOwnerId
     * @return The unique owner ID
     * @description Retrieves the system-generated unique identifier for this gym owner
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the owner ID
     * @method setOwnerId
     * @param ownerId The unique owner ID to set
     * @description Updates the gym owner's unique identifier in the system
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Checks if the gym owner is approved
     * @method isApproved
     * @return True if approved, false otherwise
     * @description Determines whether this gym owner has been approved by an administrator
     */
    public boolean isApproved() {
        return isApproved;
    }

    /**
     * Sets the approval status of the gym owner
     * @method setApproved
     * @param approved The approval status to set
     * @description Updates the approval status of the gym owner, typically done by an administrator
     */
    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    /**
     * Returns a string representation of the FlipfitGymOwner object
     * @method toString
     * @return A string containing the owner ID, approval status, name, email, and phone number
     * @description Creates a formatted string representation of this gym owner for display purposes
     */
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
