package com.flipfit.dao.impl;

import com.flipfit.dao.FlipfitPaymentDAO;
import com.flipfit.bean.FlipfitPayment;
import java.util.*;

public class FlipfitPaymentDAOImpl implements FlipfitPaymentDAO {
    private static final Map<Integer, List<FlipfitPayment>> customerPayments = new HashMap<>();
    private static int paymentIdCounter = 1;

    @Override
    public boolean addPaymentMethod(FlipfitPayment payment) {
        try {
            payment.setPaymentId(paymentIdCounter++);
            payment.setIsActive(true);
            customerPayments.computeIfAbsent(payment.getCustomerId(), k -> new ArrayList<>()).add(payment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FlipfitPayment> getCustomerPaymentMethods(int customerId) {
        return customerPayments.getOrDefault(customerId, new ArrayList<>())
            .stream()
            .filter(FlipfitPayment::getIsActive)
            .toList();
    }

    @Override
    public boolean deactivatePaymentMethod(int customerId, String lastFourDigits) {
        List<FlipfitPayment> payments = customerPayments.get(customerId);
        if (payments != null) {
            for (FlipfitPayment payment : payments) {
                if (payment.getLastFourDigits().equals(lastFourDigits) && payment.getIsActive()) {
                    payment.setIsActive(false);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean updatePaymentMethodExpiry(int customerId, String lastFourDigits, String newExpiryDate) {
        List<FlipfitPayment> payments = customerPayments.get(customerId);
        if (payments != null) {
            for (FlipfitPayment payment : payments) {
                if (payment.getLastFourDigits().equals(lastFourDigits) && payment.getIsActive()) {
                    payment.setExpiryDate(newExpiryDate);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public FlipfitPayment getPaymentMethod(int customerId, String lastFourDigits) {
        List<FlipfitPayment> payments = customerPayments.get(customerId);
        if (payments != null) {
            return payments.stream()
                .filter(p -> p.getLastFourDigits().equals(lastFourDigits) && p.getIsActive())
                .findFirst()
                .orElse(null);
        }
        return null;
    }
}
