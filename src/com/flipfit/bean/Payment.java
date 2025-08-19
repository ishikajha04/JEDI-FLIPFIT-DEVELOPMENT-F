package com.flipfit.bean;

import java.util.Date;

public class Payment {
    public int paymentId;
    public int bookingId;
    public float amount;
    public String paymentMethod;
    public String status;
    public String paymentDateTime;

    public Payment(int paymentId, int bookingId, float amount,String paymentMethod, String status, String paymentDateTime){
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.paymentDateTime = paymentDateTime;
    }
}
