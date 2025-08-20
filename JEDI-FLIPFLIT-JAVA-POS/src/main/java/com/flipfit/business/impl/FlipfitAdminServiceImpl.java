package com.flipfit.service.impl;

import com.flipfit.bean.*;
import com.flipfit.dao.*;
import com.flipfit.dao.impl.*;
import com.flipfit.service.FlipfitAdminService;
import java.util.List;

public class FlipfitAdminServiceImpl implements FlipfitAdminService {
    private FlipfitAdminDAO adminDAO;
    private FlipfitGymOwnerDAO gymOwnerDAO;
    private FlipfitGymCenterDAO gymCenterDAO;
    private FlipfitCustomerDAO customerDAO;

    public FlipfitAdminServiceImpl() {
        this.adminDAO = new FlipfitAdminDAOImpl();
        this.gymOwnerDAO = new FlipfitGymOwnerDAOImpl();
        this.gymCenterDAO = new FlipfitGymCenterDAOImpl();
        this.customerDAO = new FlipfitCustomerDAOImpl();
    }

    @Override
    public FlipfitAdmin authenticateAdmin(String email, String password) {
        FlipfitAdmin admin = adminDAO.getAdminByEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }

    @Override
    public List<FlipfitGymOwner> viewPendingGymOwnerRequests() {
        return gymOwnerDAO.getPendingApprovalOwners();
    }

    @Override
    public boolean approveGymOwner(int ownerId) {
        return gymOwnerDAO.approveGymOwner(ownerId);
    }

    @Override
    public boolean rejectGymOwner(int ownerId) {
        // For rejection, we can either delete the owner or mark as rejected
        // For simplicity, we'll delete the owner record
        return gymOwnerDAO.deleteGymOwner(ownerId);
    }

    @Override
    public List<FlipfitGymCenter> viewPendingGymCenterRequests() {
        return gymCenterDAO.getPendingApprovalCenters();
    }

    @Override
    public boolean approveGymCenter(int centerId) {
        return gymCenterDAO.approveGymCenter(centerId);
    }

    @Override
    public boolean rejectGymCenter(int centerId) {
        // For rejection, we can either delete the center or mark as rejected
        // For simplicity, we'll delete the center record
        return gymCenterDAO.deleteGymCenter(centerId);
    }

    @Override
    public List<FlipfitGymCenter> viewAllGymCenters() {
        return gymCenterDAO.getAllGymCenters();
    }

    @Override
    public List<FlipfitGymOwner> viewAllGymOwners() {
        return gymOwnerDAO.getAllGymOwners();
    }

    @Override
    public List<FlipfitCustomer> viewAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @Override
    public FlipfitAdmin getAdminProfile(int adminId) {
        return adminDAO.getAdminById(adminId);
    }
}
