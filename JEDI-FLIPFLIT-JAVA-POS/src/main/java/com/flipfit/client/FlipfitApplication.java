package com.flipfit.client;

import java.util.Scanner;

public class FlipfitApplication {
    private static Scanner scanner = new Scanner(System.in);

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

    private static void displayMainMenu() {
        System.out.println("\n=== FLIPFIT MAIN MENU ===");
        System.out.println("Please select your role:");
        System.out.println("1. Customer");
        System.out.println("2. Gym Owner");
        System.out.println("3. Admin");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");
    }

    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
