package com.flipfit.bean;

/**
 * @author Flipfit Team
 * @description Represents a gym owner in the Flipfit system, extending FlipfitUser with owner-specific properties and approval status.
 */
public class FlipfitGymOwner extends FlipfitUser {
    private int ownerId;
    private boolean isApproved;

    /**
     * @method FlipfitGymOwner
     * @description Default constructor for FlipfitGymOwner.
     */
    public FlipfitGymOwner() {
        super();
    }

    /**
     * @method FlipfitGymOwner
     * @parameter name The name of the gym owner.
     * @parameter email The email address of the gym owner.
     * @parameter phoneNumber The phone number of the gym owner.
     * @parameter password The password for the gym owner account.
     * @parameter ownerId The unique ID for the gym owner.
     * @description Constructs a FlipfitGymOwner with specified details.
     */
    public FlipfitGymOwner(String name, String email, String phoneNumber, String password, int ownerId) {
        super(name, email, phoneNumber, password);
        this.ownerId = ownerId;
        this.isApproved = false; // Default to not approved
    }

    /**
     * @method getOwnerId
     * @description Gets the owner ID.
     * @return The unique owner ID.
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * @method setOwnerId
     * @parameter ownerId The unique owner ID to set.
     * @description Sets the owner ID.
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @method isApproved
     * @description Checks if the gym owner is approved.
     * @return True if approved, false otherwise.
     */
    public boolean isApproved() {
        return isApproved;
    }

    /**
     * @method setApproved
     * @parameter approved The approval status to set.
     * @description Sets the approval status of the gym owner.
     */
    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    /**
     * @method toString
     * @description Returns a string representation of the FlipfitGymOwner object.
     * @return A string containing the owner ID, approval status, name, email, and phone number.
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
