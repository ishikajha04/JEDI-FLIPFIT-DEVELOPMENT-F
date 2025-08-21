package com.flipfit.business.impl;

import com.flipfit.bean.*;
import com.flipfit.dao.*;
import com.flipfit.dao.impl.*;
import com.flipfit.exception.DatabaseException;
import com.flipfit.business.FlipfitAdminService;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of FlipfitAdminService that uses MySQL DAO implementations
 */
public class FlipfitAdminServiceImpl implements FlipfitAdminService {
    private final FlipfitAdminDAO adminDAO;
    private final FlipfitGymOwnerDAO gymOwnerDAO;
    private final FlipfitGymCenterDAO gymCenterDAO;
    private final FlipfitCustomerDAO customerDAO;

    public FlipfitAdminServiceImpl() {
        this.adminDAO = new FlipfitAdminDAOImpl();
        this.gymOwnerDAO = new FlipfitGymOwnerDAOImpl();
        this.gymCenterDAO = new FlipfitGymCenterDAOImpl();
        this.customerDAO = new FlipfitCustomerDAOImpl();
    }

    @Override
    public FlipfitAdmin authenticateAdmin(String email, String password) {
        try {
            FlipfitAdmin admin = adminDAO.getAdminByEmail(email);
            if (admin != null && admin.getPassword().equals(password)) {
                return admin;
            }
            System.out.println("Invalid email or password");
            return null;
        } catch (DatabaseException e) {
            System.err.println("Database error during admin authentication: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error during admin authentication: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<FlipfitGymOwner> viewPendingGymOwnerRequests() {
        try {
            List<FlipfitGymOwner> pendingOwners = gymOwnerDAO.getPendingApprovalOwners();
            if (pendingOwners.isEmpty()) {
                System.out.println("No pending gym owner approval requests");
            } else {
                System.out.println("Found " + pendingOwners.size() + " pending gym owner requests");
            }
            return pendingOwners;
        } catch (DatabaseException e) {
            System.err.println("Database error while retrieving pending gym owner requests: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Unexpected error while retrieving pending gym owner requests: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean approveGymOwner(int ownerId) {
        try {
            FlipfitGymOwner owner = gymOwnerDAO.getGymOwnerById(ownerId);
            if (owner == null) {
                System.out.println("Error: Gym owner not found");
                return false;
            }

            boolean result = gymOwnerDAO.approveGymOwner(ownerId);
            if (result) {
                System.out.println("Gym owner approved successfully");
            } else {
                System.out.println("Failed to approve gym owner");
            }
            return result;
        } catch (DatabaseException e) {
            System.err.println("Database error while approving gym owner: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while approving gym owner: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean rejectGymOwner(int ownerId) {
        try {
            FlipfitGymOwner owner = gymOwnerDAO.getGymOwnerById(ownerId);
            if (owner == null) {
                System.out.println("Error: Gym owner not found");
                return false;
            }

            // For rejection, we delete the owner record
            boolean result = gymOwnerDAO.deleteGymOwner(ownerId);
            if (result) {
                System.out.println("Gym owner application rejected successfully");
            } else {
                System.out.println("Failed to reject gym owner application");
            }
            return result;
        } catch (DatabaseException e) {
            System.err.println("Database error while rejecting gym owner: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while rejecting gym owner: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<FlipfitGymCenter> viewPendingGymCenterRequests() {
        try {
            List<FlipfitGymCenter> pendingCenters = gymCenterDAO.getPendingApprovalCenters();
            if (pendingCenters.isEmpty()) {
                System.out.println("No pending gym center approval requests");
            } else {
                System.out.println("Found " + pendingCenters.size() + " pending gym center requests");
            }
            return pendingCenters;
        } catch (DatabaseException e) {
            System.err.println("Database error while retrieving pending gym center requests: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Unexpected error while retrieving pending gym center requests: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean approveGymCenter(int centerId) {
        try {
            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(centerId);
            if (center == null) {
                System.out.println("Error: Gym center not found");
                return false;
            }

            boolean result = gymCenterDAO.approveGymCenter(centerId);
            if (result) {
                System.out.println("Gym center approved successfully");
            } else {
                System.out.println("Failed to approve gym center");
            }
            return result;
        } catch (DatabaseException e) {
            System.err.println("Database error while approving gym center: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while approving gym center: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean rejectGymCenter(int centerId) {
        try {
            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(centerId);
            if (center == null) {
                System.out.println("Error: Gym center not found");
                return false;
            }

            // For rejection, we delete the center record
            boolean result = gymCenterDAO.deleteGymCenter(centerId);
            if (result) {
                System.out.println("Gym center application rejected successfully");
            } else {
                System.out.println("Failed to reject gym center application");
            }
            return result;
        } catch (DatabaseException e) {
            System.err.println("Database error while rejecting gym center: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while rejecting gym center: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<FlipfitGymCenter> viewAllGymCenters() {
        try {
            List<FlipfitGymCenter> centers = gymCenterDAO.getAllGymCenters();
            if (centers.isEmpty()) {
                System.out.println("No gym centers found in the system");
            } else {
                System.out.println("Found " + centers.size() + " gym centers");
            }
            return centers;
        } catch (DatabaseException e) {
            System.err.println("Database error while retrieving all gym centers: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Unexpected error while retrieving all gym centers: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<FlipfitGymOwner> viewAllGymOwners() {
        try {
            List<FlipfitGymOwner> owners = gymOwnerDAO.getAllGymOwners();
            if (owners.isEmpty()) {
                System.out.println("No gym owners found in the system");
            } else {
                System.out.println("Found " + owners.size() + " gym owners");
            }
            return owners;
        } catch (DatabaseException e) {
            System.err.println("Database error while retrieving all gym owners: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Unexpected error while retrieving all gym owners: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<FlipfitCustomer> viewAllCustomers() {
        try {
            List<FlipfitCustomer> customers = customerDAO.getAllCustomers();
            if (customers.isEmpty()) {
                System.out.println("No customers found in the system");
            } else {
                System.out.println("Found " + customers.size() + " customers");
            }
            return customers;
        } catch (DatabaseException e) {
            System.err.println("Database error while retrieving all customers: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Unexpected error while retrieving all customers: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public FlipfitAdmin getAdminProfile(int adminId) {
        try {
            FlipfitAdmin admin = adminDAO.getAdminById(adminId);
            if (admin == null) {
                System.out.println("Admin not found with ID: " + adminId);
            }
            return admin;
        } catch (DatabaseException e) {
            System.err.println("Database error while retrieving admin profile: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error while retrieving admin profile: " + e.getMessage());
            return null;
        }
    }
}
