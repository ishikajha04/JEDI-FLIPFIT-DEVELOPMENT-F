package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitWaitlist;
import com.flipfit.dao.FlipfitWaitlistDAO;
import com.flipfit.exception.DatabaseException;
import com.flipfit.utils.DBUtils;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * MySQL implementation of FlipfitWaitlistDAO
 */
public class FlipfitWaitlistDAOImpl implements FlipfitWaitlistDAO {

    @Override
    public FlipfitWaitlist getWaitlistBySlotId(int slotId) {
        String sql = "SELECT * FROM flipfit_waitlist WHERE slot_id = ? ORDER BY position ASC";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        FlipfitWaitlist waitlist = new FlipfitWaitlist();
        waitlist.setSlotId(slotId);

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, slotId);
            resultSet = statement.executeQuery();

            Queue<Integer> customerIds = new LinkedList<>();
            while (resultSet.next()) {
                // Use the last waitlist ID encountered
                waitlist.setWaitlistId(resultSet.getInt("waitlist_id"));
                customerIds.add(resultSet.getInt("customer_id"));
            }
            waitlist.setCustomerIds(customerIds);

            return waitlist;
        } catch (SQLException e) {
            throw new DatabaseException("Error getting waitlist by slot ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }
    }

    @Override
    public boolean addWaitlist(FlipfitWaitlist waitlist) {
        // In the database, we'll store each customer in the waitlist as a separate row
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = true;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // First, get the current position for this slot
            int position = getCurrentMaxPosition(connection, waitlist.getSlotId());

            // Now insert each customer ID in the queue
            String sql = "INSERT INTO flipfit_waitlist (slot_id, customer_id, position) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);

            Queue<Integer> customerIds = new LinkedList<>(waitlist.getCustomerIds());
            for (Integer customerId : customerIds) {
                statement.setInt(1, waitlist.getSlotId());
                statement.setInt(2, customerId);
                statement.setInt(3, ++position);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected <= 0) {
                    success = false;
                    break;
                }
            }

            if (success) {
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
            throw new DatabaseException("Error adding waitlist: " + e.getMessage(), e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error resetting auto-commit: " + e.getMessage(), e);
            }
            DBUtils.closeResources(statement, connection);
        }
    }

    /**
     * Helper method to get the current maximum position for a given slot
     */
    private int getCurrentMaxPosition(Connection connection, int slotId) throws SQLException {
        String sql = "SELECT MAX(position) FROM flipfit_waitlist WHERE slot_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, slotId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int maxPosition = resultSet.getInt(1);
                return resultSet.wasNull() ? 0 : maxPosition;
            }
            return 0;
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeStatement(statement);
        }
    }

    @Override
    public boolean updateWaitlist(FlipfitWaitlist waitlist) {
        // To update the waitlist, we'll delete the existing entries and add new ones
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        PreparedStatement insertStatement = null;
        boolean success = true;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            // Delete existing entries
            String deleteSql = "DELETE FROM flipfit_waitlist WHERE slot_id = ?";
            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, waitlist.getSlotId());
            deleteStatement.executeUpdate();

            // Now insert new entries
            String insertSql = "INSERT INTO flipfit_waitlist (slot_id, customer_id, position) VALUES (?, ?, ?)";
            insertStatement = connection.prepareStatement(insertSql);

            int position = 0;
            Queue<Integer> customerIds = new LinkedList<>(waitlist.getCustomerIds());
            for (Integer customerId : customerIds) {
                insertStatement.setInt(1, waitlist.getSlotId());
                insertStatement.setInt(2, customerId);
                insertStatement.setInt(3, ++position);

                int rowsAffected = insertStatement.executeUpdate();
                if (rowsAffected <= 0) {
                    success = false;
                    break;
                }
            }

            if (success) {
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
            throw new DatabaseException("Error updating waitlist: " + e.getMessage(), e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error resetting auto-commit: " + e.getMessage(), e);
            }
            DBUtils.closeStatement(deleteStatement);
            DBUtils.closeStatement(insertStatement);
            DBUtils.closeConnection(connection);
        }
    }

    @Override
    public boolean deleteWaitlist(int waitlistId) {
        String sql = "DELETE FROM flipfit_waitlist WHERE waitlist_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, waitlistId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting waitlist entry: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public int getNextWaitlistId() {
        String sql = "SELECT MAX(waitlist_id) FROM flipfit_waitlist";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int maxId = resultSet.getInt(1);
                return resultSet.wasNull() ? 1 : maxId + 1;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting next waitlist ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return 1; // Start with 1 if no waitlist entries exist
    }
}
