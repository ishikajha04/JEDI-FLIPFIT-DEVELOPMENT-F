package com.flipfit.dao;

import com.flipfit.bean.FlipfitBooking;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Flipfit Team
 * @description DAO interface for booking-related database operations in the Flipfit system.
 */
public interface FlipfitBookingDAO {
    /**
     * @method getBookingById
     * @parameter bookingId The ID of the booking to retrieve.
     * @description Retrieves a booking by its ID.
     * @return FlipfitBooking object if found, null otherwise.
     */
    FlipfitBooking getBookingById(int bookingId);

    /**
     * @method addBooking
     * @parameter booking The booking object to add.
     * @description Adds a new booking to the database.
     * @return True if added successfully, false otherwise.
     */
    boolean addBooking(FlipfitBooking booking);

    /**
     * @method updateBooking
     * @parameter booking The booking object with updated details.
     * @description Updates an existing booking in the database.
     * @return True if updated successfully, false otherwise.
     */
    boolean updateBooking(FlipfitBooking booking);

    /**
     * @method deleteBooking
     * @parameter bookingId The ID of the booking to delete.
     * @description Deletes a booking from the database.
     * @return True if deleted successfully, false otherwise.
     */
    boolean deleteBooking(int bookingId);

    /**
     * @method getAllBookings
     * @description Retrieves all bookings from the database.
     * @return List of FlipfitBooking objects.
     */
    List<FlipfitBooking> getAllBookings();

    /**
     * @method getBookingsByCustomerId
     * @parameter customerId The customer ID to filter bookings.
     * @description Retrieves bookings by customer ID.
     * @return List of FlipfitBooking objects for the customer.
     */
    List<FlipfitBooking> getBookingsByCustomerId(int customerId);

    /**
     * @method getBookingsBySlotId
     * @parameter slotId The slot ID to filter bookings.
     * @description Retrieves bookings by slot ID.
     * @return List of FlipfitBooking objects for the slot.
     */
    List<FlipfitBooking> getBookingsBySlotId(int slotId);

    /**
     * @method getBookingsByDate
     * @parameter date The date to filter bookings.
     * @description Retrieves bookings by date.
     * @return List of FlipfitBooking objects for the date.
     */
    List<FlipfitBooking> getBookingsByDate(LocalDate date);

    /**
     * @method cancelBooking
     * @parameter bookingId The ID of the booking to cancel.
     * @description Cancels a booking in the database.
     * @return True if cancelled successfully, false otherwise.
     */
    boolean cancelBooking(int bookingId);

    /**
     * @method getNextBookingId
     * @description Retrieves the next available booking ID for insertion.
     * @return The next booking ID.
     */
    int getNextBookingId();
}
