package com.flipfit.dao;

import com.flipfit.bean.FlipfitUser;
import java.util.List;

/**
 * @author Flipfit Team
 * @description DAO interface for user-related database operations in the Flipfit system.
 */
public interface FlipfitUserDAO {
    /**
     * @method getUserById
     * @parameter userId The ID of the user to retrieve.
     * @description Retrieves a user by their ID.
     * @return FlipfitUser object if found, null otherwise.
     */
    FlipfitUser getUserById(int userId);

    /**
     * @method getUserByEmail
     * @parameter email The email address of the user to retrieve.
     * @description Retrieves a user by their email address.
     * @return FlipfitUser object if found, null otherwise.
     */
    FlipfitUser getUserByEmail(String email);

    /**
     * @method addUser
     * @parameter user The user object to add.
     * @description Adds a new user to the database.
     * @return True if added successfully, false otherwise.
     */
    boolean addUser(FlipfitUser user);

    /**
     * @method updateUser
     * @parameter user The user object with updated details.
     * @description Updates an existing user in the database.
     * @return True if updated successfully, false otherwise.
     */
    boolean updateUser(FlipfitUser user);

    /**
     * @method deleteUser
     * @parameter userId The ID of the user to delete.
     * @description Deletes a user from the database.
     * @return True if deleted successfully, false otherwise.
     */
    boolean deleteUser(int userId);

    /**
     * @method getAllUsers
     * @description Retrieves all users from the database.
     * @return List of FlipfitUser objects.
     */
    List<FlipfitUser> getAllUsers();

    /**
     * @method authenticateUser
     * @parameter email The email address of the user.
     * @parameter password The password of the user.
     * @description Authenticates a user by email and password.
     * @return True if authentication is successful, false otherwise.
     */
    boolean authenticateUser(String email, String password);
}
