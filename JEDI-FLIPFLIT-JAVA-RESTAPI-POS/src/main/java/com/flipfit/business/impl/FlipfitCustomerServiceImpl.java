package com.flipfit.business.impl;

import com.flipfit.bean.*;
import com.flipfit.dao.*;
import com.flipfit.dao.impl.*;
import com.flipfit.exception.*;
import com.flipfit.business.FlipfitCustomerService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * @author Sukhmani
 * @description Implementation of FlipfitCustomerService interface that handles all customer operations
 */
public class FlipfitCustomerServiceImpl implements FlipfitCustomerService {
    private final FlipfitCardDAO cardDAO;
    private final FlipfitCustomerDAO customerDAO;
    private final FlipfitGymCenterDAO gymCenterDAO;
    private final FlipfitSlotDAO slotDAO;
    private final FlipfitBookingDAO bookingDAO;
    private final FlipfitWaitlistDAO waitlistDAO;

    /**
     * @method FlipfitCustomerServiceImpl
     * @description Constructor that initializes all required DAO instances
     */
    public FlipfitCustomerServiceImpl() {
        this.cardDAO = new FlipfitCardDAOImpl();
        this.customerDAO = new FlipfitCustomerDAOImpl();
        this.gymCenterDAO = new FlipfitGymCenterDAOImpl();
        this.slotDAO = new FlipfitSlotDAOImpl();
        this.bookingDAO = new FlipfitBookingDAOImpl();
        this.waitlistDAO = new FlipfitWaitlistDAOImpl();
    }

