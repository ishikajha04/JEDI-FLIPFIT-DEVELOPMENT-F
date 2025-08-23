package com.flipfit.dao;

import com.flipfit.bean.FlipfitCard;
import java.util.List;

/**
 * @author Flipfit Team
 * @description DAO interface for card-related database operations in the Flipfit system.
 */
public interface FlipfitCardDAO {
    /**
     * @method addCard
     * @parameter card The card object to add.
     * @description Adds a new card for a customer.
     * @return True if added successfully, false otherwise.
     */
    boolean addCard(FlipfitCard card);

    /**
     * @method removeCard
     * @parameter cardId The card ID to remove.
     * @parameter customerId The customer ID associated with the card.
     * @description Removes a card for a customer.
     * @return True if removed successfully, false otherwise.
     */
    boolean removeCard(int cardId, int customerId);

    /**
     * @method updateCard
     * @parameter card The card object with updated details.
     * @description Updates a card for a customer.
     * @return True if updated successfully, false otherwise.
     */
    boolean updateCard(FlipfitCard card);

    /**
     * @method getCustomerCards
     * @parameter customerId The customer ID to retrieve cards for.
     * @description Retrieves all cards for a customer.
     * @return List of FlipfitCard objects for the customer.
     */
    List<FlipfitCard> getCustomerCards(int customerId);
}
