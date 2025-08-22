package com.flipfit.client;

import java.util.Scanner;

/**
 * Entry point for the Flipfit Gym System application, providing the main menu and role selection
 * @author Sukhmani
 * @description This class serves as the main entry point for the Flipfit Gym System application,
 * allowing users to select their role and navigate to the appropriate interface
 */
public class FlipfitApplication {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Main method to start the Flipfit Gym System application and display the main menu
     * @method main
     * @param args Command-line arguments
     * @description Initializes the application, displays the welcome message, and handles
     * user role selection to direct them to the appropriate client interface
     * @exception java.lang.NumberFormatException If user enters non-numeric input for menu selection
     */
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("          WELCOME TO FLIPFIT GYM SYSTEM        ");
        System.out.println("=================================================");

        while (true) {
            displayMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    FlipfitCustomerClient customerClient = new FlipfitCustomerClient();
                    customerClient.displayCustomerMenu();
                    break;
                case 2:
                    FlipfitGymOwnerClient gymOwnerClient = new FlipfitGymOwnerClient();
                    gymOwnerClient.displayGymOwnerMenu();
                    break;
                case 3:
                    FlipfitAdminClient adminClient = new FlipfitAdminClient();
                    adminClient.displayAdminMenu();
                    break;
                case 4:
                    System.out.println("Thank you for using Flipfit Gym System!");
                    System.out.println("Have a great day!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-4.");
            }
        }
    }

    /**
     * Displays the main menu for role selection in the Flipfit Gym System
     * @method displayMainMenu
     * @description Presents the available role options to the user, including Customer, Gym Owner, Admin, and Exit
     */
    private static void displayMainMenu() {
        System.out.println("\n=== FLIPFIT MAIN MENU ===");
        System.out.println("Please select your role:");
        System.out.println("1. Customer");
        System.out.println("2. Gym Owner");
        System.out.println("3. Admin");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");
    }

    /**
     * Safely parses user input into an integer
     * @method getIntInput
     * @return Integer value of user input, or -1 if input is not a valid integer
     * @exception java.lang.NumberFormatException Handled internally if user enters non-numeric input
     * @description Reads user input from the console and attempts to parse it as an integer,
     * returning -1 if the input cannot be parsed
     */
    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
