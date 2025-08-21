package com.flipfit.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FlipfitBooking {
    private int bookingId;
    private int customerId;
    private int slotId;
    private int centerId;
    private LocalDate bookingDate;
    private LocalDateTime bookingTime;
    private BookingStatus status;
    private double amount;

    public enum BookingStatus {
        CONFIRMED, CANCELLED, PENDING, COMPLETED
    }

    public FlipfitBooking() {
    }

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
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

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