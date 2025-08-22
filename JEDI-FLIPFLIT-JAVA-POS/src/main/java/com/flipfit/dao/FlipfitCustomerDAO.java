package com.flipfit.dao;

import com.flipfit.bean.FlipfitCustomer;
import java.util.List;

/**
 * @author Flipfit Team
 * @description DAO interface for customer-related database operations in the Flipfit system.
 */
public interface FlipfitCustomerDAO {
    /**
     * @method getCustomerById
     * @parameter customerId The ID of the customer to retrieve.
     * @description Retrieves a customer by their ID.
     * @return FlipfitCustomer object if found, null otherwise.
     */
    FlipfitCustomer getCustomerById(int customerId);

    /**
     * @method getCustomerByEmail
     * @parameter email The email address of the customer to retrieve.
     * @description Retrieves a customer by their email address.
     * @return FlipfitCustomer object if found, null otherwise.
     */
    FlipfitCustomer getCustomerByEmail(String email);

    /**
     * @method addCustomer
     * @parameter customer The customer object to add.
     * @description Adds a new customer to the database.
     * @return True if added successfully, false otherwise.
     */
    boolean addCustomer(FlipfitCustomer customer);

    /**
     * @method updateCustomer
     * @parameter customer The customer object with updated details.
     * @description Updates an existing customer in the database.
     * @return True if updated successfully, false otherwise.
     */
    boolean updateCustomer(FlipfitCustomer customer);

    /**
     * @method deleteCustomer
     * @parameter customerId The ID of the customer to delete.
     * @description Deletes a customer from the database.
     * @return True if deleted successfully, false otherwise.
     */
    boolean deleteCustomer(int customerId);

    /**
     * @method getAllCustomers
     * @description Retrieves all customers from the database.
     * @return List of FlipfitCustomer objects.
     */
    List<FlipfitCustomer> getAllCustomers();

    /**
     * @method getNextCustomerId
     * @description Retrieves the next available customer ID for insertion.
     * @return The next customer ID.
     */
    int getNextCustomerId();
}
