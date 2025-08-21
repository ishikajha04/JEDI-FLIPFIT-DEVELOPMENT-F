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
    private static final double SLOT_BOOKING_AMOUNT = 500.0;


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
        List<FlipfitCard> cards = cardDAO.getCustomerCards(customerId);
        if (cards.isEmpty()) {
            System.out.println("No payment methods found. Please add a card first.");
            return null;
        }

        // Get slot information to fetch center ID
        FlipfitSlot slot = slotDAO.getSlotById(slotId);
        if (slot == null || !slot.isAvailable()) {
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

        // Create booking with payment
        FlipfitBooking booking = new FlipfitBooking();
        booking.setCustomerId(customerId);
        booking.setSlotId(slotId);
        booking.setCenterId(slot.getCenterId()); // Set the center ID from slot
        booking.setBookingDate(bookingDate);
        booking.setAmount(SLOT_BOOKING_AMOUNT);
        booking.setStatus(FlipfitBooking.BookingStatus.CONFIRMED); // Set initial status
        booking.setBookingTime(LocalDateTime.now());

        System.out.println("\nConfirm payment of ₹" + SLOT_BOOKING_AMOUNT);
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
                .collect(java.util.stream.Collectors.toList());
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
