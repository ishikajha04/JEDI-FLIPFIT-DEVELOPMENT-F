package com.flipfit.business;

public class BookingBusiness {
    public void cancelBooking(int bookingid){
        System.out.println("Cancelling booking for id " + bookingid);
    }
    public void updateWLStatus(int waitlistid){
        System.out.println("Updating WL status for id " + waitlistid);
    }
}
