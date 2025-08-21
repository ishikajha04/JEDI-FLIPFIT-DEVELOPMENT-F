package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitCard;
import com.flipfit.dao.FlipfitCardDAO;
import java.util.*;

public class FlipfitCardDAOImpl implements FlipfitCardDAO {
    private static final Map<Integer, List<FlipfitCard>> customerCards = new HashMap<>();
    private static int cardIdCounter = 1;

    @Override
    public boolean addCard(FlipfitCard card) {
        try {
            card.setCardId(cardIdCounter++);
            customerCards.computeIfAbsent(card.getCustomerId(), k -> new ArrayList<>()).add(card);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeCard(int cardId, int customerId) {
        List<FlipfitCard> cards = customerCards.get(customerId);
        if (cards != null) {
            return cards.removeIf(card -> card.getCardId() == cardId);
        }
        return false;
    }

    @Override
    public boolean updateCard(FlipfitCard card) {
        List<FlipfitCard> cards = customerCards.get(card.getCustomerId());
        if (cards != null) {
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i).getCardId() == card.getCardId()) {
                    cards.set(i, card);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<FlipfitCard> getCustomerCards(int customerId) {
        return customerCards.getOrDefault(customerId, new ArrayList<>());
    }
}
