package com.flipfit.bean;

import java.util.List;

public class FlipfitAdmin extends FlipfitUser {
    private int adminId;

    public FlipfitAdmin() {
        super();
    }

    public FlipfitAdmin(String name, String email, String phoneNumber, String password, int adminId) {
        super(name, email, phoneNumber, password);
        this.adminId = adminId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void approveGymOwner(int ownerId) {
        // Business logic will be implemented in service layer
    }

    public void approveGymCenter(int centerId) {
        // Business logic will be implemented in service layer
    }

    public List<FlipfitGymOwner> viewPendingOwnerRequests() {
        // Business logic will be implemented in service layer
        return null;
    }

    public List<FlipfitGymCenter> viewPendingCenterRequests() {
        // Business logic will be implemented in service layer
        return null;
    }

    public List<FlipfitGymCenter> viewAllCenters() {
        // Business logic will be implemented in service layer
        return null;
    }

    public List<FlipfitCustomer> viewAllCustomers() {
        // Business logic will be implemented in service layer
        return null;
    }
}
