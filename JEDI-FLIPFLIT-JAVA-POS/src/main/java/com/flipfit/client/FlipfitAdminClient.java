package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.FlipfitAdminService;
import com.flipfit.business.impl.FlipfitAdminServiceImpl;
import java.util.List;
import java.util.Scanner;

/**
 * @author Flipfit Team
 * @description Client class for Flipfit Admin operations, providing menu and admin actions.
 */
public class FlipfitAdminClient {
    private FlipfitAdminService adminService;
    private Scanner scanner;
    private FlipfitAdmin loggedInAdmin;

    /**
     * @method FlipfitAdminClient
     * @description Default constructor initializing admin service and scanner.
     */
    public FlipfitAdminClient() {
        this.adminService = new FlipfitAdminServiceImpl();
        this.scanner = new Scanner(System.in);
    }

    /**
     * @method displayAdminMenu
     * @description Displays the admin menu and handles user input for admin actions.
     */
    public void displayAdminMenu() {
        while (true) {
            System.out.println("\n=== FLIPFIT ADMIN PORTAL ===");
            if (loggedInAdmin == null) {
                System.out.println("1. Login");
                System.out.println("2. Exit");
                System.out.print("Choose an option: ");

                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        loginAdmin();
                        break;
                    case 2:
                        System.out.println("Thank you for using Flipfit Admin Portal!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } else {
                System.out.println("Welcome, " + loggedInAdmin.getName() + "!");
                System.out.println("1. View Pending Gym Owner Requests");
                System.out.println("2. Approve/Reject Gym Owner");
                System.out.println("3. View Pending Gym Center Requests");
                System.out.println("4. Approve/Reject Gym Center");
                System.out.println("5. View All Gym Centers");
                System.out.println("6. View All Gym Owners");
                System.out.println("7. View All Customers");
                System.out.println("8. View Profile");
                System.out.println("9. Logout");
                System.out.print("Choose an option: ");

                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        viewPendingGymOwnerRequests();
                        break;
                    case 2:
                        approveRejectGymOwner();
                        break;
                    case 3:
                        viewPendingGymCenterRequests();
                        break;
                    case 4:
                        approveRejectGymCenter();
                        break;
                    case 5:
                        viewAllGymCenters();
                        break;
                    case 6:
                        viewAllGymOwners();
                        break;
                    case 7:
                        viewAllCustomers();
                        break;
                    case 8:
                        viewProfile();
                        break;
                    case 9:
                        loggedInAdmin = null;
                        System.out.println("Logged out successfully!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    private void loginAdmin() {
        System.out.println("\n=== ADMIN LOGIN ===");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        FlipfitAdmin admin = adminService.authenticateAdmin(email, password);
        if (admin != null) {
            loggedInAdmin = admin;
            System.out.println("Login successful! Welcome, " + admin.getName());
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void viewPendingGymOwnerRequests() {
        System.out.println("\n=== PENDING GYM OWNER REQUESTS ===");
        List<FlipfitGymOwner> pendingOwners = adminService.viewPendingGymOwnerRequests();

        if (pendingOwners.isEmpty()) {
            System.out.println("No pending gym owner requests.");
        } else {
            for (FlipfitGymOwner owner : pendingOwners) {
                System.out.println("Owner ID: " + owner.getOwnerId() +
                                 " | Name: " + owner.getName() +
                                 " | Email: " + owner.getEmail() +
                                 " | Phone: " + owner.getPhoneNumber() +
                                 " | Status: Pending Approval");
            }
        }
    }

    private void approveRejectGymOwner() {
        viewPendingGymOwnerRequests();
        System.out.print("Enter Gym Owner ID to approve/reject: ");
        int ownerId = getIntInput();

        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.print("Choose action: ");
        int action = getIntInput();

        boolean success = false;
        if (action == 1) {
            success = adminService.approveGymOwner(ownerId);
            if (success) {
                System.out.println("Gym owner approved successfully!");
            }
        } else if (action == 2) {
            success = adminService.rejectGymOwner(ownerId);
            if (success) {
                System.out.println("Gym owner rejected successfully!");
            }
        }

        if (!success && (action == 1 || action == 2)) {
            System.out.println("Action failed. Please check the owner ID.");
        }
    }
    private void viewPendingGymCenterRequests() {
        System.out.println("\n=== PENDING GYM CENTER REQUESTS ===");
        List<FlipfitGymCenter> pendingCenters = adminService.viewPendingGymCenterRequests();

        if (pendingCenters.isEmpty()) {
            System.out.println("No pending gym center requests.");
        } else {
            for (FlipfitGymCenter center : pendingCenters) {
                System.out.println("ID: " + center.getCenterId() +
                                 " | Name: " + center.getName() +
                                 " | Location: " + center.getLocation() +
                                 " | Address: " + center.getAddress() +
                                 " | Owner ID: " + center.getOwnerId() +
                                 " | Status: Pending Approval");
            }
        }
    }

    private void approveRejectGymCenter() {
        viewPendingGymCenterRequests();
        System.out.print("Enter Gym Center ID to approve/reject: ");
        int centerId = getIntInput();

        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.print("Choose action: ");
        int action = getIntInput();

        boolean success = false;
        if (action == 1) {
            success = adminService.approveGymCenter(centerId);
            if (success) {
                System.out.println("Gym center approved successfully!");
            }
        } else if (action == 2) {
            success = adminService.rejectGymCenter(centerId);
            if (success) {
                System.out.println("Gym center rejected successfully!");
            }
        }

        if (!success && (action == 1 || action == 2)) {
            System.out.println("Action failed. Please check the center ID.");
        }
    }

    private void viewAllGymCenters() {
        System.out.println("\n=== ALL GYM CENTERS ===");
        List<FlipfitGymCenter> centers = adminService.viewAllGymCenters();

        if (centers.isEmpty()) {
            System.out.println("No gym centers found.");
        } else {
            for (FlipfitGymCenter center : centers) {
                System.out.println("Center ID: " + center.getCenterId() +
                                 " | Name: " + center.getName() +
                                 " | Location: " + center.getLocation() +
                                 " | Address: " + center.getAddress() +
                                 " | Owner ID: " + center.getOwnerId() +
                                 " | Approved: " + (center.isApproved() ? "Yes" : "No"));
            }
        }
    }

    private void viewAllGymOwners() {
        System.out.println("\n=== ALL GYM OWNERS ===");
        List<FlipfitGymOwner> owners = adminService.viewAllGymOwners();

        if (owners.isEmpty()) {
            System.out.println("No gym owners found.");
        } else {
            for (FlipfitGymOwner owner : owners) {
                System.out.println("Owner ID: " + owner.getOwnerId() +
                                 " | Name: " + owner.getName() +
                                 " | Email: " + owner.getEmail() +
                                 " | Phone: " + owner.getPhoneNumber() +
                                 " | Approved: " + (owner.isApproved() ? "Yes" : "No"));
            }
        }
    }

    private void viewAllCustomers() {
        System.out.println("\n=== ALL CUSTOMERS ===");
        List<FlipfitCustomer> customers = adminService.viewAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (FlipfitCustomer customer : customers) {
                System.out.println("Customer ID: " + customer.getCustomerId() +
                                 " | Name: " + customer.getName() +
                                 " | Email: " + customer.getEmail() +
                                 " | Phone: " + customer.getPhoneNumber());
            }
        }
    }

    private void viewProfile() {
        System.out.println("\n=== ADMIN PROFILE ===");
        System.out.println("Admin ID: " + loggedInAdmin.getAdminId());
        System.out.println("Name: " + loggedInAdmin.getName());
        System.out.println("Email: " + loggedInAdmin.getEmail());
        System.out.println("Phone: " + loggedInAdmin.getPhoneNumber());
    }

    private int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.print("Please enter a number: ");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
}
