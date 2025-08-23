package com.flipfit.dao;

import com.flipfit.bean.FlipfitPayment;
import java.util.List;

/**
 * @author Flipfit Team
 * @description DAO interface for payment method-related database operations in the Flipfit system.
 */
public interface FlipfitPaymentDAO {
    /**
     * @method addPaymentMethod
     * @parameter payment The payment method object to add.
     * @description Adds a new payment method for a customer.
     * @return True if added successfully, false otherwise.
     */
    boolean addPaymentMethod(FlipfitPayment payment);

    /**
     * @method getCustomerPaymentMethods
     * @parameter customerId The customer ID to retrieve payment methods for.
     * @description Retrieves all payment methods for a customer.
     * @return List of FlipfitPayment objects for the customer.
     */
    List<FlipfitPayment> getCustomerPaymentMethods(int customerId);

    /**
     * @method deactivatePaymentMethod
     * @parameter customerId The customer ID.
     * @parameter lastFourDigits The last four digits of the card to deactivate.
     * @description Deactivates a payment method for a customer.
     * @return True if deactivated successfully, false otherwise.
     */
    boolean deactivatePaymentMethod(int customerId, String lastFourDigits);

    /**
     * @method updatePaymentMethodExpiry
     * @parameter customerId The customer ID.
     * @parameter lastFourDigits The last four digits of the card to update.
     * @parameter newExpiryDate The new expiry date to set.
     * @description Updates the expiry date of a payment method for a customer.
     * @return True if updated successfully, false otherwise.
     */
    boolean updatePaymentMethodExpiry(int customerId, String lastFourDigits, String newExpiryDate);

    /**
     * @method getPaymentMethod
     * @parameter customerId The customer ID.
     * @parameter lastFourDigits The last four digits of the card to retrieve.
     * @description Retrieves a specific payment method for a customer.
     * @return FlipfitPayment object if found, null otherwise.
     */
    FlipfitPayment getPaymentMethod(int customerId, String lastFourDigits);
}
