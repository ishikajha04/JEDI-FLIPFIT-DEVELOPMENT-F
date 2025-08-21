package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitPayment;
import com.flipfit.dao.FlipfitPaymentDAO;
import com.flipfit.exception.DatabaseException;
import com.flipfit.utils.DBUtils;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of FlipfitPaymentDAO
 * This implementation uses a supplementary table for payment methods since
 * the primary payment table is for transaction records
 */
public class FlipfitPaymentDAOImpl implements FlipfitPaymentDAO {

    /**
     * Adds a new payment method for a customer
     */
    @Override
    public boolean addPaymentMethod(FlipfitPayment payment) {
        // We'll use the existing cards table for storing payment methods
        String sql = "INSERT INTO flipfit_cards (customer_id, card_number, card_holder_name, expiry_date, cvv, card_type, is_active) " +
                     "VALUES (?, ?, ?, ?, 'XXX', 'PAYMENT_METHOD', ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, payment.getCustomerId());
            statement.setString(2, "XXXXXXXXXXXX" + payment.getLastFourDigits()); // Masked number with last 4 digits
            statement.setString(3, payment.getCardHolderName());
            statement.setString(4, payment.getExpiryDate());
            statement.setBoolean(5, payment.getIsActive());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    payment.setPaymentId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error adding payment method: " + e.getMessage(), e);
        } finally {
            if (generatedKeys != null) {
                DBUtils.closeResultSet(generatedKeys);
            }
            DBUtils.closeResources(statement, connection);
        }

        return false;
    }

    /**
     * Gets all active payment methods for a customer
     */
    @Override
    public List<FlipfitPayment> getCustomerPaymentMethods(int customerId) {
        String sql = "SELECT card_id, customer_id, SUBSTRING(card_number, -4) AS last_four_digits, " +
                     "card_holder_name, expiry_date, is_active FROM flipfit_cards " +
                     "WHERE customer_id = ? AND card_type = 'PAYMENT_METHOD' AND is_active = true";

        List<FlipfitPayment> paymentMethods = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                paymentMethods.add(mapResultSetToPaymentMethod(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting customer payment methods: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return paymentMethods;
    }

    /**
     * Deactivates a payment method
     */
    @Override
    public boolean deactivatePaymentMethod(int customerId, String lastFourDigits) {
        String sql = "UPDATE flipfit_cards SET is_active = false " +
                     "WHERE customer_id = ? AND SUBSTRING(card_number, -4) = ? AND card_type = 'PAYMENT_METHOD'";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.setString(2, lastFourDigits);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error deactivating payment method: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    /**
     * Updates the expiry date for a payment method
     */
    @Override
    public boolean updatePaymentMethodExpiry(int customerId, String lastFourDigits, String newExpiryDate) {
        String sql = "UPDATE flipfit_cards SET expiry_date = ? " +
                     "WHERE customer_id = ? AND SUBSTRING(card_number, -4) = ? AND card_type = 'PAYMENT_METHOD'";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, newExpiryDate);
            statement.setInt(2, customerId);
            statement.setString(3, lastFourDigits);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error updating payment method expiry: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    /**
     * Gets a specific payment method by last four digits
     */
    @Override
    public FlipfitPayment getPaymentMethod(int customerId, String lastFourDigits) {
        String sql = "SELECT card_id, customer_id, SUBSTRING(card_number, -4) AS last_four_digits, " +
                     "card_holder_name, expiry_date, is_active FROM flipfit_cards " +
                     "WHERE customer_id = ? AND SUBSTRING(card_number, -4) = ? " +
                     "AND card_type = 'PAYMENT_METHOD' AND is_active = true";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.setString(2, lastFourDigits);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToPaymentMethod(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting payment method: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return null;
    }

    /**
     * Maps a ResultSet to a FlipfitPayment object representing a payment method
     * @param resultSet The ResultSet containing payment method data
     * @return A FlipfitPayment object
     * @throws SQLException if a database access error occurs
     */
    private FlipfitPayment mapResultSetToPaymentMethod(ResultSet resultSet) throws SQLException {
        FlipfitPayment payment = new FlipfitPayment();
        payment.setPaymentId(resultSet.getInt("card_id"));
        payment.setCustomerId(resultSet.getInt("customer_id"));
        payment.setLastFourDigits(resultSet.getString("last_four_digits"));
        payment.setCardHolderName(resultSet.getString("card_holder_name"));
        payment.setExpiryDate(resultSet.getString("expiry_date"));
        payment.setIsActive(resultSet.getBoolean("is_active"));
        return payment;
    }
}
