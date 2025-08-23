package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitCustomer;
import com.flipfit.dao.FlipfitCustomerDAO;
import com.flipfit.exception.DatabaseException;
import com.flipfit.utils.DBUtils;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of FlipfitCustomerDAO
 */
public class FlipfitCustomerDAOImpl implements FlipfitCustomerDAO {

    @Override
    public FlipfitCustomer getCustomerById(int customerId) {
        String sql = "SELECT u.*, c.* FROM flipfit_users u " +
                     "JOIN flipfit_customers c ON u.user_id = c.user_id " +
                     "WHERE c.customer_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToCustomer(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting customer by ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return null;
    }

    @Override
    public FlipfitCustomer getCustomerByEmail(String email) {
        String sql = "SELECT u.*, c.* FROM flipfit_users u " +
                     "JOIN flipfit_customers c ON u.user_id = c.user_id " +
                     "WHERE u.email = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToCustomer(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting customer by email: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return null;
    }

    @Override
    public boolean addCustomer(FlipfitCustomer customer) {
        Connection connection = null;
        PreparedStatement userStatement = null;
        PreparedStatement customerStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // First insert into flipfit_users table
            String userSql = "INSERT INTO flipfit_users (email, password, phone_number, role) " +
                            "VALUES (?, ?, ?, 'CUSTOMER')";

            userStatement = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, customer.getEmail());
            userStatement.setString(2, customer.getPassword());
            userStatement.setString(3, customer.getPhoneNumber());

            int userRowsAffected = userStatement.executeUpdate();

            if (userRowsAffected > 0) {
                generatedKeys = userStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    customer.setUserId(userId);

                    // Then insert into flipfit_customers table
                    String customerSql = "INSERT INTO flipfit_customers (user_id, name, address, location) VALUES (?, ?, ?, ?)";
                    customerStatement = connection.prepareStatement(customerSql, Statement.RETURN_GENERATED_KEYS);
                    customerStatement.setInt(1, userId);
                    customerStatement.setString(2, customer.getName());
                    customerStatement.setString(3, ""); // Default empty address
                    customerStatement.setString(4, ""); // Default empty location

                    int customerRowsAffected = customerStatement.executeUpdate();

                    if (customerRowsAffected > 0) {
                        ResultSet customerKeys = customerStatement.getGeneratedKeys();
                        if (customerKeys.next()) {
                            customer.setCustomerId(customerKeys.getInt(1));
                        }
                        connection.commit();
                        return true;
                    }
                }
            }

            // If we got here, something went wrong
            connection.rollback();
            return false;

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new DatabaseException("Error rolling back transaction: " + ex.getMessage(), ex);
            }
            throw new DatabaseException("Error adding customer: " + e.getMessage(), e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error resetting auto-commit: " + e.getMessage(), e);
            }
            if (generatedKeys != null) {
                DBUtils.closeResultSet(generatedKeys);
            }
            DBUtils.closeStatement(userStatement);
            DBUtils.closeStatement(customerStatement);
            DBUtils.closeConnection(connection);
        }
    }

    @Override
    public boolean updateCustomer(FlipfitCustomer customer) {
        Connection connection = null;
        PreparedStatement userStatement = null;
        PreparedStatement customerStatement = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            // Update flipfit_users table
            String userSql = "UPDATE flipfit_users SET email = ?, password = ?, phone_number = ? WHERE user_id = ?";
            userStatement = connection.prepareStatement(userSql);
            userStatement.setString(1, customer.getEmail());
            userStatement.setString(2, customer.getPassword());
            userStatement.setString(3, customer.getPhoneNumber());
            userStatement.setInt(4, customer.getUserId());

            int userRowsAffected = userStatement.executeUpdate();

            // Update flipfit_customers table
            String customerSql = "UPDATE flipfit_customers SET name = ? WHERE customer_id = ?";
            customerStatement = connection.prepareStatement(customerSql);
            customerStatement.setString(1, customer.getName());
            customerStatement.setInt(2, customer.getCustomerId());

            int customerRowsAffected = customerStatement.executeUpdate();

            if (userRowsAffected > 0 && customerRowsAffected > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new DatabaseException("Error rolling back transaction: " + ex.getMessage(), ex);
            }
            throw new DatabaseException("Error updating customer: " + e.getMessage(), e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error resetting auto-commit: " + e.getMessage(), e);
            }
            DBUtils.closeStatement(userStatement);
            DBUtils.closeStatement(customerStatement);
            DBUtils.closeConnection(connection);
        }
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Get the user_id first
            String getUserIdSql = "SELECT user_id FROM flipfit_customers WHERE customer_id = ?";
            PreparedStatement getUserIdStatement = connection.prepareStatement(getUserIdSql);
            getUserIdStatement.setInt(1, customerId);
            ResultSet resultSet = getUserIdStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");

                // Due to ON DELETE CASCADE, we only need to delete from flipfit_users table
                String sql = "DELETE FROM flipfit_users WHERE user_id = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, userId);

                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
            return false;

        } catch (SQLException e) {
            throw new DatabaseException("Error deleting customer: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public List<FlipfitCustomer> getAllCustomers() {
        String sql = "SELECT u.*, c.* FROM flipfit_users u " +
                     "JOIN flipfit_customers c ON u.user_id = c.user_id";

        List<FlipfitCustomer> customers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                customers.add(mapResultSetToCustomer(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting all customers: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return customers;
    }

    @Override
    public int getNextCustomerId() {
        String sql = "SELECT MAX(customer_id) FROM flipfit_customers";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting next customer ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return 1; // Start with 1 if no customers exist
    }

    /**
     * Maps a ResultSet to a FlipfitCustomer object
     * @param resultSet The ResultSet containing customer data
     * @return A FlipfitCustomer object
     * @throws SQLException if a database access error occurs
     */
    private FlipfitCustomer mapResultSetToCustomer(ResultSet resultSet) throws SQLException {
        FlipfitCustomer customer = new FlipfitCustomer();

        // Set User properties
        customer.setUserId(resultSet.getInt("user_id"));
        customer.setEmail(resultSet.getString("email"));
        customer.setPhoneNumber(resultSet.getString("phone_number"));
        customer.setPassword(resultSet.getString("password"));

        // Set Customer properties
        customer.setCustomerId(resultSet.getInt("customer_id"));
        customer.setName(resultSet.getString("name"));

        return customer;
    }
}
