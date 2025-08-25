package com.flipfit.business;

import com.flipfit.bean.FlipfitCustomer;
import com.flipfit.bean.FlipfitGymCenter;
import com.flipfit.bean.FlipfitSlot;
import com.flipfit.bean.FlipfitBooking;
import com.flipfit.bean.FlipfitCard;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing customer operations in the Flipfit system
 * @author Khushi, Kritika
 * @description This interface defines the business operations that can be performed by customers in the Flipfit application
 */
public interface FlipfitCustomerService {
    /**
     * Registers a new customer in the system
     * @method registerCustomer
     * @param customer FlipfitCustomer object containing customer details
     * @return boolean indicating success or failure of registration
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Creates a new customer account in the system with all required details
     */
    boolean registerCustomer(FlipfitCustomer customer);

    /**
     * Authenticates a customer with their credentials
     * @method authenticateCustomer
     * @param email Customer's email address
     * @param password Customer's password
     * @return FlipfitCustomer object if authentication successful, null otherwise
     * @exception com.flipfit.exception.UserNotFoundException If the customer with given email doesn't exist
     * @description Verifies the customer's credentials and returns their profile if authenticated
     */
    FlipfitCustomer authenticateCustomer(String email, String password);

    /**
     * Retrieves all available gym centers in the system
     * @method viewAvailableGymCenters
     * @return List of available gym centers
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches and returns all active gym centers from the system
     */
    List<FlipfitGymCenter> viewAvailableGymCenters();

    /**
     * Retrieves gym centers at a specific location
     * @method viewGymCentersByLocation
     * @param location Location to search for gym centers
     * @return List of gym centers at specified location
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Searches and returns gym centers available at the specified location
     */
    List<FlipfitGymCenter> viewGymCentersByLocation(String location);

    /**
     * Retrieves available slots for a specific gym center and day
     * @method viewAvailableSlots
     * @param centerId ID of the gym center
     * @param day Day of the week
     * @return List of available slots for the specified center and day
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.SlotNotFoundException If no slots are available
     * @description Fetches and returns all available slots for a specific gym center on a given day
     */
    List<FlipfitSlot> viewAvailableSlots(int centerId, String day);

    /**
     * Books a slot for a customer
     * @method bookSlot
     * @param customerId ID of the customer
     * @param slotId ID of the slot to book
     * @param bookingDate Date for the booking
     * @param cardId ID of the payment card to use
     * @return FlipfitBooking object if booking successful, null otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.SlotNotFoundException If the slot doesn't exist
     * @exception com.flipfit.exception.BookingNotConfirmedException If the booking cannot be confirmed
     * @description Creates a booking for a customer for a specific slot on a given date
     */
    FlipfitBooking bookSlot(int customerId, int slotId, LocalDate bookingDate, int cardId);

    /**
     * Cancels an existing booking
     * @method cancelBooking
     * @param bookingId ID of the booking to cancel
     * @param customerId ID of the customer
     * @return boolean indicating success or failure of cancellation
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.BookingNotConfirmedException If the booking doesn't exist
     * @description Cancels a previously confirmed booking for a customer
     */
    boolean cancelBooking(int bookingId, int customerId);

    /**
     * Retrieves all bookings for a customer
     * @method viewBookings
     * @param customerId ID of the customer
     * @return List of all bookings for the customer
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches and returns all bookings made by a specific customer
     */
    List<FlipfitBooking> viewBookings(int customerId);

    /**
     * Retrieves bookings for a customer on a specific date
     * @method viewBookingsByDate
     * @param customerId ID of the customer
     * @param date Date to filter bookings
     * @return List of bookings for the specified date
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches and returns all bookings made by a customer for a specific date
     */
    List<FlipfitBooking> viewBookingsByDate(int customerId, LocalDate date);

    /**
     * Retrieves the profile of a customer
     * @method getCustomerProfile
     * @param customerId ID of the customer
     * @return FlipfitCustomer object containing customer profile
     * @exception com.flipfit.exception.UserNotFoundException If the customer doesn't exist
     * @description Fetches and returns the profile details of a specific customer
     */
    FlipfitCustomer getCustomerProfile(int customerId);

    /**
     * Updates a customer's profile information
     * @method updateCustomerProfile
     * @param customer FlipfitCustomer object with updated details
     * @return boolean indicating success or failure of update
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.UserNotFoundException If the customer doesn't exist
     * @description Updates the profile details of a customer in the system
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
     * @param card FlipfitCard object containing card details
     * @return boolean indicating success or failure of card addition
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Adds a new payment card to a customer's profile
     */
    boolean addCard(FlipfitCard card);

    /**
     * @method removeCard
     * @parameter cardId ID of the card to remove
     * @parameter customerId ID of the customer
     * @return boolean indicating success or failure of card removal
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Removes a payment card from a customer's profile
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
