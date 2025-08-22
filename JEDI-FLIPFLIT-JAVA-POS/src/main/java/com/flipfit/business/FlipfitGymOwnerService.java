package com.flipfit.business;

import com.flipfit.bean.*;
import java.util.List;

/**
 * @author Sukhmani
 * @description Service interface for managing gym owner operations in the Flipfit system
 */
public interface FlipfitGymOwnerService {
    /**
     * @method registerGymOwner
     * @parameter owner FlipfitGymOwner object containing owner details
     * @return boolean indicating success or failure of registration
     */
    boolean registerGymOwner(FlipfitGymOwner owner);

    /**
     * @method authenticateGymOwner
     * @parameter email Gym owner's email address
     * @parameter password Gym owner's password
     * @return FlipfitGymOwner object if authentication successful, null otherwise
     */
    FlipfitGymOwner authenticateGymOwner(String email, String password);

    /**
     * @method addGymCenter
     * @parameter center FlipfitGymCenter object containing center details
     * @return boolean indicating success or failure of center addition
     */
    boolean addGymCenter(FlipfitGymCenter center);

    /**
     * @method updateGymCenter
     * @parameter center FlipfitGymCenter object with updated details
     * @return boolean indicating success or failure of update
     */
    boolean updateGymCenter(FlipfitGymCenter center);

    /**
     * @method removeGymCenter
     * @parameter centerId ID of the center to remove
     * @parameter ownerId ID of the owner
     * @return boolean indicating success or failure of removal
     */
    boolean removeGymCenter(int centerId, int ownerId);

    /**
     * @method viewOwnedGymCenters
     * @parameter ownerId ID of the gym owner
     * @return List of gym centers owned by the specified owner
     */
    List<FlipfitGymCenter> viewOwnedGymCenters(int ownerId);

    /**
     * @method addSlotToCenter
     * @parameter slot FlipfitSlot object containing slot details
     * @return boolean indicating success or failure of slot addition
     */
    boolean addSlotToCenter(FlipfitSlot slot);

    /**
     * @method updateSlot
     * @parameter slot FlipfitSlot object with updated details
     * @return boolean indicating success or failure of update
     */
    boolean updateSlot(FlipfitSlot slot);

    /**
     * @method removeSlot
     * @parameter slotId ID of the slot to remove
     * @parameter ownerId ID of the owner
     * @return boolean indicating success or failure of removal
     */
    boolean removeSlot(int slotId, int ownerId);

    /**
     * @method viewSlotsForCenter
     * @parameter centerId ID of the gym center
     * @parameter ownerId ID of the owner
     * @return List of slots for the specified center
     */
    List<FlipfitSlot> viewSlotsForCenter(int centerId, int ownerId);

    /**
     * @method viewBookingsForCenter
     * @parameter centerId ID of the gym center
     * @parameter ownerId ID of the owner
     * @return List of bookings for the specified center
     */
    List<FlipfitBooking> viewBookingsForCenter(int centerId, int ownerId);

    /**
     * @method getOwnerProfile
     * @parameter ownerId ID of the gym owner
     * @return FlipfitGymOwner object containing owner profile
     */
    FlipfitGymOwner getOwnerProfile(int ownerId);

    /**
     * @method updateOwnerProfile
     * @parameter owner FlipfitGymOwner object with updated details
     * @return boolean indicating success or failure of update
     */
    boolean updateOwnerProfile(FlipfitGymOwner owner);

    /**
     * @method isOwnerApproved
     * @parameter ownerId ID of the gym owner
     * @return boolean indicating if the owner is approved
     */
    boolean isOwnerApproved(int ownerId);
}
