package com.flipfit.business;

public class FlipfitPaymentService {
    public void processPayment(int bookingid,double amount){
        System.out.println("Processing payment for id " + bookingid);
    }
    public void getPaymentDetails(int paymentid) {
        System.out.println("Getting payment details for id " + paymentid);
    }
}
