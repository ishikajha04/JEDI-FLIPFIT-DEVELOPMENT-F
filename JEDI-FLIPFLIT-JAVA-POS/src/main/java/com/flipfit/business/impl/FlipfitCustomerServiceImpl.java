package com.flipfit.business.impl;

import com.flipfit.bean.*;
import com.flipfit.dao.*;
import com.flipfit.dao.impl.*;
import com.flipfit.business.FlipfitCustomerService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import java.util.Scanner;

public class FlipfitCustomerServiceImpl implements FlipfitCustomerService {
    private FlipfitCardDAO cardDAO;
    private FlipfitCustomerDAO customerDAO;
    private FlipfitGymCenterDAO gymCenterDAO;
    private FlipfitSlotDAO slotDAO;
    private FlipfitBookingDAO bookingDAO;
    private FlipfitWaitlistDAO waitlistDAO;


    public FlipfitCustomerServiceImpl() {
        this.cardDAO= new FlipfitCardDAOImpl();
        this.customerDAO = new FlipfitCustomerDAOImpl();
        this.gymCenterDAO = new FlipfitGymCenterDAOImpl();
        this.slotDAO = new FlipfitSlotDAOImpl();
        this.bookingDAO = new FlipfitBookingDAOImpl();
        this.waitlistDAO = new FlipfitWaitlistDAOImpl();
    }

    @Override
    public boolean registerCustomer(FlipfitCustomer customer) {
        if (customer == null || customer.getEmail() == null || customer.getPassword() == null) {
            return false;
        }

        // Check if email already exists
        if (customerDAO.getCustomerByEmail(customer.getEmail()) != null) {
            return false;
        }

        return customerDAO.addCustomer(customer);
    }

