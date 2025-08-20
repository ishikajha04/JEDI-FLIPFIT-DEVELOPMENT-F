package com.flipfit.bean;
import java.util.Date;

public class Booking {
    public enum BookingStatusEnum {
        CONFIRMED,
        WAITLISTED,
        CANCELLED
    }

    private int bookingId;
    private int userId;
    private int slotId;
    private int centerId;
    private BookingStatusEnum bookingStatus;

    private String type;
    private Date date;
    private String customerEmail;

    public Booking() {
        super();
    }

    public Booking(int bookingId, int userId, int slotId, int centerId, BookingStatusEnum bookingStatus, String type,Date date,String customerEmail)
    {
        this.bookingId=bookingId;
        this.slotId=slotId;
        this.centerId=centerId;
        this.type=type;
        this.date=date;
        this.customerEmail=customerEmail;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

}