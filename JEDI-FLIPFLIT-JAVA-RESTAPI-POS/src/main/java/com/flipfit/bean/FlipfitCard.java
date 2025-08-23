package com.flipfit.bean;

/**
 * @author Flipfit Team
 * @description Represents a payment card associated with a Flipfit customer.
 */
public class FlipfitCard {
    private int cardId;
    private int customerId;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    /**
     * @method FlipfitCard
     * @description Default constructor for FlipfitCard.
     */
    public FlipfitCard() {}

    /**
     * @method getCardId
     * @description Gets the card ID.
     * @return The unique card ID.
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * @method setCardId
     * @parameter cardId The unique card ID to set.
     * @description Sets the card ID.
     */
    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    /**
     * @method getCustomerId
     * @description Gets the customer ID associated with the card.
     * @return The customer ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @method setCustomerId
     * @parameter customerId The customer ID to set.
     * @description Sets the customer ID for the card.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @method getCardNumber
     * @description Gets the card number.
     * @return The card number.
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @method setCardNumber
     * @parameter cardNumber The card number to set.
     * @description Sets the card number.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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
     * @method getCvv
     * @description Gets the card verification value (CVV).
     * @return The CVV.
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * @method setCvv
     * @parameter cvv The CVV to set.
     * @description Sets the card verification value (CVV).
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
