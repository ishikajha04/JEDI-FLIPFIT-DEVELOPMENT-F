package com.flipfit.service.impl;

import com.flipfit.bean.*;
import com.flipfit.dao.*;
import com.flipfit.dao.impl.*;
import com.flipfit.service.FlipfitCustomerService;
import java.time.LocalDate;
import java.util.List;

public class FlipfitCustomerServiceImpl implements FlipfitCustomerService {
    private FlipfitCustomerDAO customerDAO;
    private FlipfitGymCenterDAO gymCenterDAO;
    private FlipfitSlotDAO slotDAO;
    private FlipfitBookingDAO bookingDAO;

    public FlipfitCustomerServiceImpl() {
        this.customerDAO = new FlipfitCustomerDAOImpl();
        this.gymCenterDAO = new FlipfitGymCenterDAOImpl();
        this.slotDAO = new FlipfitSlotDAOImpl();
        this.bookingDAO = new FlipfitBookingDAOImpl();
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

    @Override
    public boolean cancelBooking(int bookingId, int customerId) {
        FlipfitBooking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null || booking.getCustomerId() != customerId) {
            return false;
        }

        if (booking.getStatus() == FlipfitBooking.BookingStatus.CANCELLED) {
            return false;
        }

        // Cancel booking
        booking.setStatus(FlipfitBooking.BookingStatus.CANCELLED);
        bookingDAO.updateBooking(booking);

        // Increase slot availability
        FlipfitSlot slot = slotDAO.getSlotById(booking.getSlotId());
        if (slot != null) {
            slot.setAvailableSeats(slot.getAvailableSeats() + 1);
            slotDAO.updateSlot(slot);
        }

        return true;
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
