package com.flipfit.bean;

/**
 * @author Flipfit Team
 * @description Represents a gym center in the Flipfit system, including details such as owner, name, location, and approval status.
 */
public class FlipfitGymCenter {
    private int centerId;
    private int ownerId;
    private String name;
    private String location;
    private String address;
    private boolean isApproved;

    /**
     * @method FlipfitGymCenter
     * @description Default constructor for FlipfitGymCenter.
     */
    public FlipfitGymCenter() {
    }

    /**
     * @method FlipfitGymCenter
     * @parameter centerId The unique center ID.
     * @parameter ownerId The owner ID of the gym center.
     * @parameter name The name of the gym center.
     * @parameter location The location of the gym center.
     * @parameter address The address of the gym center.
     * @description Constructs a FlipfitGymCenter with specified details.
     */
    public FlipfitGymCenter(int centerId, int ownerId, String name, String location, String address) {
        this.centerId = centerId;
        this.ownerId = ownerId;
        this.name = name;
        this.location = location;
        this.address = address;
        this.isApproved = false; // Default to not approved
    }

    // Getters and Setters
    /**
     * @method getCenterId
     * @description Gets the center ID.
     * @return The unique center ID.
     */
    public int getCenterId() {
        return centerId;
    }

    /**
     * @method setCenterId
     * @parameter centerId The unique center ID to set.
     * @description Sets the center ID.
     */
    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    /**
     * @method getOwnerId
     * @description Gets the owner ID of the gym center.
     * @return The owner ID.
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * @method setOwnerId
     * @parameter ownerId The owner ID to set.
     * @description Sets the owner ID of the gym center.
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @method getName
     * @description Gets the name of the gym center.
     * @return The name of the gym center.
     */
    public String getName() {
        return name;
    }

    /**
     * @method setName
     * @parameter name The name to set for the gym center.
     * @description Sets the name of the gym center.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @method getLocation
     * @description Gets the location of the gym center.
     * @return The location of the gym center.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @method setLocation
     * @parameter location The location to set for the gym center.
     * @description Sets the location of the gym center.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @method getAddress
     * @description Gets the address of the gym center.
     * @return The address of the gym center.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @method setAddress
     * @parameter address The address to set for the gym center.
     * @description Sets the address of the gym center.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @method isApproved
     * @description Checks if the gym center is approved.
     * @return True if approved, false otherwise.
     */
    public boolean isApproved() {
        return isApproved;
    }

    /**
     * @method setApproved
     * @parameter approved The approval status to set.
     * @description Sets the approval status of the gym center.
     */
    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    /**
     * @method toString
     * @description Returns a string representation of the FlipfitGymCenter object.
     * @return A string containing the center ID, name, location, address, and approval status.
     */
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