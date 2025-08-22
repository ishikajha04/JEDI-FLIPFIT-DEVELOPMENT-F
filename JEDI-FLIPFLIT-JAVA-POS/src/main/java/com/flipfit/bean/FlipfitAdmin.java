package com.flipfit.bean;

import java.util.List;

/**
 * @author Flipfit Team
 * @description Represents an admin user in the Flipfit system, extending FlipfitUser with admin-specific properties.
 */
public class FlipfitAdmin extends FlipfitUser {
    private int adminId;

    /**
     * @method FlipfitAdmin
     * @description Default constructor for FlipfitAdmin.
     */
    public FlipfitAdmin() {
        super();
    }

    /**
     * @method FlipfitAdmin
     * @parameter name The name of the admin.
     * @parameter email The email address of the admin.
     * @parameter phoneNumber The phone number of the admin.
     * @parameter password The password for the admin account.
     * @parameter adminId The unique ID for the admin.
     * @description Constructs a FlipfitAdmin with specified details.
     */
    public FlipfitAdmin(String name, String email, String phoneNumber, String password, int adminId) {
        super(name, email, phoneNumber, password);
        this.adminId = adminId;
    }

    /**
     * @method getAdminId
     * @description Gets the admin ID.
     * @return The unique admin ID.
     */
    public int getAdminId() {
        return adminId;
    }

    /**
     * @method setAdminId
     * @parameter adminId The unique admin ID to set.
     * @description Sets the admin ID.
     */
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    /**
     * @method approveGymOwner
     * @parameter ownerId
     * @exception
     * @description
     */
    public void approveGymOwner(int ownerId) {
        // Business logic will be implemented in service layer
    }

    /**
     * @method approveGymCenter
     * @parameter centerId
     * @exception
     * @description
     */
    public void approveGymCenter(int centerId) {
        // Business logic will be implemented in service layer
    }

    /**
     * @method viewPendingOwnerRequests
     * @parameter
     * @exception
     * @description
     */
    public List<FlipfitGymOwner> viewPendingOwnerRequests() {
        // Business logic will be implemented in service layer
        return null;
    }

    /**
     * @method viewPendingCenterRequests
     * @parameter
     * @exception
     * @description
     */
    public List<FlipfitGymCenter> viewPendingCenterRequests() {
        // Business logic will be implemented in service layer
        return null;
    }

    /**
     * @method viewAllCenters
     * @parameter
     * @exception
     * @description
     */
    public List<FlipfitGymCenter> viewAllCenters() {
        // Business logic will be implemented in service layer
        return null;
    }

    /**
     * @method viewAllCustomers
     * @parameter
     * @exception
     * @description
     */
    public List<FlipfitCustomer> viewAllCustomers() {
        // Business logic will be implemented in service layer
        return null;
    }
}
