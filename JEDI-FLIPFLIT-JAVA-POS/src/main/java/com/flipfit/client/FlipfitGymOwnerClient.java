package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.service.FlipfitGymOwnerService;
import com.flipfit.service.impl.FlipfitGymOwnerServiceImpl;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class FlipfitGymOwnerClient {
    private FlipfitGymOwnerService gymOwnerService;
    private Scanner scanner;
    private FlipfitGymOwner loggedInOwner;

    public FlipfitGymOwnerClient() {
        this.gymOwnerService = new FlipfitGymOwnerServiceImpl();
        this.scanner = new Scanner(System.in);
    }

    public void displayGymOwnerMenu() {
        while (true) {
            System.out.println("\n=== FLIPFIT GYM OWNER PORTAL ===");
            if (loggedInOwner == null) {
                System.out.println("1. Register as Gym Owner");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        registerGymOwner();
                        break;
                    case 2:
                        loginGymOwner();
                        break;
                    case 3:
                        System.out.println("Thank you for using Flipfit!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } else {
                if (!gymOwnerService.isOwnerApproved(loggedInOwner.getOwnerId())) {
                    System.out.println("Your account is pending approval by admin. Please wait for approval.");
                    System.out.println("1. Logout");
                    System.out.print("Choose an option: ");
                    getIntInput();
                    loggedInOwner = null;
                    continue;
                }

                System.out.println("Welcome, " + loggedInOwner.getName() + "!");
                System.out.println("1. Add Gym Center");
                System.out.println("2. View My Gym Centers");
                System.out.println("3. Update Gym Center");
                System.out.println("4. Remove Gym Center");
                System.out.println("5. Add Slot to Center");
                System.out.println("6. View Slots for Center");
                System.out.println("7. Update Slot");
                System.out.println("8. Remove Slot");
                System.out.println("9. View Bookings for Center");
                System.out.println("10. View Profile");
                System.out.println("11. Update Profile");
                System.out.println("12. Logout");
                System.out.print("Choose an option: ");

                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        addGymCenter();
                        break;
                    case 2:
                        viewMyGymCenters();
                        break;
                    case 3:
                        updateGymCenter();
                        break;
                    case 4:
                        removeGymCenter();
                        break;
                    case 5:
                        addSlotToCenter();
                        break;
                    case 6:
                        viewSlotsForCenter();
                        break;
                    case 7:
                        updateSlot();
                        break;
                    case 8:
                        removeSlot();
                        break;
                    case 9:
                        viewBookingsForCenter();
                        break;
                    case 10:
                        viewProfile();
                        break;
                    case 11:
                        updateProfile();
                        break;
                    case 12:
                        loggedInOwner = null;
                        System.out.println("Logged out successfully!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    private void registerGymOwner() {
        System.out.println("\n=== GYM OWNER REGISTRATION ===");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        FlipfitGymOwner owner = new FlipfitGymOwner(name, email, phoneNumber, password, 0);

        if (gymOwnerService.registerGymOwner(owner)) {
            System.out.println("Registration successful! Your account is pending admin approval.");
        } else {
            System.out.println("Registration failed. Email might already exist.");
        }
    }

    private void loginGymOwner() {
        System.out.println("\n=== GYM OWNER LOGIN ===");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        FlipfitGymOwner owner = gymOwnerService.authenticateGymOwner(email, password);
        if (owner != null) {
            loggedInOwner = owner;
            System.out.println("Login successful! Welcome, " + owner.getName());
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void addGymCenter() {
        System.out.println("\n=== ADD GYM CENTER ===");
        System.out.print("Enter gym center name: ");
        String name = scanner.nextLine();

        System.out.print("Enter location: ");
        String location = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter capacity: ");
        int capacity = getIntInput();

        FlipfitGymCenter center = new FlipfitGymCenter(0, loggedInOwner.getOwnerId(), name, location, address, capacity);

        if (gymOwnerService.addGymCenter(center)) {
            System.out.println("Gym center added successfully! It's pending admin approval.");
        } else {
            System.out.println("Failed to add gym center.");
        }
    }

    private void viewMyGymCenters() {
        System.out.println("\n=== MY GYM CENTERS ===");
        List<FlipfitGymCenter> centers = gymOwnerService.viewOwnedGymCenters(loggedInOwner.getOwnerId());

        if (centers.isEmpty()) {
            System.out.println("You have no gym centers.");
        } else {
            for (FlipfitGymCenter center : centers) {
                System.out.println("ID: " + center.getCenterId() +
                                 " | Name: " + center.getName() +
                                 " | Location: " + center.getLocation() +
                                 " | Address: " + center.getAddress() +
                                 " | Capacity: " + center.getCapacity() +
                                 " | Approved: " + (center.isApproved() ? "Yes" : "Pending"));
            }
        }
    }

    private void updateGymCenter() {
        viewMyGymCenters();
        System.out.print("Enter Gym Center ID to update: ");
        int centerId = getIntInput();

        List<FlipfitGymCenter> centers = gymOwnerService.viewOwnedGymCenters(loggedInOwner.getOwnerId());
        FlipfitGymCenter centerToUpdate = null;

        for (FlipfitGymCenter center : centers) {
            if (center.getCenterId() == centerId) {
                centerToUpdate = center;
                break;
            }
        }

        if (centerToUpdate == null) {
            System.out.println("Gym center not found or you don't own it.");
            return;
        }

        System.out.print("Enter new name (current: " + centerToUpdate.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) {
            centerToUpdate.setName(name);
        }

        System.out.print("Enter new location (current: " + centerToUpdate.getLocation() + "): ");
        String location = scanner.nextLine();
        if (!location.trim().isEmpty()) {
            centerToUpdate.setLocation(location);
        }

        System.out.print("Enter new address (current: " + centerToUpdate.getAddress() + "): ");
        String address = scanner.nextLine();
        if (!address.trim().isEmpty()) {
            centerToUpdate.setAddress(address);
        }

        if (gymOwnerService.updateGymCenter(centerToUpdate)) {
            System.out.println("Gym center updated successfully!");
        } else {
            System.out.println("Failed to update gym center.");
        }
    }

    private void removeGymCenter() {
        viewMyGymCenters();
        System.out.print("Enter Gym Center ID to remove: ");
        int centerId = getIntInput();

        if (gymOwnerService.removeGymCenter(centerId, loggedInOwner.getOwnerId())) {
            System.out.println("Gym center removed successfully!");
        } else {
            System.out.println("Failed to remove gym center. Please check the ID.");
        }
    }

    private void addSlotToCenter() {
        viewMyGymCenters();
        System.out.print("Enter Gym Center ID to add slot: ");
        int centerId = getIntInput();

        System.out.print("Enter start time (HH:MM): ");
        String startTimeStr = scanner.nextLine();

        System.out.print("Enter end time (HH:MM): ");
        String endTimeStr = scanner.nextLine();

        System.out.print("Enter capacity: ");
        int capacity = getIntInput();

        System.out.print("Enter day (e.g., Monday, Tuesday): ");
        String day = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = getDoubleInput();

        try {
            LocalTime startTime = LocalTime.parse(startTimeStr);
            LocalTime endTime = LocalTime.parse(endTimeStr);

            FlipfitSlot slot = new FlipfitSlot(0, centerId, startTime, endTime, capacity, day, price);

            if (gymOwnerService.addSlotToCenter(slot)) {
                System.out.println("Slot added successfully!");
            } else {
                System.out.println("Failed to add slot. Make sure the gym center exists and is approved.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please use HH:MM format.");
        }
    }

    private void viewSlotsForCenter() {
        viewMyGymCenters();
        System.out.print("Enter Gym Center ID: ");
        int centerId = getIntInput();

        List<FlipfitSlot> slots = gymOwnerService.viewSlotsForCenter(centerId, loggedInOwner.getOwnerId());

        if (slots.isEmpty()) {
            System.out.println("No slots found for this center.");
        } else {
            System.out.println("\n=== SLOTS FOR CENTER ===");
            for (FlipfitSlot slot : slots) {
                System.out.println("Slot ID: " + slot.getSlotId() +
                                 " | Day: " + slot.getDay() +
                                 " | Time: " + slot.getStartTime() + " - " + slot.getEndTime() +
                                 " | Capacity: " + slot.getCapacity() +
                                 " | Available: " + slot.getAvailableSeats() +
                                 " | Price: ₹" + slot.getPrice());
            }
        }
    }

    private void updateSlot() {
        System.out.print("Enter Slot ID to update: ");
        int slotId = getIntInput();

        System.out.print("Enter new price: ");
        double price = getDoubleInput();

        // For simplicity, we'll just update the price
        // In a real implementation, you'd fetch the slot first and update all fields
        System.out.println("Update slot functionality would be implemented here.");
        System.out.println("For this demo, slot update is simplified.");
    }

    private void removeSlot() {
        System.out.print("Enter Slot ID to remove: ");
        int slotId = getIntInput();

        if (gymOwnerService.removeSlot(slotId, loggedInOwner.getOwnerId())) {
            System.out.println("Slot removed successfully!");
        } else {
            System.out.println("Failed to remove slot. Please check the ID.");
        }
    }

    private void viewBookingsForCenter() {
        viewMyGymCenters();
        System.out.print("Enter Gym Center ID: ");
        int centerId = getIntInput();

        List<FlipfitBooking> bookings = gymOwnerService.viewBookingsForCenter(centerId, loggedInOwner.getOwnerId());

        if (bookings.isEmpty()) {
            System.out.println("No bookings found for this center.");
        } else {
            System.out.println("\n=== BOOKINGS FOR CENTER ===");
            for (FlipfitBooking booking : bookings) {
                System.out.println("Booking ID: " + booking.getBookingId() +
                                 " | Customer ID: " + booking.getCustomerId() +
                                 " | Slot ID: " + booking.getSlotId() +
                                 " | Date: " + booking.getBookingDate() +
                                 " | Status: " + booking.getStatus() +
                                 " | Amount: ₹" + booking.getAmount());
            }
        }
    }

    private void viewProfile() {
        System.out.println("\n=== MY PROFILE ===");
        System.out.println("Owner ID: " + loggedInOwner.getOwnerId());
        System.out.println("Name: " + loggedInOwner.getName());
        System.out.println("Email: " + loggedInOwner.getEmail());
        System.out.println("Phone: " + loggedInOwner.getPhoneNumber());
        System.out.println("Approved: " + (loggedInOwner.isApproved() ? "Yes" : "No"));
    }

    private void updateProfile() {
        System.out.println("\n=== UPDATE PROFILE ===");
        System.out.print("Enter new name (current: " + loggedInOwner.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) {
            loggedInOwner.setName(name);
        }

        System.out.print("Enter new phone number (current: " + loggedInOwner.getPhoneNumber() + "): ");
        String phone = scanner.nextLine();
        if (!phone.trim().isEmpty()) {
            loggedInOwner.setPhoneNumber(phone);
        }

        if (gymOwnerService.updateOwnerProfile(loggedInOwner)) {
            System.out.println("Profile updated successfully!");
        } else {
            System.out.println("Failed to update profile.");
        }
    }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

    private double getDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return 0.0;
        }
    }
}
