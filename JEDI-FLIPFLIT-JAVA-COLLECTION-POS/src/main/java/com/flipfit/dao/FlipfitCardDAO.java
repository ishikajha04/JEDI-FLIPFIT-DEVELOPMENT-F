package com.flipfit.dao;

import com.flipfit.bean.FlipfitCard;
import java.util.List;

public interface FlipfitCardDAO {
    boolean addCard(FlipfitCard card);
    boolean removeCard(int cardId, int customerId);
    boolean updateCard(FlipfitCard card);
    List<FlipfitCard> getCustomerCards(int customerId);
}
