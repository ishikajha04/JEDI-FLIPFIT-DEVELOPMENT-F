package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.FlipfitGymOwnerService;
import com.flipfit.business.impl.FlipfitGymOwnerServiceImpl;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Flipfit Team
 * @description Client class for Flipfit Gym Owner operations, providing menu and owner actions.
 */
public class FlipfitGymOwnerClient {
    private FlipfitGymOwnerService gymOwnerService;
    private Scanner scanner;
    private FlipfitGymOwner loggedInOwner;

    /**
     * @method FlipfitGymOwnerClient
     * @description Default constructor initializing gym owner service and scanner.
     */
    public FlipfitGymOwnerClient() {
        this.gymOwnerService = new FlipfitGymOwnerServiceImpl();
        this.scanner = new Scanner(System.in);
    }

    /**
     * @method displayGymOwnerMenu
     * @description Displays the gym owner menu and handles user input for owner actions.
     */
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

        FlipfitGymCenter center = new FlipfitGymCenter(0, loggedInOwner.getOwnerId(), name, location, address);

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

        // First, get the existing slot to verify ownership and show current values
        FlipfitSlot existingSlot = null;
        // We need to find the slot through our centers
        List<FlipfitGymCenter> ownedCenters = gymOwnerService.viewOwnedGymCenters(loggedInOwner.getOwnerId());

        boolean slotFound = false;
        for (FlipfitGymCenter center : ownedCenters) {
            List<FlipfitSlot> slots = gymOwnerService.viewSlotsForCenter(center.getCenterId(), loggedInOwner.getOwnerId());
            for (FlipfitSlot slot : slots) {
                if (slot.getSlotId() == slotId) {
                    existingSlot = slot;
                    slotFound = true;
                    break;
                }
            }
            if (slotFound) break;
        }

        if (!slotFound || existingSlot == null) {
            System.out.println("Slot not found or you don't own this slot.");
            return;
        }

        // Display current slot information
        System.out.println("\n=== CURRENT SLOT INFORMATION ===");
        System.out.println("Slot ID: " + existingSlot.getSlotId());
        System.out.println("Day: " + existingSlot.getDay());
        System.out.println("Time: " + existingSlot.getStartTime() + " - " + existingSlot.getEndTime());
        System.out.println("Capacity: " + existingSlot.getCapacity());
        System.out.println("Available Seats: " + existingSlot.getAvailableSeats());
        System.out.println("Price: ₹" + existingSlot.getPrice());
        System.out.println("Available: " + (existingSlot.isAvailable() ? "Yes" : "No"));

        System.out.println("\n=== UPDATE SLOT ===");
        System.out.println("Leave blank to keep current value");

        // Update start time
        System.out.print("Enter new start time (HH:MM) [Current: " + existingSlot.getStartTime() + "]: ");
        scanner.nextLine(); // consume newline
        String startTimeStr = scanner.nextLine().trim();
        if (!startTimeStr.isEmpty()) {
            try {
                existingSlot.setStartTime(java.time.LocalTime.parse(startTimeStr));
            } catch (Exception e) {
                System.out.println("Invalid time format. Keeping current start time.");
            }
        }

        // Update end time
        System.out.print("Enter new end time (HH:MM) [Current: " + existingSlot.getEndTime() + "]: ");
        String endTimeStr = scanner.nextLine().trim();
        if (!endTimeStr.isEmpty()) {
            try {
                existingSlot.setEndTime(java.time.LocalTime.parse(endTimeStr));
            } catch (Exception e) {
                System.out.println("Invalid time format. Keeping current end time.");
            }
        }

        // Update capacity
        System.out.print("Enter new capacity [Current: " + existingSlot.getCapacity() + "]: ");
        String capacityStr = scanner.nextLine().trim();
        if (!capacityStr.isEmpty()) {
            try {
                int newCapacity = Integer.parseInt(capacityStr);
                if (newCapacity > 0) {
                    int currentBookings = existingSlot.getCapacity() - existingSlot.getAvailableSeats();
                    existingSlot.setCapacity(newCapacity);
                    existingSlot.setAvailableSeats(Math.max(0, newCapacity - currentBookings));
                } else {
                    System.out.println("Capacity must be positive. Keeping current capacity.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid capacity. Keeping current capacity.");
            }
        }

        // Update price
        System.out.print("Enter new price [Current: ₹" + existingSlot.getPrice() + "]: ");
        String priceStr = scanner.nextLine().trim();
        if (!priceStr.isEmpty()) {
            try {
                double newPrice = Double.parseDouble(priceStr);
                if (newPrice >= 0) {
                    existingSlot.setPrice(newPrice);
                } else {
                    System.out.println("Price cannot be negative. Keeping current price.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Keeping current price.");
            }
        }

        // Update day
        System.out.print("Enter new day (e.g., Monday, Tuesday) [Current: " + existingSlot.getDay() + "]: ");
        String dayStr = scanner.nextLine().trim();
        if (!dayStr.isEmpty()) {
            existingSlot.setDay(dayStr);
        }

        // Confirm and save changes
        System.out.print("\nSave changes? (1 for Yes, 0 for No): ");
        int confirm = getIntInput();

        if (confirm == 1) {
            if (gymOwnerService.updateSlot(existingSlot)) {
                System.out.println("Slot updated successfully!");
            } else {
                System.out.println("Failed to update slot. Please try again.");
            }
        } else {
            System.out.println("Update cancelled.");
        }
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

    private double getDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return 0.0;
        }
    }
}
