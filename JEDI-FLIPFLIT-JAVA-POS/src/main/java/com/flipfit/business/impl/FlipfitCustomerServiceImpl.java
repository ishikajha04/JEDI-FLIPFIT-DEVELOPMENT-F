package com.flipfit.business.impl;

import com.flipfit.bean.*;
import com.flipfit.business.FlipfitCustomerService;
import com.flipfit.dao.*;
import com.flipfit.dao.impl.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class FlipfitCustomerServiceImpl implements FlipfitCustomerService {
    private final FlipfitCardDAO cardDAO = new FlipfitCardDAOImpl();
    private final FlipfitCustomerDAO customerDAO = new FlipfitCustomerDAOImpl();
    private final FlipfitGymCenterDAO gymCenterDAO = new FlipfitGymCenterDAOImpl();
    private final FlipfitSlotDAO slotDAO = new FlipfitSlotDAOImpl();
    private final FlipfitBookingDAO bookingDAO = new FlipfitBookingDAOImpl();
    private static final double SLOT_BOOKING_AMOUNT = 500.0;

    @Override
    public boolean registerCustomer(FlipfitCustomer customer) {
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
        if (slot == null) {
            System.out.println("Invalid slot selected.");
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
        if (booking != null && booking.getCustomerId() == customerId) {
            return bookingDAO.cancelBooking(bookingId);
        }
        return false;
    }

    @Override
    public List<FlipfitBooking> viewBookings(int customerId) {
        return bookingDAO.getBookingsByCustomerId(customerId);
    }

    @Override
    public List<FlipfitBooking> viewBookingsByDate(int customerId, LocalDate date) {
        return bookingDAO.getBookingsByCustomerId(customerId).stream()
            .filter(booking -> booking.getBookingDate().equals(date))
            .toList();
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
