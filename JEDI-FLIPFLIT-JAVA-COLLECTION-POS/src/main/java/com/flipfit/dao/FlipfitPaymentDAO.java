package com.flipfit.dao;

import com.flipfit.bean.FlipfitPayment;
import java.util.List;

public interface FlipfitPaymentDAO {
    boolean addPaymentMethod(FlipfitPayment payment);
    List<FlipfitPayment> getCustomerPaymentMethods(int customerId);
    boolean deactivatePaymentMethod(int customerId, String lastFourDigits);
    boolean updatePaymentMethodExpiry(int customerId, String lastFourDigits, String newExpiryDate);
    FlipfitPayment getPaymentMethod(int customerId, String lastFourDigits);
}
