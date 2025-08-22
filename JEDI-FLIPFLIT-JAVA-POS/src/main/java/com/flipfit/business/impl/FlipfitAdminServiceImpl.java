package com.flipfit.business.impl;

import com.flipfit.bean.*;
import com.flipfit.dao.*;
import com.flipfit.dao.impl.*;
import com.flipfit.exception.*;
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
            if (admin == null) {
                throw new UserNotFoundException("Admin with email " + email + " not found");
            }

            if (!admin.getPassword().equals(password)) {
                System.out.println("Invalid password");
                return null;
            }

            return admin;
        } catch (UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return null;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
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
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
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
                throw new UserNotFoundException(String.valueOf(ownerId), "Gym Owner");
            }

            boolean result = gymOwnerDAO.approveGymOwner(ownerId);
            if (result) {
                System.out.println("Gym owner approved successfully");
            } else {
                throw new RegistrationNotDoneException("Failed to approve gym owner in the database");
            }
            return result;
        } catch (UserNotFoundException | RegistrationNotDoneException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
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
                throw new UserNotFoundException(String.valueOf(ownerId), "Gym Owner");
            }

            // For rejection, we delete the owner record
            boolean result = gymOwnerDAO.deleteGymOwner(ownerId);
            if (result) {
                System.out.println("Gym owner application rejected successfully");
            } else {
                throw new RegistrationNotDoneException("Failed to reject gym owner application in the database");
            }
            return result;
        } catch (UserNotFoundException | RegistrationNotDoneException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
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
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
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
                throw new SlotNotFoundException("Gym center with ID " + centerId + " not found");
            }

            // Verify gym owner is approved
            FlipfitGymOwner owner = gymOwnerDAO.getGymOwnerById(center.getOwnerId());
            if (owner == null) {
                throw new UserNotFoundException(String.valueOf(center.getOwnerId()), "Gym Owner");
            }

            if (!owner.isApproved()) {
                throw new RegistrationNotDoneException("Cannot approve gym center for unapproved gym owner");
            }

            boolean result = gymCenterDAO.approveGymCenter(centerId);
            if (result) {
                System.out.println("Gym center approved successfully");
            } else {
                throw new RegistrationNotDoneException("Failed to approve gym center in the database");
            }
            return result;
        } catch (SlotNotFoundException | UserNotFoundException | RegistrationNotDoneException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
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
                throw new SlotNotFoundException("Gym center with ID " + centerId + " not found");
            }

            // For rejection, we delete the center record
            boolean result = gymCenterDAO.deleteGymCenter(centerId);
            if (result) {
                System.out.println("Gym center application rejected successfully");
            } else {
                throw new RegistrationNotDoneException("Failed to reject gym center application in the database");
            }
            return result;
        } catch (SlotNotFoundException | RegistrationNotDoneException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while rejecting gym center: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<FlipfitGymOwner> viewAllGymOwners() {
        try {
            List<FlipfitGymOwner> owners = gymOwnerDAO.getAllGymOwners();
            if (owners.isEmpty()) {
                System.out.println("No gym owners found in the system");
            }
            return owners;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Unexpected error while retrieving all gym owners: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<FlipfitGymCenter> viewAllGymCenters() {
        try {
            List<FlipfitGymCenter> centers = gymCenterDAO.getAllGymCenters();
            if (centers.isEmpty()) {
                System.out.println("No gym centers found in the system");
            }
            return centers;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Unexpected error while retrieving all gym centers: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<FlipfitCustomer> viewAllCustomers() {
        try {
            List<FlipfitCustomer> customers = customerDAO.getAllCustomers();
            if (customers.isEmpty()) {
                System.out.println("No customers found in the system");
            }
            return customers;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Unexpected error while retrieving all customers: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public FlipfitGymOwner viewGymOwnerById(int ownerId) {
        try {
            FlipfitGymOwner owner = gymOwnerDAO.getGymOwnerById(ownerId);
            if (owner == null) {
                throw new UserNotFoundException(String.valueOf(ownerId), "Gym Owner");
            }
            return owner;
        } catch (UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return null;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error while retrieving gym owner: " + e.getMessage());
            return null;
        }
    }

    @Override
    public FlipfitGymCenter viewGymCenterById(int centerId) {
        try {
            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(centerId);
            if (center == null) {
                throw new SlotNotFoundException("Gym center with ID " + centerId + " not found");
            }
            return center;
        } catch (SlotNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return null;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error while retrieving gym center: " + e.getMessage());
            return null;
        }
    }

    @Override
    public FlipfitCustomer viewCustomerById(int customerId) {
        try {
            FlipfitCustomer customer = customerDAO.getCustomerById(customerId);
            if (customer == null) {
                throw new UserNotFoundException(String.valueOf(customerId), "Customer");
            }
            return customer;
        } catch (UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return null;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error while retrieving customer: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean removeGymOwner(int ownerId) {
        try {
            FlipfitGymOwner owner = gymOwnerDAO.getGymOwnerById(ownerId);
            if (owner == null) {
                throw new UserNotFoundException(String.valueOf(ownerId), "Gym Owner");
            }

            // First, remove all associated gym centers
            List<FlipfitGymCenter> centers = gymCenterDAO.getGymCentersByOwnerId(ownerId);
            for (FlipfitGymCenter center : centers) {
                gymCenterDAO.deleteGymCenter(center.getCenterId());
            }

            boolean result = gymOwnerDAO.deleteGymOwner(ownerId);
            if (result) {
                System.out.println("Gym owner and associated gym centers removed successfully");
            } else {
                throw new DatabaseException("Failed to remove gym owner from the database");
            }
            return result;
        } catch (UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while removing gym owner: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeGymCenter(int centerId) {
        try {
            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(centerId);
            if (center == null) {
                throw new SlotNotFoundException("Gym center with ID " + centerId + " not found");
            }

            boolean result = gymCenterDAO.deleteGymCenter(centerId);
            if (result) {
                System.out.println("Gym center removed successfully");
            } else {
                throw new DatabaseException("Failed to remove gym center from the database");
            }
            return result;
        } catch (SlotNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while removing gym center: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeCustomer(int customerId) {
        try {
            FlipfitCustomer customer = customerDAO.getCustomerById(customerId);
            if (customer == null) {
                throw new UserNotFoundException(String.valueOf(customerId), "Customer");
            }

            boolean result = customerDAO.deleteCustomer(customerId);
            if (result) {
                System.out.println("Customer removed successfully");
            } else {
                throw new DatabaseException("Failed to remove customer from the database");
            }
            return result;
        } catch (UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while removing customer: " + e.getMessage());
            return false;
        }
    }

    @Override
    public FlipfitAdmin getAdminProfile(int adminId) {
        try {
            FlipfitAdmin admin = adminDAO.getAdminById(adminId);
            if (admin == null) {
                throw new UserNotFoundException(String.valueOf(adminId), "Admin");
            }
            return admin;
        } catch (UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error retrieving admin profile: " + e.getMessage());
            return null;
        }
    }
}