    @Override
    public FlipfitCustomer authenticateCustomer(String email, String password) {
        FlipfitCustomer customer = customerDAO.getCustomerByEmail(email);
        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        }
        return null;
    }

    @Override
    public List<FlipfitGymCenter> viewAvailableGymCenters() {
        return gymCenterDAO.getApprovedGymCenters();
    }

    @Override
    public List<FlipfitGymCenter> viewGymCentersByLocation(String location) {
        return gymCenterDAO.getGymCentersByLocation(location);
    }

    @Override
    public List<FlipfitSlot> viewAvailableSlots(int centerId, String day) {
        return slotDAO.getAvailableSlotsByCenterAndDay(centerId, day);
    }

    @Override
    public FlipfitBooking bookSlot(int customerId, int slotId, LocalDate bookingDate) {
        // Validate booking date constraints
        if (!isValidBookingDate(bookingDate)) {
            return null; // Don't add to waitlist for invalid dates
        }

        // Get slot information to validate weekday and fetch center ID
        FlipfitSlot slot = slotDAO.getSlotById(slotId);
        if (slot == null) {
            System.out.println("Slot doesn't exist.");
            return null;
        }

        // Validate that booking date matches the slot's weekday
        if (!isCorrectWeekday(bookingDate, slot.getDay())) {
            System.out.println("Booking date " + bookingDate + " doesn't match slot day " + slot.getDay() + ". Please choose a correct date.");
            return null; // Don't add to waitlist for wrong weekday
        }

        // Check for overlapping bookings on the same date
        List<FlipfitBooking> existingBookings = getCustomerBookingsForDate(customerId, bookingDate);
        if (!existingBookings.isEmpty()) {
            if (!handleOverlappingBookings(existingBookings, slot)) {
                return null; // User explicitly cancelled, don't add to waitlist
            }
        }

        // Check if slot is full, if so add to waitlist
        if (slot.getAvailableSeats() <= 0) {
            System.out.println("Slot is full. You have been added to the waitlist.");
            return addToWaitlist(customerId, slotId, bookingDate);
        }

        if (!slot.isAvailable()) {
            System.out.println("Slot not available.");
            return null;
        }

        List<FlipfitCard> cards = cardDAO.getCustomerCards(customerId);
        if (cards.isEmpty()) {
            System.out.println("No payment methods found. Please add a card first.");
            return null;
        }

        // Display available cards
        System.out.println("\nSelect a card for payment:");
        for (FlipfitCard card : cards) {
            System.out.println(card.getCardId() + ". Card ending in " +
                    card.getCardNumber().substring(card.getCardNumber().length() - 4));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter card ID to use for payment: ");
        int selectedCardId = scanner.nextInt();

        // Validate selected card
        boolean cardFound = false;
        for (FlipfitCard card : cards) {
            if (card.getCardId() == selectedCardId) {
                cardFound = true;
                break;
            }
        }

        if (!cardFound) {
            System.out.println("Invalid card selected.");
            return null;
        }

        FlipfitCustomer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            return null;
        }

        // Create booking with payment using slot's dynamic price
        FlipfitBooking booking = new FlipfitBooking();
        booking.setCustomerId(customerId);
        booking.setSlotId(slotId);
        booking.setCenterId(slot.getCenterId()); // Set the center ID from slot
        booking.setBookingDate(bookingDate);
        booking.setAmount(slot.getPrice()); // Use slot's dynamic price
        booking.setStatus(FlipfitBooking.BookingStatus.CONFIRMED); // Set initial status
        booking.setBookingTime(LocalDateTime.now());

        System.out.println("\nConfirm payment of ₹" + slot.getPrice());
        System.out.print("Enter 1 to confirm payment, 0 to cancel: ");
        int confirm = scanner.nextInt();

        if (confirm != 1) {
            System.out.println("Payment cancelled.");
            return null;
        }

        if (bookingDAO.addBooking(booking)) {
            // Update slot availability
            int newAvailableSeats = slot.getAvailableSeats() - 1;
            slotDAO.updateSlotAvailability(slotId, newAvailableSeats);
            System.out.println("Payment successful!");
            return booking;
        } else {
            System.out.println("Booking failed. Please try again.");
            return null;
        }
    }

    private boolean isValidBookingDate(LocalDate bookingDate) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate maxBookingDate = today.plusWeeks(1);

        if (bookingDate.isBefore(tomorrow)) {
            System.out.println("Cannot book for today or past dates. Please select from tomorrow onwards.");
            return false;
        }

        if (bookingDate.isAfter(maxBookingDate)) {
            System.out.println("Cannot book more than 1 week in advance. Maximum booking date: " + maxBookingDate);
            return false;
        }

        return true;
    }

    private boolean isCorrectWeekday(LocalDate bookingDate, String slotDay) {
        String bookingDayOfWeek = bookingDate.getDayOfWeek().name();

        // Convert to proper case for comparison
        String bookingDay = bookingDayOfWeek.charAt(0) + bookingDayOfWeek.substring(1).toLowerCase();

        return bookingDay.equalsIgnoreCase(slotDay);
    }

    private List<FlipfitBooking> getCustomerBookingsForDate(int customerId, LocalDate date) {
        List<FlipfitBooking> allBookings = bookingDAO.getBookingsByCustomerId(customerId);
        return allBookings.stream()
                .filter(booking -> booking.getBookingDate().equals(date) &&
                        (booking.getStatus() == FlipfitBooking.BookingStatus.CONFIRMED ||
                         booking.getStatus() == FlipfitBooking.BookingStatus.PENDING))
                .collect(Collectors.toList());
    }

    private boolean handleOverlappingBookings(List<FlipfitBooking> existingBookings, FlipfitSlot newSlot) {
        System.out.println("\nWarning: You already have bookings on this date:");

        for (FlipfitBooking booking : existingBookings) {
            FlipfitSlot existingSlot = slotDAO.getSlotById(booking.getSlotId());
            if (existingSlot != null) {
                System.out.println("Booking ID: " + booking.getBookingId() +
                                 " | Time: " + existingSlot.getStartTime() + " - " + existingSlot.getEndTime() +
                                 " | Status: " + booking.getStatus());

                // Check for time overlap
                if (slotsOverlap(existingSlot, newSlot)) {
                    System.out.println("Time slots overlap detected!");
                    System.out.println("Existing slot: " + existingSlot.getStartTime() + " - " + existingSlot.getEndTime());
                    System.out.println("New slot: " + newSlot.getStartTime() + " - " + newSlot.getEndTime());

                    Scanner scanner = new Scanner(System.in);
                    System.out.println("\nChoose an option:");
                    System.out.println("1. Cancel existing overlapping bookings and proceed with new booking");
                    System.out.println("2. Keep both bookings (allow overlapping)");
                    System.out.println("3. Cancel new booking");
                    System.out.print("Enter your choice (1-3): ");

                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            // Cancel overlapping bookings
                            for (FlipfitBooking overlappingBooking : existingBookings) {
                                FlipfitSlot overlappingSlot = slotDAO.getSlotById(overlappingBooking.getSlotId());
                                if (overlappingSlot != null && slotsOverlap(overlappingSlot, newSlot)) {
                                    cancelBooking(overlappingBooking.getBookingId(), overlappingBooking.getCustomerId());
                                    System.out.println("Cancelled overlapping booking ID: " + overlappingBooking.getBookingId());
                                }
                            }
                            return true;
                        case 2:
                            System.out.println("Proceeding with overlapping bookings...");
                            return true;
                        case 3:
                            System.out.println("New booking cancelled.");
                            return false;
                        default:
                            System.out.println("Invalid choice. Cancelling new booking.");
                            return false;
                    }
                }
            }
        }

        // No overlaps found, but existing bookings on same date
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nYou have existing bookings on this date but no time conflicts.");
        System.out.print("Do you want to proceed with the new booking? (1 for Yes, 0 for No): ");
        int proceed = scanner.nextInt();

        return proceed == 1;
    }

    private boolean slotsOverlap(FlipfitSlot slot1, FlipfitSlot slot2) {
        return slot1.getStartTime().isBefore(slot2.getEndTime()) &&
               slot2.getStartTime().isBefore(slot1.getEndTime());
    }

    @Override
    public boolean cancelBooking(int bookingId, int customerId) {
        FlipfitBooking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null || booking.getCustomerId() != customerId) {
            return false;
        }

        if (booking.getStatus() == FlipfitBooking.BookingStatus.CANCELLED) {
            return false;
        }

        // Cancel the original booking
        booking.setStatus(FlipfitBooking.BookingStatus.CANCELLED);
        bookingDAO.updateBooking(booking);

        // Increase slot availability and check for waitlist
        FlipfitSlot slot = slotDAO.getSlotById(booking.getSlotId());
        if (slot != null) {
            slot.setAvailableSeats(slot.getAvailableSeats() + 1);
            slotDAO.updateSlot(slot);

            // Promote customer from waitlist if slot is now available
            FlipfitWaitlist waitlist = waitlistDAO.getWaitlistBySlotId(slot.getSlotId());
            if (waitlist != null && !waitlist.getCustomerIds().isEmpty()) {
                Integer nextCustomerId = waitlist.getNextCustomer();
                if (nextCustomerId != null) {
                    System.out.println("Promoting customer " + nextCustomerId + " from waitlist for slot " + slot.getSlotId());

                    // Find the existing waitlisted booking for this customer
                    FlipfitBooking waitlistBooking = bookingDAO.getBookingsByCustomerId(nextCustomerId)
                            .stream()
                            .filter(b -> b.getSlotId() == slot.getSlotId() && b.getStatus() == FlipfitBooking.BookingStatus.WAITLISTED)
                            .findFirst()
                            .orElse(null);

                    if (waitlistBooking != null) {
                        // Change the status of the existing booking to CONFIRMED
                        waitlistBooking.setStatus(FlipfitBooking.BookingStatus.CONFIRMED);
                        bookingDAO.updateBooking(waitlistBooking);

                        // Update slot availability again for the new confirmed booking
                        slot.setAvailableSeats(slot.getAvailableSeats() - 1);
                        slotDAO.updateSlot(slot);

                        waitlistDAO.updateWaitlist(waitlist);
                    }
                }
            }
        }
        return true;
    }




