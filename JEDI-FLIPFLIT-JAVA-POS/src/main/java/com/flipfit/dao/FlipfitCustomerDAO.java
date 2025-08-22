package com.flipfit.dao;

import com.flipfit.bean.FlipfitCustomer;
import java.util.List;

/**
 * DAO interface for customer-related database operations in the Flipfit system
 * @author Khushi, Kritika
 * @description This interface defines the data access operations for customers in the Flipfit application
 */
public interface FlipfitCustomerDAO {
    /**
     * Retrieves a customer by their ID
     * @method getCustomerById
     * @param customerId The ID of the customer to retrieve
     * @return FlipfitCustomer object if found, null otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches a customer's complete profile from the database using their unique ID
     */
    FlipfitCustomer getCustomerById(int customerId);

    /**
     * Retrieves a customer by their email address
     * @method getCustomerByEmail
     * @param email The email address of the customer to retrieve
     * @return FlipfitCustomer object if found, null otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches a customer's complete profile from the database using their email address
     */
    FlipfitCustomer getCustomerByEmail(String email);

    /**
     * Adds a new customer to the database
     * @method addCustomer
     * @param customer The customer object to add
     * @return True if added successfully, false otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Inserts a new customer record into the database with all required details
     */
    boolean addCustomer(FlipfitCustomer customer);

    /**
     * Updates an existing customer in the database
     * @method updateCustomer
     * @param customer The customer object with updated details
     * @return True if updated successfully, false otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.UserNotFoundException If the customer doesn't exist
     * @description Updates the profile information of an existing customer in the database
     */
    boolean updateCustomer(FlipfitCustomer customer);

    /**
     * Deletes a customer from the database
     * @method deleteCustomer
     * @param customerId The ID of the customer to delete
     * @return True if deleted successfully, false otherwise
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @exception com.flipfit.exception.UserNotFoundException If the customer doesn't exist
     * @description Removes a customer record from the database based on the provided ID
     */
    boolean deleteCustomer(int customerId);

    /**
     * Retrieves all customers from the database
     * @method getAllCustomers
     * @return List of all customers in the system
     * @exception com.flipfit.exception.DatabaseException If there is an issue with the database operation
     * @description Fetches all customer records from the database
     */
    List<FlipfitCustomer> getAllCustomers();

    /**
     * @method getNextCustomerId
     * @description Retrieves the next available customer ID for insertion.
     * @return The next customer ID.
     */
    int getNextCustomerId();
}
