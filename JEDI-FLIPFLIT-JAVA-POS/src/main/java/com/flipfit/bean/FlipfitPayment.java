package com.flipfit.bean;

import java.util.Date;

public class FlipfitPayment {
    public int paymentId;
    public int bookingId;
    public float amount;
    public String paymentMethod;
    public String status;
    public String paymentDateTime;

    public FlipfitPayment(int paymentId, int bookingId, float amount,String paymentMethod, String status, String paymentDateTime){
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.paymentDateTime = paymentDateTime;
    }
}
