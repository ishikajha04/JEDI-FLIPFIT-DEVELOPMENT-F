package com.flipfit.bean;

/**
 * @author Flipfit Team
 * @description Represents a payment method for a Flipfit customer, including card details and status.
 */
public class FlipfitPayment {
    private int paymentId;
    private int customerId;
    private String lastFourDigits;
    private String cardHolderName;
    private String expiryDate;
    private boolean isActive;

    /**
     * @method FlipfitPayment
     * @description Default constructor for FlipfitPayment.
     */
    public FlipfitPayment() {}

    /**
     * @method getPaymentId
     * @description Gets the payment ID.
     * @return The unique payment ID.
     */
    public int getPaymentId() {
        return paymentId;
    }

    /**
     * @method setPaymentId
     * @parameter paymentId The unique payment ID to set.
     * @description Sets the payment ID.
     */
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * @method getCustomerId
     * @description Gets the customer ID associated with the payment.
     * @return The customer ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @method setCustomerId
     * @parameter customerId The customer ID to set.
     * @description Sets the customer ID for the payment.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @method getLastFourDigits
     * @description Gets the last four digits of the card.
     * @return The last four digits of the card.
     */
    public String getLastFourDigits() {
        return lastFourDigits;
    }

    /**
     * @method setLastFourDigits
     * @parameter lastFourDigits The last four digits to set.
     * @description Sets the last four digits of the card.
     */
    public void setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    /**
     * @method getCardHolderName
     * @description Gets the card holder's name.
     * @return The card holder's name.
     */
    public String getCardHolderName() {
        return cardHolderName;
    }

    /**
     * @method setCardHolderName
     * @parameter cardHolderName The card holder's name to set.
     * @description Sets the card holder's name.
     */
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    /**
     * @method getExpiryDate
     * @description Gets the card's expiry date.
     * @return The expiry date.
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * @method setExpiryDate
     * @parameter expiryDate The expiry date to set.
     * @description Sets the card's expiry date.
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @method isActive
     * @description Checks if the payment method is active.
     * @return True if active, false otherwise.
     */
    public boolean getIsActive() {
        return isActive;
    }
    /**
     * @method setActive
     * @parameter active The active status to set.
     * @description Sets the active status of the payment method.
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
