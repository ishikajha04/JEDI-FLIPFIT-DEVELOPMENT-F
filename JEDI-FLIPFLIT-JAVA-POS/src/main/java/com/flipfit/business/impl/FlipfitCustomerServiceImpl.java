package com.flipfit.business.impl;

import com.flipfit.bean.*;
import com.flipfit.dao.*;
import com.flipfit.dao.impl.*;
import com.flipfit.business.FlipfitCustomerService;
import java.time.LocalDate;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;


public class FlipfitCustomerServiceImpl implements FlipfitCustomerService {
    private FlipfitCustomerDAO customerDAO;
    private FlipfitGymCenterDAO gymCenterDAO;
    private FlipfitSlotDAO slotDAO;
    private FlipfitBookingDAO bookingDAO;
    private FlipfitWaitlistDAO waitlistDAO;


    public FlipfitCustomerServiceImpl() {
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
        FlipfitSlot slot = slotDAO.getSlotById(slotId);
        if (slot == null || !slot.isAvailable()) {
            return null;
        }

        FlipfitCustomer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            return null;
        }

        // Create booking
        FlipfitBooking booking = new FlipfitBooking(0, customerId, slotId, slot.getCenterId(), bookingDate, slot.getPrice());

        if (bookingDAO.addBooking(booking)) {
            // Update slot availability
            slot.setAvailableSeats(slot.getAvailableSeats() - 1);
            slotDAO.updateSlot(slot);
            return booking;
        }

        return null;
    }

//    @Override
//    public boolean cancelBooking(int bookingId, int customerId) {
//        FlipfitBooking booking = bookingDAO.getBookingById(bookingId);
//        if (booking == null || booking.getCustomerId() != customerId) {
//            return false;
//        }
//
//        if (booking.getStatus() == FlipfitBooking.BookingStatus.CANCELLED) {
//            return false;
//        }
//
//        // Cancel booking
//        booking.setStatus(FlipfitBooking.BookingStatus.CANCELLED);
//        bookingDAO.updateBooking(booking);
//
//        // Increase slot availability
//        FlipfitSlot slot = slotDAO.getSlotById(booking.getSlotId());
//        if (slot != null) {
//            slot.setAvailableSeats(slot.getAvailableSeats() + 1);
//            slotDAO.updateSlot(slot);
//
//            FlipfitWaitlist waitlist = waitlistDAO.getWaitlistBySlotId(slot.getSlotId());
//            if (waitlist != null && !waitlist.getCustomerIds().isEmpty()) {
//                Integer nextCustomerId = waitlist.getNextCustomer();
//                if (nextCustomerId != null) {
//                    System.out.println("Promoting customer " + nextCustomerId + " from waitlist for slot " + slot.getSlotId());
//                    FlipfitBooking newBooking = new FlipfitBooking(0, nextCustomerId, slot.getSlotId(), slot.getCenterId(), booking.getBookingDate(), slot.getPrice());
//                    bookingDAO.addBooking(newBooking);
//                    // Update slot availability again for the new booking
//                    slot.setAvailableSeats(slot.getAvailableSeats() - 1);
//                    slotDAO.updateSlot(slot);
//                    waitlistDAO.updateWaitlist(waitlist);
//                }
//            }
//        }
//
//        return true;
//    }



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
}
