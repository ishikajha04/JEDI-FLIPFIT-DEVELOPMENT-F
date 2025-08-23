package com.flipfit.dao;

import com.flipfit.bean.FlipfitWaitlist;
import java.util.List;

/**
 * @author Flipfit Team
 * @description DAO interface for waitlist-related database operations in the Flipfit system.
 */
public interface FlipfitWaitlistDAO {
    /**
     * @method getWaitlistBySlotId
     * @parameter slotId The slot ID to retrieve the waitlist for.
     * @description Retrieves the waitlist for a specific slot.
     * @return FlipfitWaitlist object if found, null otherwise.
     */
    FlipfitWaitlist getWaitlistBySlotId(int slotId);

    /**
     * @method addWaitlist
     * @parameter waitlist The waitlist object to add.
     * @description Adds a new waitlist to the database.
     * @return True if added successfully, false otherwise.
     */
    boolean addWaitlist(FlipfitWaitlist waitlist);

    /**
     * @method updateWaitlist
     * @parameter waitlist The waitlist object with updated details.
     * @description Updates an existing waitlist in the database.
     * @return True if updated successfully, false otherwise.
     */
    boolean updateWaitlist(FlipfitWaitlist waitlist);

    /**
     * @method deleteWaitlist
     * @parameter waitlistId The ID of the waitlist to delete.
     * @description Deletes a waitlist from the database.
     * @return True if deleted successfully, false otherwise.
     */
    boolean deleteWaitlist(int waitlistId);

    /**
     * @method getNextWaitlistId
     * @description Retrieves the next available waitlist ID for insertion.
     * @return The next waitlist ID.
     */
    int getNextWaitlistId();
}