package com.flipfit.client;

import java.util.Scanner;

public class FlipfitCustomerClient {

    public void displayGymCustomerMenu(Scanner scanner) {
        int choice;

        do {
            System.out.println("      *** GYM CUSTOMER MENU ***");
            System.out.println();
            System.out.println("  1. View Gym Locations");
            System.out.println("  2. View Available Slots by Gym");
            System.out.println("  3. Book a New Slot");
            System.out.println("  4. View My Bookings");
            System.out.println("  5. Modify a Booked Slot");
            System.out.println("  6. Cancel a Booked Slot");
            System.out.println("  7. View My Waitlisted Slots");
            System.out.println("  8. View My Membership Plan");
            System.out.println("  9. View Payment History");
            System.out.println();
            System.out.println("  10. Logout");
            System.out.println("-----------------------------------------");
            System.out.print("\nPlease enter your choice (1-10): ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("You have selected: View Gym Locations");
                    break;
                case 2:
                    System.out.println("You have selected: View Available Slots by Gym");
                    break;
                case 3:
                    System.out.println("You have selected: Book a New Slot");
                    break;
                case 4:
                    System.out.println("You have selected: View My Bookings");
                    break;
                case 5:
                    System.out.println("You have selected: Modify a Booked Slot");
                    break;
                case 6:
                    System.out.println("You have selected: Cancel a Booked Slot");
                    break;
                case 7:
                    System.out.println("You have selected: View My Waitlisted Slots");
                    break;
                case 8:
                    System.out.println("You have selected: View My Membership Plan");
                    break;
                case 9:
                    System.out.println("You have selected: View Payment History");
                    break;
                case 10:
                    System.out.println("Logging out from Gym Customer Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 10.");
            }
            System.out.println();
        } while (choice != 10);
    }
}