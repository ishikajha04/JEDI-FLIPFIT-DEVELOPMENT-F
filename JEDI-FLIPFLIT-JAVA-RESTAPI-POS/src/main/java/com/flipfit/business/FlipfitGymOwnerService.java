package com.flipfit.business;

import com.flipfit.bean.*;
import java.util.List;

/**
 * Service interface for managing gym owner operations in the Flipfit system
 * @author Ishita, Shubham
 * @description This interface defines the business operations that can be performed by gym owners in the Flipfit application
 */
public interface FlipfitGymOwnerService {
    /**
     * Registers a new gym owner in the system
     * @method registerGymOwner
     * @param owner FlipfitGymOwner object containing owner details
     * @return boolean indicating success or failure of registration
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Creates a new gym owner account in the system with all required details
     */
    boolean registerGymOwner(FlipfitGymOwner owner);

    /**
     * Authenticates a gym owner with their credentials
     * @method authenticateGymOwner
     * @param email Gym owner's email address
     * @param password Gym owner's password
     * @return FlipfitGymOwner object if authentication successful, null otherwise
     * @exception com.flipfit.exception.UserNotFoundException If the gym owner with given email doesn't exist
     * @description Verifies the gym owner's credentials and returns their profile if authenticated
     */
    FlipfitGymOwner authenticateGymOwner(String email, String password);

    /**
     * Adds a new gym center to the system
     * @method addGymCenter
     * @param center FlipfitGymCenter object containing center details
     * @return boolean indicating success or failure of center addition
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Creates a new gym center entry in the system with the provided details
     */
    boolean addGymCenter(FlipfitGymCenter center);

    /**
     * Updates an existing gym center's information
     * @method updateGymCenter
     * @param center FlipfitGymCenter object with updated details
     * @return boolean indicating success or failure of update
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Updates the details of an existing gym center in the system
     */
    boolean updateGymCenter(FlipfitGymCenter center);

    /**
     * Removes a gym center from the system
     * @method removeGymCenter
     * @param centerId ID of the center to remove
     * @param ownerId ID of the owner
     * @return boolean indicating success or failure of removal
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Removes a gym center from the system if it belongs to the specified owner
     */
    boolean removeGymCenter(int centerId, int ownerId);

    /**
     * Retrieves all gym centers owned by a specific owner
     * @method viewOwnedGymCenters
     * @param ownerId ID of the gym owner
     * @return List of gym centers owned by the specified owner
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches and returns all gym centers that belong to the specified gym owner
     */
    List<FlipfitGymCenter> viewOwnedGymCenters(int ownerId);

    /**
     * Adds a new slot to a gym center
     * @method addSlotToCenter
     * @param slot FlipfitSlot object containing slot details
     * @return boolean indicating success or failure of slot addition
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Creates a new time slot for a gym center with the provided details
     */
    boolean addSlotToCenter(FlipfitSlot slot);

    /**
     * Updates an existing slot's information
     * @method updateSlot
     * @param slot FlipfitSlot object with updated details
     * @return boolean indicating success or failure of update
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.SlotNotFoundException If the slot doesn't exist
     * @description Updates the details of an existing slot in the system
     */
    boolean updateSlot(FlipfitSlot slot);

    /**
     * Removes a slot from the system
     * @method removeSlot
     * @param slotId ID of the slot to remove
     * @param ownerId ID of the owner
     * @return boolean indicating success or failure of removal
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.SlotNotFoundException If the slot doesn't exist
     * @description Removes a slot from the system if it belongs to a center owned by the specified owner
     */
    boolean removeSlot(int slotId, int ownerId);

    /**
     * Retrieves all slots for a specific gym center
     * @method viewSlotsForCenter
     * @param centerId ID of the gym center
     * @param ownerId ID of the owner
     * @return List of slots for the specified center
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches and returns all available slots for a specific gym center
     */
    List<FlipfitSlot> viewSlotsForCenter(int centerId, int ownerId);

    /**
     * Retrieves all bookings for a specific gym center
     * @method viewBookingsForCenter
     * @param centerId ID of the gym center
     * @param ownerId ID of the owner
     * @return List of bookings for the specified center
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches and returns all bookings made for a specific gym center
     */
    List<FlipfitBooking> viewBookingsForCenter(int centerId, int ownerId);

    /**
     * Retrieves the profile of a gym owner
     * @method getOwnerProfile
     * @param ownerId ID of the gym owner
     * @return FlipfitGymOwner object containing owner profile
     * @exception com.flipfit.exception.UserNotFoundException If the gym owner doesn't exist
     * @description Fetches and returns the profile details of a specific gym owner
     */
    FlipfitGymOwner getOwnerProfile(int ownerId);

    /**
     * Updates a gym owner's profile information
     * @method updateOwnerProfile
     * @param owner FlipfitGymOwner object with updated details
     * @return boolean indicating success or failure of update
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.UserNotFoundException If the gym owner doesn't exist
     * @description Updates the profile details of a gym owner in the system
     */
    boolean updateOwnerProfile(FlipfitGymOwner owner);

    /**
     * Checks if a gym owner is approved
     * @method isOwnerApproved
     * @param ownerId ID of the gym owner
     * @return boolean indicating if the owner is approved
     * @exception com.flipfit.exception.UserNotFoundException If the gym owner doesn't exist
     * @description Verifies if the specified gym owner has been approved in the system
     */
    boolean isOwnerApproved(int ownerId);
}
