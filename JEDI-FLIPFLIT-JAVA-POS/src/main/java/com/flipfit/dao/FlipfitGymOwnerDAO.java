package com.flipfit.dao;

import com.flipfit.bean.FlipfitGymOwner;
import java.util.List;

/**
 * @author Flipfit Team
 * @description DAO interface for gym owner-related database operations in the Flipfit system.
 */
public interface FlipfitGymOwnerDAO {
    /**
     * @method getGymOwnerById
     * @parameter ownerId The ID of the gym owner to retrieve.
     * @description Retrieves a gym owner by their ID.
     * @return FlipfitGymOwner object if found, null otherwise.
     */
    FlipfitGymOwner getGymOwnerById(int ownerId);

    /**
     * @method getGymOwnerByEmail
     * @parameter email The email address of the gym owner to retrieve.
     * @description Retrieves a gym owner by their email address.
     * @return FlipfitGymOwner object if found, null otherwise.
     */
    FlipfitGymOwner getGymOwnerByEmail(String email);

    /**
     * @method addGymOwner
     * @parameter owner The gym owner object to add.
     * @description Adds a new gym owner to the database.
     * @return True if added successfully, false otherwise.
     */
    boolean addGymOwner(FlipfitGymOwner owner);

    /**
     * @method updateGymOwner
     * @parameter owner The gym owner object with updated details.
     * @description Updates an existing gym owner in the database.
     * @return True if updated successfully, false otherwise.
     */
    boolean updateGymOwner(FlipfitGymOwner owner);

    /**
     * @method deleteGymOwner
     * @parameter ownerId The ID of the gym owner to delete.
     * @description Deletes a gym owner from the database.
     * @return True if deleted successfully, false otherwise.
     */
    boolean deleteGymOwner(int ownerId);

    /**
     * @method getAllGymOwners
     * @description Retrieves all gym owners from the database.
     * @return List of FlipfitGymOwner objects.
     */
    List<FlipfitGymOwner> getAllGymOwners();

    /**
     * @method getPendingApprovalOwners
     * @description Retrieves gym owners pending approval.
     * @return List of FlipfitGymOwner objects pending approval.
     */
    List<FlipfitGymOwner> getPendingApprovalOwners();

    /**
     * @method approveGymOwner
     * @parameter ownerId The ID of the gym owner to approve.
     * @description Approves a gym owner in the database.
     * @return True if approved successfully, false otherwise.
     */
    boolean approveGymOwner(int ownerId);

    /**
     * @method getNextOwnerId
     * @description Retrieves the next available gym owner ID for insertion.
     * @return The next gym owner ID.
     */
    int getNextOwnerId();
}
