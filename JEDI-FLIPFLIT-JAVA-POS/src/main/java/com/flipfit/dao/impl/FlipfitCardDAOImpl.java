package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitCard;
import com.flipfit.dao.FlipfitCardDAO;
import com.flipfit.exception.DatabaseException;
import com.flipfit.utils.DBUtils;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of FlipfitCardDAO
 */
public class FlipfitCardDAOImpl implements FlipfitCardDAO {

    @Override
    public boolean addCard(FlipfitCard card) {
        String sql = "INSERT INTO flipfit_cards (customer_id, card_number, card_holder_name, expiry_date, cvv, card_type) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, card.getCustomerId());
            statement.setString(2, card.getCardNumber());
            statement.setString(3, card.getCardHolderName());
            statement.setString(4, card.getExpiryDate());
            statement.setString(5, card.getCvv());
            statement.setString(6, "DEBIT"); // Default card type

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    card.setCardId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error adding card: " + e.getMessage(), e);
        } finally {
            if (generatedKeys != null) {
                DBUtils.closeResultSet(generatedKeys);
            }
            DBUtils.closeResources(statement, connection);
        }

        return false;
    }

    @Override
    public boolean removeCard(int cardId, int customerId) {
        String sql = "UPDATE flipfit_cards SET is_active = false WHERE card_id = ? AND customer_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, cardId);
            statement.setInt(2, customerId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error removing card: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public boolean updateCard(FlipfitCard card) {
        String sql = "UPDATE flipfit_cards SET card_holder_name = ?, expiry_date = ? WHERE card_id = ? AND customer_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, card.getCardHolderName());
            statement.setString(2, card.getExpiryDate());
            statement.setInt(3, card.getCardId());
            statement.setInt(4, card.getCustomerId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error updating card: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public List<FlipfitCard> getCustomerCards(int customerId) {
        String sql = "SELECT * FROM flipfit_cards WHERE customer_id = ? AND is_active = true";
        List<FlipfitCard> cards = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                cards.add(mapResultSetToCard(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting customer cards: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return cards;
    }

    /**
     * Maps a ResultSet to a FlipfitCard object
     * @param resultSet The ResultSet containing card data
     * @return A FlipfitCard object
     * @throws SQLException if a database access error occurs
     */
    private FlipfitCard mapResultSetToCard(ResultSet resultSet) throws SQLException {
        FlipfitCard card = new FlipfitCard();
        card.setCardId(resultSet.getInt("card_id"));
        card.setCustomerId(resultSet.getInt("customer_id"));
        card.setCardNumber(resultSet.getString("card_number"));
        card.setCardHolderName(resultSet.getString("card_holder_name"));
        card.setExpiryDate(resultSet.getString("expiry_date"));
        card.setCvv(resultSet.getString("cvv"));
        return card;
    }
}
