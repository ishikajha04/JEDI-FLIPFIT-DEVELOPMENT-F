package com.flipfit.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Flipfit Team
 * @description Represents a booking made by a customer for a slot at a gym center.
 */
public class FlipfitBooking {
    private int bookingId;
    private int customerId;
    private int slotId;
    private int centerId;
    private LocalDate bookingDate;
    private LocalDateTime bookingTime;
    private BookingStatus status;
    private double amount;

    /**
     * @author Flipfit Team
     * @description Status of the booking.
     */
    public enum BookingStatus {
        CONFIRMED, CANCELLED, PENDING, COMPLETED, WAITLISTED
    }

    /**
     * @method FlipfitBooking
     * @description Default constructor for FlipfitBooking.
     */
    public FlipfitBooking() {
    }

    /**
     * @method FlipfitBooking
     * @parameter bookingId The unique booking ID.
     * @parameter customerId The customer making the booking.
     * @parameter slotId The slot being booked.
     * @parameter centerId The gym center for the booking.
     * @parameter bookingDate The date of the booking.
     * @parameter amount The amount paid for the booking.
     * @description Constructs a FlipfitBooking with specified details.
     */
    public FlipfitBooking(int bookingId, int customerId, int slotId, int centerId,
                         LocalDate bookingDate, double amount) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.slotId = slotId;
        this.centerId = centerId;
        this.bookingDate = bookingDate;
        this.bookingTime = LocalDateTime.now();
        this.status = BookingStatus.CONFIRMED;
        this.amount = amount;
    }

    // Getters and Setters
    /**
     * @method getBookingId
     * @description Gets the booking ID.
     * @return The unique booking ID.
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * @method setBookingId
     * @parameter bookingId The unique booking ID to set.
     * @description Sets the booking ID.
     */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * @method getCustomerId
     * @description Gets the customer ID.
     * @return The customer ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @method setCustomerId
     * @parameter customerId The customer ID to set.
     * @description Sets the customer ID.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @method getSlotId
     * @description Gets the slot ID.
     * @return The slot ID.
     */
    public int getSlotId() {
        return slotId;
    }

    /**
     * @method setSlotId
     * @parameter slotId The slot ID to set.
     * @description Sets the slot ID.
     */
    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    /**
     * @method getCenterId
     * @description Gets the center ID.
     * @return The center ID.
     */
    public int getCenterId() {
        return centerId;
    }

    /**
     * @method setCenterId
     * @parameter centerId The center ID to set.
     * @description Sets the center ID.
     */
    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    /**
     * @method getBookingDate
     * @description Gets the booking date.
     * @return The booking date.
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    /**
     * @method setBookingDate
     * @parameter bookingDate The booking date to set.
     * @description Sets the booking date.
     */
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * @method getBookingTime
     * @description Gets the booking time.
     * @return The booking time.
     */
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    /**
     * @method setBookingTime
     * @parameter bookingTime The booking time to set.
     * @description Sets the booking time.
     */
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    /**
     * @method getStatus
     * @description Gets the booking status.
     * @return The booking status.
     */
    public BookingStatus getStatus() {
        return status;
    }

    /**
     * @method setStatus
     * @parameter status The status to set.
     * @description Sets the booking status.
     */
    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    /**
     * @method getAmount
     * @description Gets the booking amount.
     * @return The booking amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @method setAmount
     * @parameter amount The amount to set.
     * @description Sets the booking amount.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @method toString
     * @description Returns a string representation of the booking.
     * @return A string containing booking details.
     */
    @Override
    public String toString() {
        return "FlipfitBooking{" +
                "bookingId=" + bookingId +
                ", customerId=" + customerId +
                ", slotId=" + slotId +
                ", centerId=" + centerId +
                ", bookingDate=" + bookingDate +
                ", bookingTime=" + bookingTime +
                ", status=" + status +
                ", amount=" + amount +
                '}';
    }
}