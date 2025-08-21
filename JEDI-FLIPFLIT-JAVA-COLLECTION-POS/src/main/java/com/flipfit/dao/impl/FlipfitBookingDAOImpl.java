package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitBooking;
import com.flipfit.dao.FlipfitBookingDAO;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FlipfitBookingDAOImpl implements FlipfitBookingDAO {
    private static Map<Integer, FlipfitBooking> bookingMap = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger(1);

    static {
        // Initialize with some dummy booking data
        FlipfitBooking booking1 = new FlipfitBooking(1, 1, 1, 1, LocalDate.now().plusDays(1), 500.0);
        FlipfitBooking booking2 = new FlipfitBooking(2, 2, 3, 1, LocalDate.now().plusDays(2), 600.0);

        bookingMap.put(1, booking1);
        bookingMap.put(2, booking2);

        idCounter.set(3);
    }

    @Override
    public FlipfitBooking getBookingById(int bookingId) {
        return bookingMap.get(bookingId);
    }

    @Override
    public boolean addBooking(FlipfitBooking booking) {
        if (booking == null) {
            return false;
        }
        booking.setBookingId(getNextBookingId());
        bookingMap.put(booking.getBookingId(), booking);
        return true;
    }

    @Override
    public boolean updateBooking(FlipfitBooking booking) {
        if (booking == null || !bookingMap.containsKey(booking.getBookingId())) {
            return false;
        }
        bookingMap.put(booking.getBookingId(), booking);
        return true;
    }

    @Override
    public boolean deleteBooking(int bookingId) {
        return bookingMap.remove(bookingId) != null;
    }

    @Override
    public List<FlipfitBooking> getAllBookings() {
        return new ArrayList<>(bookingMap.values());
    }

    @Override
    public List<FlipfitBooking> getBookingsByCustomerId(int customerId) {
        return bookingMap.values().stream()
                .filter(booking -> booking.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlipfitBooking> getBookingsBySlotId(int slotId) {
        return bookingMap.values().stream()
                .filter(booking -> booking.getSlotId() == slotId)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlipfitBooking> getBookingsByDate(LocalDate date) {
        return bookingMap.values().stream()
                .filter(booking -> booking.getBookingDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        FlipfitBooking booking = bookingMap.get(bookingId);
        if (booking != null) {
            booking.setStatus(FlipfitBooking.BookingStatus.CANCELLED);
            return true;
        }
        return false;
    }

    @Override
    public int getNextBookingId() {
        return idCounter.getAndIncrement();
    }
}
