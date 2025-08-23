package com.flipfit.dao;

import com.flipfit.bean.FlipfitAdmin;
import java.util.List;

/**
 * DAO interface for admin-related database operations in the Flipfit system
 * @author Rishita, Prerna
 * @description This interface defines the data access operations for administrators in the Flipfit application
 */
public interface FlipfitAdminDAO {
    /**
     * Retrieves an admin by their ID
     * @method getAdminById
     * @param adminId The ID of the admin to retrieve
     * @return FlipfitAdmin object if found, null otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches an admin's complete profile from the database using their unique ID
     */
    FlipfitAdmin getAdminById(int adminId);

    /**
     * Retrieves an admin by their email address
     * @method getAdminByEmail
     * @param email The email address of the admin to retrieve
     * @return FlipfitAdmin object if found, null otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches an admin's complete profile from the database using their email address
     */
    FlipfitAdmin getAdminByEmail(String email);

    /**
     * Adds a new admin to the database
     * @method addAdmin
     * @param admin The admin object to add
     * @return True if added successfully, false otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Inserts a new admin record into the database with all required details
     */
    boolean addAdmin(FlipfitAdmin admin);

    /**
     * Updates an existing admin in the database
     * @method updateAdmin
     * @param admin The admin object with updated details
     * @return True if updated successfully, false otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.UserNotFoundException If the admin doesn't exist
     * @description Updates the profile information of an existing admin in the database
     */
    boolean updateAdmin(FlipfitAdmin admin);

    /**
     * Deletes an admin from the database
     * @method deleteAdmin
     * @param adminId The ID of the admin to delete
     * @return True if deleted successfully, false otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.UserNotFoundException If the admin doesn't exist
     * @description Removes an admin record from the database based on the provided ID
     */
    boolean deleteAdmin(int adminId);

    /**
     * Retrieves all admins from the database
     * @method getAllAdmins
     * @return List of all admins in the system
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches all admin records from the database
     */
    List<FlipfitAdmin> getAllAdmins();

    /**
     * @method getNextAdminId
     * @description Retrieves the next available admin ID for insertion.
     * @return The next admin ID.
     */
    int getNextAdminId();
}
