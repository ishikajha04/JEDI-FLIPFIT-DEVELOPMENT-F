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
                System.out.println("9. Payment Methods");
                System.out.println("10. Logout");
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
                        managePaymentMethods();
                        break;
                    case 10:
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
        // First check if customer has any saved cards
        List<FlipfitCard> cards = customerService.getCustomerCards(loggedInCustomer.getCustomerId());
        if (cards.isEmpty()) {
            System.out.println("\nNo payment methods found. You need to add a card before booking.");
            System.out.print("Would you like to add a card now? (1 for Yes, 0 for No): ");
            int addCard = getIntInput();
            if (addCard == 1) {
                managePaymentMethods();
            } else {
                return;
            }
        }

        System.out.print("Enter Slot ID to book: ");
        int slotId = getIntInput();

        System.out.print("Enter booking date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();

        try {
            LocalDate bookingDate = LocalDate.parse(dateStr);
            FlipfitBooking booking = customerService.bookSlot(loggedInCustomer.getCustomerId(), slotId, bookingDate);

            if (booking != null) {
                System.out.println("\nBooking successful!");
                System.out.println("Booking ID: " + booking.getBookingId());
                System.out.println("Amount paid: ₹" + booking.getAmount());
                System.out.println("You will be redirected to the main menu...");
            } else {
                System.out.println("Booking failed. Please ensure you have a valid payment method and try again.");
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

    private void managePaymentMethods() {
        while (true) {
            System.out.println("\n=== PAYMENT METHODS MANAGEMENT ===");
            System.out.println("1. Add New Card");
            System.out.println("2. Remove Card");
            System.out.println("3. Modify Card");
            System.out.println("4. View Saved Cards");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addNewCard();
                    break;
                case 2:
                    removeCard();
                    break;
                case 3:
                    modifyCard();
                    break;
                case 4:
                    viewSavedCards();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addNewCard() {
        System.out.println("\n=== ADD NEW CARD ===");
        FlipfitCard card = new FlipfitCard();
        card.setCustomerId(loggedInCustomer.getCustomerId());

        System.out.print("Enter Card Number (16 digits): ");
        String cardNumber = scanner.nextLine();
        if (!cardNumber.matches("\\d{16}")) {
            System.out.println("Invalid card number format!");
            return;
        }
        card.setCardNumber(cardNumber);

        System.out.print("Enter Card Holder Name: ");
        card.setCardHolderName(scanner.nextLine());

        System.out.print("Enter Expiry Date (MM/YY): ");
        String expiryDate = scanner.nextLine();
        if (!expiryDate.matches("\\d{2}/\\d{2}")) {
            System.out.println("Invalid expiry date format!");
            return;
        }
        card.setExpiryDate(expiryDate);

        System.out.print("Enter CVV (3 digits): ");
        String cvv = scanner.nextLine();
        if (!cvv.matches("\\d{3}")) {
            System.out.println("Invalid CVV format!");
            return;
        }
        card.setCvv(cvv);

        if (customerService.addCard(card)) {
            System.out.println("Card added successfully!");
        } else {
            System.out.println("Failed to add card. Please try again.");
        }
    }

    private void removeCard() {
        List<FlipfitCard> cards = viewSavedCards();
        if (cards.isEmpty()) {
            return;
        }

        System.out.print("Enter Card ID to remove: ");
        int cardId = getIntInput();

        if (customerService.removeCard(cardId, loggedInCustomer.getCustomerId())) {
            System.out.println("Card removed successfully!");
        } else {
            System.out.println("Failed to remove card. Please try again.");
        }
    }

    private void modifyCard() {
        List<FlipfitCard> cards = viewSavedCards();
        if (cards.isEmpty()) {
            return;
        }

        System.out.print("Enter Card ID to modify: ");
        int cardId = getIntInput();

        FlipfitCard cardToModify = null;
        for (FlipfitCard card : cards) {
            if (card.getCardId() == cardId) {
                cardToModify = card;
                break;
            }
        }

        if (cardToModify == null) {
            System.out.println("Card not found!");
            return;
        }

        System.out.println("\n=== MODIFY CARD ===");
        System.out.print("Enter new Card Holder Name (press Enter to keep current): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            cardToModify.setCardHolderName(name);
        }

        System.out.print("Enter new Expiry Date (MM/YY) (press Enter to keep current): ");
        String expiry = scanner.nextLine();
        if (!expiry.isEmpty()) {
            if (!expiry.matches("\\d{2}/\\d{2}")) {
                System.out.println("Invalid expiry date format!");
                return;
            }
            cardToModify.setExpiryDate(expiry);
        }

        if (customerService.updateCard(cardToModify)) {
            System.out.println("Card updated successfully!");
        } else {
            System.out.println("Failed to update card. Please try again.");
        }
    }

    private List<FlipfitCard> viewSavedCards() {
        System.out.println("\n=== SAVED CARDS ===");
        List<FlipfitCard> cards = customerService.getCustomerCards(loggedInCustomer.getCustomerId());

        if (cards.isEmpty()) {
            System.out.println("No saved cards found.");
            return cards;
        }

        for (FlipfitCard card : cards) {
            System.out.println("Card ID: " + card.getCardId());
            System.out.println("Card Number: ****" + card.getCardNumber().substring(12));
            System.out.println("Card Holder: " + card.getCardHolderName());
            System.out.println("Expiry: " + card.getExpiryDate());
            System.out.println("------------------------");
        }
        return cards;
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
