package com.flipfit.client;

import java.util.Scanner;

public class FlipfitApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("WELCOME TO THE FLIPFIT APPLICATION");
            System.out.println("\t1. Login");
            System.out.println("\t2. Registration of the FlipFit GymCustomer");
            System.out.println("\t3. Registration of the FlipFit GymOwner");
            System.out.println("\t4. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleLogin(scanner);
                    break;
                case 2:
                    System.out.println("--- Customer Registration ---");
                    // Logic for customer registration goes here
                    break;
                case 3:
                    System.out.println("--- Gym Owner Registration ---");
                    // Logic for gym owner registration goes here
                    break;
                case 4:
                    System.out.println("Exiting FlipFit application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
            System.out.println();

        } while (choice != 4);

        scanner.close();
    }

    private static void handleLogin(Scanner scanner) {
        System.out.println("--- Login ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.println("Select your Role:");
        System.out.println("\t1. GymCustomer");
        System.out.println("\t2. GymOwner");
        System.out.println("\t3. GymAdmin");
        System.out.print("Enter role choice (1-3): ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        switch (roleChoice) {
            case 1:
                FlipfitCustomerClient customerClient = new FlipfitCustomerClient();
                customerClient.displayGymCustomerMenu(scanner);
                break;
            case 2:
                FlipfitGymOwnerClient gymOwnerClient = new FlipfitGymOwnerClient();
                break;
            case 3:
                FlipfitAdminClient adminClient = new FlipfitAdminClient();
                break;
            default:
                System.out.println("Invalid role choice. Please try again.");
        }
    }
}
