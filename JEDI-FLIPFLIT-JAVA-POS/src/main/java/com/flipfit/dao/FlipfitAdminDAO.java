package com.flipfit.dao;

import com.flipfit.bean.FlipfitAdmin;
import java.util.List;

/**
 * @author Flipfit Team
 * @description DAO interface for admin-related database operations in the Flipfit system.
 */
public interface FlipfitAdminDAO {
    /**
     * @method getAdminById
     * @parameter adminId The ID of the admin to retrieve.
     * @description Retrieves an admin by their ID.
     * @return FlipfitAdmin object if found, null otherwise.
     */
    FlipfitAdmin getAdminById(int adminId);

    /**
     * @method getAdminByEmail
     * @parameter email The email address of the admin to retrieve.
     * @description Retrieves an admin by their email address.
     * @return FlipfitAdmin object if found, null otherwise.
     */
    FlipfitAdmin getAdminByEmail(String email);

    /**
     * @method addAdmin
     * @parameter admin The admin object to add.
     * @description Adds a new admin to the database.
     * @return True if added successfully, false otherwise.
     */
    boolean addAdmin(FlipfitAdmin admin);

    /**
     * @method updateAdmin
     * @parameter admin The admin object with updated details.
     * @description Updates an existing admin in the database.
     * @return True if updated successfully, false otherwise.
     */
    boolean updateAdmin(FlipfitAdmin admin);

    /**
     * @method deleteAdmin
     * @parameter adminId The ID of the admin to delete.
     * @description Deletes an admin from the database.
     * @return True if deleted successfully, false otherwise.
     */
    boolean deleteAdmin(int adminId);

    /**
     * @method getAllAdmins
     * @description Retrieves all admins from the database.
     * @return List of FlipfitAdmin objects.
     */
    List<FlipfitAdmin> getAllAdmins();

    /**
     * @method getNextAdminId
     * @description Retrieves the next available admin ID for insertion.
     * @return The next admin ID.
     */
    int getNextAdminId();
}
