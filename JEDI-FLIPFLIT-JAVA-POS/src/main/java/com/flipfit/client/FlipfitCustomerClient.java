package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.FlipfitCustomerService;
import com.flipfit.business.impl.FlipfitCustomerServiceImpl;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class FlipfitCustomerClient {
    private FlipfitCustomerService customerService;
    private Scanner scanner;
    private FlipfitCustomer loggedInCustomer;

    public FlipfitCustomerClient() {
        this.customerService = new FlipfitCustomerServiceImpl();
        this.scanner = new Scanner(System.in);
    }

    public void displayCustomerMenu() {
        while (true) {
            System.out.println("\n=== FLIPFIT CUSTOMER PORTAL ===");
            if (loggedInCustomer == null) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        registerCustomer();
                        break;
                    case 2:
                        loginCustomer();
                        break;
                    case 3:
                        System.out.println("Thank you for using Flipfit!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } else {
                System.out.println("Welcome, " + loggedInCustomer.getName() + "!");
                System.out.println("1. View Available Gym Centers");
                System.out.println("2. Search Gym Centers by Location");
                System.out.println("3. View Available Slots");
                System.out.println("4. Book a Slot");
                System.out.println("5. View My Bookings");
                System.out.println("6. Cancel Booking");
                System.out.println("7. View Profile");
                System.out.println("8. Update Profile");
                System.out.println("9. Logout");
                System.out.print("Choose an option: ");

                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        viewAvailableGymCenters();
                        break;
                    case 2:
                        searchGymCentersByLocation();
                        break;
                    case 3:
                        viewAvailableSlots();
                        break;
                    case 4:
                        bookSlot();
                        break;
                    case 5:
                        viewMyBookings();
                        break;
                    case 6:
                        cancelBooking();
                        break;
                    case 7:
                        viewProfile();
                        break;
                    case 8:
                        updateProfile();
                        break;
                    case 9:
                        loggedInCustomer = null;
                        System.out.println("Logged out successfully!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    private void registerCustomer() {
        System.out.println("\n=== CUSTOMER REGISTRATION ===");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        FlipfitCustomer customer = new FlipfitCustomer(name, email, phoneNumber, password, 0);

        if (customerService.registerCustomer(customer)) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed. Email might already exist.");
        }
    }

    private void loginCustomer() {
        System.out.println("\n=== CUSTOMER LOGIN ===");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        FlipfitCustomer customer = customerService.authenticateCustomer(email, password);
        if (customer != null) {
            loggedInCustomer = customer;
            System.out.println("Login successful! Welcome, " + customer.getName());
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void viewAvailableGymCenters() {
        System.out.println("\n=== AVAILABLE GYM CENTERS ===");
        List<FlipfitGymCenter> centers = customerService.viewAvailableGymCenters();

        if (centers.isEmpty()) {
            System.out.println("No gym centers available.");
        } else {
            for (FlipfitGymCenter center : centers) {
                System.out.println("ID: " + center.getCenterId() +
                                 " | Name: " + center.getName() +
                                 " | Location: " + center.getLocation() +
                                 " | Address: " + center.getAddress() +
                                 " | Capacity: " + center.getCapacity());
            }
        }
    }

    private void searchGymCentersByLocation() {
        System.out.print("Enter location to search: ");
        String location = scanner.nextLine();

        List<FlipfitGymCenter> centers = customerService.viewGymCentersByLocation(location);

        if (centers.isEmpty()) {
            System.out.println("No gym centers found in " + location);
        } else {
            System.out.println("\n=== GYM CENTERS IN " + location.toUpperCase() + " ===");
            for (FlipfitGymCenter center : centers) {
                System.out.println("ID: " + center.getCenterId() +
                                 " | Name: " + center.getName() +
                                 " | Address: " + center.getAddress());
            }
        }
    }

    private void viewAvailableSlots() {
        System.out.print("Enter Gym Center ID: ");
        int centerId = getIntInput();

        System.out.print("Enter day (e.g., Monday, Tuesday): ");
        String day = scanner.nextLine();

        List<FlipfitSlot> slots = customerService.viewAvailableSlots(centerId, day);

        if (slots.isEmpty()) {
            System.out.println("No available slots for the selected center and day.");
        } else {
            System.out.println("\n=== AVAILABLE SLOTS ===");
            for (FlipfitSlot slot : slots) {
                System.out.println("Slot ID: " + slot.getSlotId() +
                                 " | Time: " + slot.getStartTime() + " - " + slot.getEndTime() +
                                 " | Available Seats: " + slot.getAvailableSeats() +
                                 " | Price: ₹" + slot.getPrice());
            }
        }
    }

    private void bookSlot() {
        System.out.print("Enter Slot ID to book: ");
        int slotId = getIntInput();

        System.out.print("Enter booking date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();

        try {
            LocalDate bookingDate = LocalDate.parse(dateStr);
            FlipfitBooking booking = customerService.bookSlot(loggedInCustomer.getCustomerId(), slotId, bookingDate);

            if (booking != null) {
                System.out.println("Booking successful! Booking ID: " + booking.getBookingId());
                System.out.println("Amount paid: ₹" + booking.getAmount());
            } else {
                System.out.println("Booking failed. Slot might not be available.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private void viewMyBookings() {
        System.out.println("\n=== MY BOOKINGS ===");
        List<FlipfitBooking> bookings = customerService.viewBookings(loggedInCustomer.getCustomerId());

        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            for (FlipfitBooking booking : bookings) {
                System.out.println("Booking ID: " + booking.getBookingId() +
                                 " | Slot ID: " + booking.getSlotId() +
                                 " | Center ID: " + booking.getCenterId() +
                                 " | Date: " + booking.getBookingDate() +
                                 " | Status: " + booking.getStatus() +
                                 " | Amount: ₹" + booking.getAmount());
            }
        }
    }

    private void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = getIntInput();

        if (customerService.cancelBooking(bookingId, loggedInCustomer.getCustomerId())) {
            System.out.println("Booking cancelled successfully!");
        } else {
            System.out.println("Failed to cancel booking. Please check the booking ID.");
        }
    }

    private void viewProfile() {
        System.out.println("\n=== MY PROFILE ===");
        System.out.println("Customer ID: " + loggedInCustomer.getCustomerId());
        System.out.println("Name: " + loggedInCustomer.getName());
        System.out.println("Email: " + loggedInCustomer.getEmail());
        System.out.println("Phone: " + loggedInCustomer.getPhoneNumber());
    }

    private void updateProfile() {
        System.out.println("\n=== UPDATE PROFILE ===");
        System.out.print("Enter new name (current: " + loggedInCustomer.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) {
            loggedInCustomer.setName(name);
        }

        System.out.print("Enter new phone number (current: " + loggedInCustomer.getPhoneNumber() + "): ");
        String phone = scanner.nextLine();
        if (!phone.trim().isEmpty()) {
            loggedInCustomer.setPhoneNumber(phone);
        }

        if (customerService.updateCustomerProfile(loggedInCustomer)) {
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
}
