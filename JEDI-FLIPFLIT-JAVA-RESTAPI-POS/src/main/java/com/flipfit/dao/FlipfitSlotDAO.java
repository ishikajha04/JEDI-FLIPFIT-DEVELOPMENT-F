package com.flipfit.dao;

import com.flipfit.bean.FlipfitSlot;
import java.util.List;

/**
 * @author Flipfit Team
 * @description DAO interface for slot-related database operations in the Flipfit system.
 */
public interface FlipfitSlotDAO {
    /**
     * @method getSlotById
     * @parameter slotId The ID of the slot to retrieve.
     * @description Retrieves a slot by its ID.
     * @return FlipfitSlot object if found, null otherwise.
     */
    FlipfitSlot getSlotById(int slotId);

    /**
     * @method addSlot
     * @parameter slot The slot object to add.
     * @description Adds a new slot to the database.
     * @return True if added successfully, false otherwise.
     */
    boolean addSlot(FlipfitSlot slot);

    /**
     * @method updateSlot
     * @parameter slot The slot object with updated details.
     * @description Updates an existing slot in the database.
     * @return True if updated successfully, false otherwise.
     */
    boolean updateSlot(FlipfitSlot slot);

    /**
     * @method deleteSlot
     * @parameter slotId The ID of the slot to delete.
     * @description Deletes a slot from the database.
     * @return True if deleted successfully, false otherwise.
     */
    boolean deleteSlot(int slotId);

    /**
     * @method getAllSlots
     * @description Retrieves all slots from the database.
     * @return List of FlipfitSlot objects.
     */
    List<FlipfitSlot> getAllSlots();

    /**
     * @method getSlotsByCenterId
     * @parameter centerId The center ID to filter slots.
     * @description Retrieves slots by center ID.
     * @return List of FlipfitSlot objects for the center.
     */
    List<FlipfitSlot> getSlotsByCenterId(int centerId);

    /**
     * @method getAvailableSlotsByCenterAndDay
     * @parameter centerId The center ID.
     * @parameter day The day to filter available slots.
     * @description Retrieves available slots for a center on a specific day.
     * @return List of available FlipfitSlot objects.
     */
    List<FlipfitSlot> getAvailableSlotsByCenterAndDay(int centerId, String day);

    /**
     * @method updateSlotAvailability
     * @parameter slotId The slot ID.
     * @parameter availableSeats The number of available seats to set.
     * @description Updates the availability of a slot.
     * @return True if updated successfully, false otherwise.
     */
    boolean updateSlotAvailability(int slotId, int availableSeats);

    /**
     * @method getNextSlotId
     * @description Retrieves the next available slot ID for insertion.
     * @return The next slot ID.
     */
    int getNextSlotId();
}
