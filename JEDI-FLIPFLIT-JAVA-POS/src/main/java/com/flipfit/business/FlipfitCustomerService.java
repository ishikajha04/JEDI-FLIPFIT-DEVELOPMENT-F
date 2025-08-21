package com.flipfit.business;

import com.flipfit.bean.FlipfitCustomer;
import com.flipfit.bean.FlipfitGymCenter;
import com.flipfit.bean.FlipfitSlot;
import com.flipfit.bean.FlipfitBooking;
import com.flipfit.bean.FlipfitCard;

import java.time.LocalDate;
import java.util.List;

public interface FlipfitCustomerService {
    boolean registerCustomer(FlipfitCustomer customer);
    FlipfitCustomer authenticateCustomer(String email, String password);
    List<FlipfitGymCenter> viewAvailableGymCenters();
    List<FlipfitGymCenter> viewGymCentersByLocation(String location);
    List<FlipfitSlot> viewAvailableSlots(int centerId, String day);
    FlipfitBooking bookSlot(int customerId, int slotId, LocalDate bookingDate);
    boolean cancelBooking(int bookingId, int customerId);
    List<FlipfitBooking> viewBookings(int customerId);
    List<FlipfitBooking> viewBookingsByDate(int customerId, LocalDate date);
    FlipfitCustomer getCustomerProfile(int customerId);
    boolean updateCustomerProfile(FlipfitCustomer customer);

    // Card Management methods
    boolean addCard(FlipfitCard card);
    boolean removeCard(int cardId, int customerId);
    boolean updateCard(FlipfitCard card);
    List<FlipfitCard> getCustomerCards(int customerId);
}
