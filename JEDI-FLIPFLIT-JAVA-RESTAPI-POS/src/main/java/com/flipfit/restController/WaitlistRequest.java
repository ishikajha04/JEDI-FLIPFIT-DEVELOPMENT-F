package com.flipfit.restController;

/**
 * Request object for adding to waitlist
 */
public class WaitlistRequest {
    private int customerId;
    private int slotId;
    private String bookingDate;

    // Default constructor
    public WaitlistRequest() {}

    // Getters and setters
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

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
}
