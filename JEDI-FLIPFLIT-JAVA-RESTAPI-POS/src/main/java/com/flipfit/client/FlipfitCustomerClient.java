package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.FlipfitCustomerService;
import com.flipfit.business.impl.FlipfitCustomerServiceImpl;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Client class for Flipfit Customer operations, providing menu and customer actions
 * @author Khushi, Kritika
 * @description This class provides a command-line interface for customers to interact with the Flipfit system,
 * including options for registration, login, booking slots, and managing profile
 */
public class FlipfitCustomerClient {
    private FlipfitCustomerService customerService;
    private Scanner scanner;
    private FlipfitCustomer loggedInCustomer;

    /**
     * Default constructor initializing customer service and scanner
     * @method FlipfitCustomerClient
     * @description Initializes the customer client by creating an instance of the customer service implementation
     * and setting up the scanner for user input
     */
    public FlipfitCustomerClient() {
        this.customerService = new FlipfitCustomerServiceImpl();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the customer menu and handles user input for customer actions
     * @method displayCustomerMenu
     * @description Presents different menu options based on whether a customer is logged in,
     * and processes the user's selections accordingly
     * @exception java.lang.NumberFormatException Handled internally if user enters non-numeric input
     */
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
                        System.out.println("Invalid option. Please choose 1-3.");
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
                System.out.println("9. Manage Payment Methods");
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

    /**
     * Handles customer registration process
     * @method registerCustomer
     * @description Collects customer information, creates a new customer account,
     * and registers the customer in the system
     * @exception com.flipfit.exception.RegistrationNotDoneException If registration fails
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     */
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

    /**
     * Handles customer login process
     * @method loginCustomer
     * @description Authenticates a customer using their email and password credentials
     * @exception com.flipfit.exception.UserNotFoundException If the customer with given email doesn't exist
     */
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

    /**
     * Displays available gym centers
     * @method viewAvailableGymCenters
     * @description Retrieves and displays a list of gym centers that are available for booking
     */
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
                                 " | Address: " + center.getAddress());
            }
        }
    }

    /**
     * Searches gym centers by location
     * @method searchGymCentersByLocation
     * @description Allows the customer to search for gym centers based on a specific location
     */
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

    /**
     * Displays available slots for a specific gym center and day
     * @method viewAvailableSlots
     * @description Shows the time slots available for booking in a selected gym center on a specific day
     */
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

    /**
     * Books a slot for the customer
     * @method bookSlot
     * @description Allows the customer to book a selected slot for a specific date
     */
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
                System.out.println("Booking was not completed.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    /**
     * Displays the customer's bookings
     * @method viewMyBookings
     * @description Retrieves and shows the list of bookings made by the customer
     */
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

    /**
     * Cancels a booking made by the customer
     * @method cancelBooking
     * @description Allows the customer to cancel a specific booking using its ID
     */
    private void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = getIntInput();

        if (customerService.cancelBooking(bookingId, loggedInCustomer.getCustomerId())) {
            System.out.println("Booking cancelled successfully!");
        } else {
            System.out.println("Failed to cancel booking. Please check the booking ID.");
        }
    }

    /**
     * Displays the customer's profile information
     * @method viewProfile
     * @description Shows the details of the customer's profile including ID, name, email, and phone number
     */
    private void viewProfile() {
        System.out.println("\n=== MY PROFILE ===");
        System.out.println("Customer ID: " + loggedInCustomer.getCustomerId());
        System.out.println("Name: " + loggedInCustomer.getName());
        System.out.println("Email: " + loggedInCustomer.getEmail());
        System.out.println("Phone: " + loggedInCustomer.getPhoneNumber());
    }

    /**
     * Updates the customer's profile information
     * @method updateProfile
     * @description Allows the customer to update their profile details like name and phone number
     */
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

    /**
     * Manages the customer's payment methods
     * @method managePaymentMethods
     * @description Provides options to the customer for adding, removing, or modifying saved payment cards
     */
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

    /**
     * Adds a new card for the customer
     * @method addNewCard
     * @description Collects card details from the customer and saves the new card information
     */
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

    /**
     * Removes a saved card for the customer
     * @method removeCard
     * @description Deletes the selected card from the customer's saved payment methods
     */
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

    /**
     * Modifies an existing saved card for the customer
     * @method modifyCard
     * @description Updates the details of a selected card, such as holder name and expiry date
     */
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

    /**
     * Views the customer's saved cards
     * @method viewSavedCards
     * @description Displays a list of cards saved by the customer for payments, with options to manage them
     * @return List of customer's saved cards
     */
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

    /**
     * Safely parses user input into an integer
     * @method getIntInput
     * @return Integer value of user input, or -1 if input is not a valid integer
     * @exception java.lang.NumberFormatException Handled internally if user enters non-numeric input
     * @description Reads user input from the console and attempts to parse it as an integer,
     * returning -1 if the input cannot be parsed
     */
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
