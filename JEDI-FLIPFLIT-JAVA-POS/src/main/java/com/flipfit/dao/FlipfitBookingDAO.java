package com.flipfit.dao;

import com.flipfit.bean.FlipfitBooking;
import java.time.LocalDate;
import java.util.List;

public interface FlipfitBookingDAO {
    FlipfitBooking getBookingById(int bookingId);
    boolean addBooking(FlipfitBooking booking);
    boolean updateBooking(FlipfitBooking booking);
    boolean deleteBooking(int bookingId);
    List<FlipfitBooking> getAllBookings();
    List<FlipfitBooking> getBookingsByCustomerId(int customerId);
    List<FlipfitBooking> getBookingsBySlotId(int slotId);
    List<FlipfitBooking> getBookingsByDate(LocalDate date);
    boolean cancelBooking(int bookingId);
    int getNextBookingId();
}