//    @Override
//    public int addToWaitlist(int customerId, int slotId) {
//        FlipfitWaitlist waitlist = waitlistDAO.getWaitlistBySlotId(slotId);
//        if (waitlist == null) {
//            waitlist = new FlipfitWaitlist();
//            waitlist.setSlotId(slotId);
//            waitlistDAO.addWaitlist(waitlist);
//        }
//
//        if (waitlist.getCustomerIds().contains(customerId)) {
//            return waitlist.getCustomerIds().size();
//        }
//
//        waitlist.getCustomerIds().add(customerId);
//        waitlistDAO.updateWaitlist(waitlist);
//        return waitlist.getCustomerIds().size();
//    }

    @Override
    public FlipfitBooking addToWaitlist(int customerId, int slotId, LocalDate bookingDate) {
        FlipfitWaitlist waitlist = waitlistDAO.getWaitlistBySlotId(slotId);
        FlipfitSlot slot = slotDAO.getSlotById(slotId);

        if (slot == null) {
            return null;
        }

        if (waitlist == null) {
            waitlist = new FlipfitWaitlist();
            waitlist.setSlotId(slotId);
            waitlistDAO.addWaitlist(waitlist);
        }

        if (waitlist.getCustomerIds().contains(customerId)) {
            System.out.println("You are already on the waitlist for this slot.");
            return null;
        }

        waitlist.getCustomerIds().add(customerId);
        waitlistDAO.updateWaitlist(waitlist);

        FlipfitBooking waitlistBooking = new FlipfitBooking(0, customerId, slotId, slot.getCenterId(), bookingDate, slot.getPrice());
        waitlistBooking.setStatus(FlipfitBooking.BookingStatus.WAITLISTED);
        bookingDAO.addBooking(waitlistBooking);

        return waitlistBooking;
    }

    @Override
    public List<FlipfitBooking> viewBookings(int customerId) {
        return bookingDAO.getBookingsByCustomerId(customerId);
    }

    @Override
    public List<FlipfitBooking> viewBookingsByDate(int customerId, LocalDate date) {
        return bookingDAO.getBookingsByCustomerId(customerId).stream()
                .filter(booking -> booking.getBookingDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public FlipfitCustomer getCustomerProfile(int customerId) {
        return customerDAO.getCustomerById(customerId);
    }

    @Override
    public boolean updateCustomerProfile(FlipfitCustomer customer) {
        return customerDAO.updateCustomer(customer);
    }

    @Override
    public boolean addCard(FlipfitCard card) {
        return cardDAO.addCard(card);
    }

    @Override
    public boolean removeCard(int cardId, int customerId) {
        return cardDAO.removeCard(cardId, customerId);
    }

    @Override
    public boolean updateCard(FlipfitCard card) {
        return cardDAO.updateCard(card);
    }

    @Override
    public List<FlipfitCard> getCustomerCards(int customerId) {
        return cardDAO.getCustomerCards(customerId);
    }
}

