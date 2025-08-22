package com.flipfit.dao;

import com.flipfit.bean.FlipfitGymOwner;
import java.util.List;

/**
 * DAO interface for gym owner-related database operations in the Flipfit system
 * @author Ishita, Shubham
 * @description This interface defines the data access operations for gym owners in the Flipfit application
 */
public interface FlipfitGymOwnerDAO {
    /**
     * Retrieves a gym owner by their ID
     * @method getGymOwnerById
     * @param ownerId The ID of the gym owner to retrieve
     * @return FlipfitGymOwner object if found, null otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches a gym owner's complete profile from the database using their unique ID
     */
    FlipfitGymOwner getGymOwnerById(int ownerId);

    /**
     * Retrieves a gym owner by their email address
     * @method getGymOwnerByEmail
     * @param email The email address of the gym owner to retrieve
     * @return FlipfitGymOwner object if found, null otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches a gym owner's complete profile from the database using their email address
     */
    FlipfitGymOwner getGymOwnerByEmail(String email);

    /**
     * Adds a new gym owner to the database
     * @method addGymOwner
     * @param owner The gym owner object to add
     * @return True if added successfully, false otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Inserts a new gym owner record into the database with all required details
     */
    boolean addGymOwner(FlipfitGymOwner owner);

    /**
     * Updates an existing gym owner in the database
     * @method updateGymOwner
     * @param owner The gym owner object with updated details
     * @return True if updated successfully, false otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.UserNotFoundException If the gym owner doesn't exist
     * @description Updates the profile information of an existing gym owner in the database
     */
    boolean updateGymOwner(FlipfitGymOwner owner);

    /**
     * Deletes a gym owner from the database
     * @method deleteGymOwner
     * @param ownerId The ID of the gym owner to delete
     * @return True if deleted successfully, false otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.UserNotFoundException If the gym owner doesn't exist
     * @description Removes a gym owner record from the database based on the provided ID
     */
    boolean deleteGymOwner(int ownerId);

    /**
     * Retrieves all gym owners from the database
     * @method getAllGymOwners
     * @return List of all gym owners in the system
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches all gym owner records from the database
     */
    List<FlipfitGymOwner> getAllGymOwners();

    /**
     * @method getPendingApprovalOwners
     * @description Retrieves gym owners pending approval.
     * @return List of FlipfitGymOwner objects pending approval.
     */
    List<FlipfitGymOwner> getPendingApprovalOwners();

    /**
     * Checks if a gym owner is approved
     * @method isOwnerApproved
     * @param ownerId The ID of the gym owner
     * @return True if the owner is approved, false otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.UserNotFoundException If the gym owner doesn't exist
     * @description Verifies if a gym owner has been approved in the system
     */
    boolean approveGymOwner(int ownerId);

    /**
     * @method getNextOwnerId
     * @description Retrieves the next available gym owner ID for insertion.
     * @return The next gym owner ID.
     */
    int getNextOwnerId();
}