    @Override
    public boolean registerCustomer(FlipfitCustomer customer) {
        try {
            if (customer == null || customer.getEmail() == null || customer.getPassword() == null) {
                throw new RegistrationNotDoneException("Invalid customer data provided");
            }

            // Check if email already exists
            if (customerDAO.getCustomerByEmail(customer.getEmail()) != null) {
                throw new RegistrationNotDoneException("A customer with email " + customer.getEmail() + " already exists");
            }

            boolean result = customerDAO.addCustomer(customer);
            if (!result) {
                throw new RegistrationNotDoneException("Failed to register customer in the database");
            }
            return result;
        } catch (RegistrationNotDoneException | DatabaseException e) {
            throw new RuntimeException(ExceptionHandler.handleException(e), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during customer registration: " + e.getMessage(), e);
        }
    }

    @Override
    public FlipfitCustomer authenticateCustomer(String email, String password) {
        try {
            FlipfitCustomer customer = customerDAO.getCustomerByEmail(email);
            if (customer == null) {
                throw new UserNotFoundException("Customer with email " + email + " not found");
            }

            if (!customer.getPassword().equals(password)) {
                throw new UserNotFoundException("Invalid password for customer with email " + email);
            }

            return customer;
        } catch (UserNotFoundException | DatabaseException e) {
            throw new RuntimeException(ExceptionHandler.handleException(e), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during authentication: " + e.getMessage(), e);
        }
    }

    @Override
    public List<FlipfitGymCenter> viewAvailableGymCenters() {
        try {
            return gymCenterDAO.getApprovedGymCenters();
        } catch (DatabaseException e) {
            throw new RuntimeException("Database error while retrieving gym centers: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while retrieving gym centers: " + e.getMessage(), e);
        }
    }

    @Override
    public List<FlipfitGymCenter> viewGymCentersByLocation(String location) {
        try {
            if (location == null || location.trim().isEmpty()) {
                throw new IllegalArgumentException("Location cannot be empty");
            }

            return gymCenterDAO.getGymCentersByLocation(location);
        } catch (DatabaseException e) {
            throw new RuntimeException("Database error while retrieving gym centers by location: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while retrieving gym centers by location: " + e.getMessage(), e);
        }
    }

    @Override
    public List<FlipfitSlot> viewAvailableSlots(int centerId, String day) {
        try {
            if (centerId <= 0) {
                throw new IllegalArgumentException("Invalid center ID");
            }

            if (day == null || day.trim().isEmpty()) {
                throw new IllegalArgumentException("Day cannot be empty");
            }

            return slotDAO.getAvailableSlotsByCenterAndDay(centerId, day);
        } catch (DatabaseException e) {
            throw new RuntimeException("Database error while retrieving available slots: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while retrieving available slots: " + e.getMessage(), e);
        }
    }

    @Override
    public FlipfitBooking bookSlot(int customerId, int slotId, LocalDate bookingDate, int cardId) {
        try {
            // Validate booking date constraints
            if (!isValidBookingDate(bookingDate)) {
                throw new BookingNotConfirmedException("Invalid booking date. Please choose a date between tomorrow and 1 week from now.");
            }

            // Get slot information to validate weekday and fetch center ID
            FlipfitSlot slot = slotDAO.getSlotById(slotId);
            if (slot == null) {
                throw new SlotNotFoundException(String.valueOf(slotId), "N/A");
            }

            // Validate that booking date matches the slot's weekday
            if (!isCorrectWeekday(bookingDate, slot.getDay())) {
                throw new SlotNotFoundException("Booking date " + bookingDate + " doesn't match slot day " + slot.getDay() + ". Please choose a correct date.");
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
                return addToWaitlist(customerId, slotId, bookingDate);
            }

            if (!slot.isAvailable()) {
                throw new SlotNotFoundException("Slot is not available for booking");
            }

            List<FlipfitCard> cards = cardDAO.getCustomerCards(customerId);
            if (cards.isEmpty()) {
                throw new PaymentNotDoneException("No payment methods found. Please add a card first.");
            }

            // Validate selected card
            boolean cardFound = false;
            for (FlipfitCard card : cards) {
                if (card.getCardId() == cardId) {
                    cardFound = true;
                    break;
                }
            }

            if (!cardFound) {
                throw new PaymentNotDoneException("Invalid card selected.");
            }

            FlipfitCustomer customer = customerDAO.getCustomerById(customerId);
            if (customer == null) {
                throw new UserNotFoundException(String.valueOf(customerId), "Customer");
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

            if (bookingDAO.addBooking(booking)) {
                // Update slot availability
                int newAvailableSeats = slot.getAvailableSeats() - 1;
                slotDAO.updateSlotAvailability(slotId, newAvailableSeats);
                return booking;
            } else {
                throw new BookingNotConfirmedException("Booking could not be confirmed. Please try again.");
            }
        } catch (SlotNotFoundException | UserNotFoundException | PaymentNotDoneException | BookingNotConfirmedException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            throw new RuntimeException(errorMessage, e);
        } catch (DatabaseException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            throw new RuntimeException(errorMessage, e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during slot booking: " + e.getMessage(), e);
        }
    }

    private boolean isValidBookingDate(LocalDate bookingDate) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate maxBookingDate = today.plusWeeks(1);

        if (bookingDate.isBefore(tomorrow)) {
            return false;
        }

        if (bookingDate.isAfter(maxBookingDate)) {
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
        // Check for time overlap with existing bookings
        for (FlipfitBooking booking : existingBookings) {
            FlipfitSlot existingSlot = slotDAO.getSlotById(booking.getSlotId());
            if (existingSlot != null && slotsOverlap(existingSlot, newSlot)) {
                // Time slots overlap - throw exception to let API handle it
                throw new BookingNotConfirmedException(
                    "Time slot conflict detected. Existing booking from " +
                    existingSlot.getStartTime() + " to " + existingSlot.getEndTime() +
                    " overlaps with new slot from " + newSlot.getStartTime() + " to " + newSlot.getEndTime()
                );
            }
        }

        // No overlaps found, proceed with booking
        return true;
    }

    private boolean slotsOverlap(FlipfitSlot slot1, FlipfitSlot slot2) {
        return slot1.getStartTime().isBefore(slot2.getEndTime()) &&
               slot2.getStartTime().isBefore(slot1.getEndTime());
    }

    @Override
    public boolean cancelBooking(int bookingId, int customerId) {
        try {
            FlipfitBooking booking = bookingDAO.getBookingById(bookingId);
            if (booking == null) {
                throw new BookingNotConfirmedException("Booking with ID " + bookingId + " not found");
            }

            if (booking.getCustomerId() != customerId) {
                throw new UserNotFoundException("Customer " + customerId + " is not authorized to cancel this booking");
            }

            if (booking.getStatus() == FlipfitBooking.BookingStatus.CANCELLED) {
                throw new BookingNotConfirmedException("Booking is already cancelled");
            }

            // Cancel the original booking
            booking.setStatus(FlipfitBooking.BookingStatus.CANCELLED);
            boolean updateSuccess = bookingDAO.updateBooking(booking);

            if (!updateSuccess) {
                throw new DatabaseException("Failed to update booking status to cancelled");
            }

            // Increase slot availability and check for waitlist
            FlipfitSlot slot = slotDAO.getSlotById(booking.getSlotId());
            if (slot == null) {
                throw new SlotNotFoundException(String.valueOf(booking.getSlotId()), String.valueOf(booking.getCenterId()));
            }

            slot.setAvailableSeats(slot.getAvailableSeats() + 1);
            slotDAO.updateSlot(slot);

            // Promote customer from waitlist if slot is now available
            FlipfitWaitlist waitlist = waitlistDAO.getWaitlistBySlotId(slot.getSlotId());
            if (waitlist != null && !waitlist.getCustomerIds().isEmpty()) {
                Integer nextCustomerId = waitlist.getNextCustomer();
                if (nextCustomerId != null) {
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
            return true;
        } catch (BookingNotConfirmedException | UserNotFoundException | SlotNotFoundException | DatabaseException e) {
            throw new RuntimeException(ExceptionHandler.handleException(e), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during booking cancellation: " + e.getMessage(), e);
        }
    }

    @Override
    public FlipfitBooking addToWaitlist(int customerId, int slotId, LocalDate bookingDate) {
        try {
            FlipfitSlot slot = slotDAO.getSlotById(slotId);
            if (slot == null) {
                throw new SlotNotFoundException(String.valueOf(slotId), "N/A");
            }

            FlipfitCustomer customer = customerDAO.getCustomerById(customerId);
            if (customer == null) {
                throw new UserNotFoundException(String.valueOf(customerId), "Customer");
            }

            FlipfitWaitlist waitlist = waitlistDAO.getWaitlistBySlotId(slotId);
            if (waitlist == null) {
                waitlist = new FlipfitWaitlist();
                waitlist.setSlotId(slotId);
                boolean created = waitlistDAO.addWaitlist(waitlist);
                if (!created) {
                    throw new DatabaseException("Failed to create waitlist for slot " + slotId);
                }
            }

            if (waitlist.getCustomerIds().contains(customerId)) {
                throw new BookingNotConfirmedException("You are already on the waitlist for this slot.");
            }

            waitlist.getCustomerIds().add(customerId);
            boolean updated = waitlistDAO.updateWaitlist(waitlist);
            if (!updated) {
                throw new DatabaseException("Failed to update waitlist for slot " + slotId);
            }

            FlipfitBooking waitlistBooking = new FlipfitBooking(0, customerId, slotId, slot.getCenterId(), bookingDate, slot.getPrice());
            waitlistBooking.setStatus(FlipfitBooking.BookingStatus.WAITLISTED);
            boolean bookingAdded = bookingDAO.addBooking(waitlistBooking);

            if (!bookingAdded) {
                throw new BookingNotConfirmedException("Failed to add waitlist booking for slot " + slotId);
            }

            return waitlistBooking;
        } catch (SlotNotFoundException | UserNotFoundException | BookingNotConfirmedException | DatabaseException e) {
            throw new RuntimeException(ExceptionHandler.handleException(e), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error adding to waitlist: " + e.getMessage(), e);
        }
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
