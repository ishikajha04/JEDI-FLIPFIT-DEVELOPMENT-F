package com.flipfit.business;

import com.flipfit.bean.FlipfitCustomer;
import com.flipfit.bean.FlipfitGymCenter;
import com.flipfit.bean.FlipfitSlot;
import com.flipfit.bean.FlipfitBooking;
import com.flipfit.bean.FlipfitCard;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Sukhmani
 * @description Service interface for managing customer operations in the Flipfit system
 */
public interface FlipfitCustomerService {
    /**
     * @method registerCustomer
     * @parameter customer FlipfitCustomer object containing customer details
     * @return boolean indicating success or failure of registration
     */
    boolean registerCustomer(FlipfitCustomer customer);

    /**
     * @method authenticateCustomer
     * @parameter email Customer's email address
     * @parameter password Customer's password
     * @return FlipfitCustomer object if authentication successful, null otherwise
     */
    FlipfitCustomer authenticateCustomer(String email, String password);

    /**
     * @method viewAvailableGymCenters
     * @return List of available gym centers
     */
    List<FlipfitGymCenter> viewAvailableGymCenters();

    /**
     * @method viewGymCentersByLocation
     * @parameter location to search for gym centers
     * @return List of gym centers at specified location
     */
    List<FlipfitGymCenter> viewGymCentersByLocation(String location);

    /**
     * @method viewAvailableSlots
     * @parameter centerId ID of the gym center
     * @parameter day of the week
     * @return List of available slots for the specified center and day
     * @exception  if database operation fails
     */
    List<FlipfitSlot> viewAvailableSlots(int centerId, String day);

    /**
     * @method bookSlot
     * @parameter customerId ID of the customer
     * @parameter slotId ID of the slot to book
     * @parameter bookingDate Date for the booking
     * @return FlipfitBooking object if booking successful, null otherwise
     * @exception  if database operation fails
     */
    FlipfitBooking bookSlot(int customerId, int slotId, LocalDate bookingDate);

    /**
     * @method cancelBooking
     * @parameter bookingId ID of the booking to cancel
     * @parameter customerId ID of the customer
     * @return boolean indicating success or failure of cancellation
     */
    boolean cancelBooking(int bookingId, int customerId);

    /**
     * @method viewBookings
     * @parameter customerId ID of the customer
     * @return List of all bookings for the customer
     */
    List<FlipfitBooking> viewBookings(int customerId);

    /**
     * @method viewBookingsByDate
     * @parameter customerId ID of the customer
     * @parameter date Date to filter bookings
     * @return List of bookings for the specified date
     */
    List<FlipfitBooking> viewBookingsByDate(int customerId, LocalDate date);

    /**
     * @method getCustomerProfile
     * @parameter customerId ID of the customer
     * @return FlipfitCustomer object containing customer profile
     */
    FlipfitCustomer getCustomerProfile(int customerId);

    /**
     * @method updateCustomerProfile
     * @parameter customer FlipfitCustomer object with updated details
     * @return boolean indicating success or failure of update
     */
    boolean updateCustomerProfile(FlipfitCustomer customer);

    /**
     * @method addToWaitlist
     * @parameter customerId ID of the customer
     * @parameter slotId ID of the slot
     * @parameter bookingDate Preferred booking date
     * @return FlipfitBooking object with waitlist status if successful
     */
    FlipfitBooking addToWaitlist(int customerId, int slotId, LocalDate bookingDate);

    /**
     * @method addCard
     * @parameter card FlipfitCard object containing card details
     * @return boolean indicating success or failure of card addition
     */
    boolean addCard(FlipfitCard card);

    /**
     * @method removeCard
     * @parameter cardId ID of the card to remove
     * @parameter customerId ID of the customer
     * @return boolean indicating success or failure of card removal
     */
    boolean removeCard(int cardId, int customerId);

    /**
     * @method updateCard
     * @parameter card FlipfitCard object with updated details
     * @return boolean indicating success or failure of card update
     */
    boolean updateCard(FlipfitCard card);

    /**
     * @method getCustomerCards
     * @parameter customerId ID of the customer
     * @return List of cards associated with the customer
     */
    List<FlipfitCard> getCustomerCards(int customerId);
}
