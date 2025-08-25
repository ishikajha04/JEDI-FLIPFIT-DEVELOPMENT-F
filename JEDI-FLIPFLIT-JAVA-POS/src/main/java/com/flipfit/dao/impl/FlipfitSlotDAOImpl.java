package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitSlot;
import com.flipfit.dao.FlipfitSlotDAO;
import com.flipfit.exception.DatabaseException;
import com.flipfit.utils.DBUtils;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of FlipfitSlotDAO
 */
public class FlipfitSlotDAOImpl implements FlipfitSlotDAO {

    @Override
    public FlipfitSlot getSlotById(int slotId) {
        String sql = "SELECT * FROM flipfit_slots WHERE slot_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, slotId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToSlot(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting slot by ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return null;
    }

    @Override
    public boolean addSlot(FlipfitSlot slot) {
        String sql = "INSERT INTO flipfit_slots (center_id, day, start_time, end_time, capacity, available_seats, price, is_available) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, slot.getCenterId());
            statement.setString(2, slot.getDay());
            statement.setTime(3, Time.valueOf(slot.getStartTime()));
            statement.setTime(4, Time.valueOf(slot.getEndTime()));
            statement.setInt(5, slot.getCapacity());
            statement.setInt(6, slot.getAvailableSeats());
            statement.setDouble(7, slot.getPrice());
            statement.setBoolean(8, true); // Default to available

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    slot.setSlotId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error adding slot: " + e.getMessage(), e);
        } finally {
            if (generatedKeys != null) {
                DBUtils.closeResultSet(generatedKeys);
            }
            DBUtils.closeResources(statement, connection);
        }

        return false;
    }

    @Override
    public boolean updateSlot(FlipfitSlot slot) {
        String sql = "UPDATE flipfit_slots SET day = ?, start_time = ?, end_time = ?, capacity = ?, " +
                     "available_seats = ?, price = ?, is_available = ? WHERE slot_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, slot.getDay());
            statement.setTime(2, Time.valueOf(slot.getStartTime()));
            statement.setTime(3, Time.valueOf(slot.getEndTime()));
            statement.setInt(4, slot.getCapacity());
            statement.setInt(5, slot.getAvailableSeats());
            statement.setDouble(6, slot.getPrice());
            statement.setBoolean(7, slot.isAvailable());
            statement.setInt(8, slot.getSlotId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error updating slot: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public boolean deleteSlot(int slotId) {
        String sql = "DELETE FROM flipfit_slots WHERE slot_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, slotId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting slot: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public List<FlipfitSlot> getAllSlots() {
        String sql = "SELECT * FROM flipfit_slots WHERE is_available = true";
        List<FlipfitSlot> slots = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                slots.add(mapResultSetToSlot(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting all slots: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return slots;
    }

    @Override
    public List<FlipfitSlot> getSlotsByCenterId(int centerId) {
        String sql = "SELECT * FROM flipfit_slots WHERE center_id = ? AND is_available = true";
        List<FlipfitSlot> slots = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, centerId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                slots.add(mapResultSetToSlot(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting slots by center ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return slots;
    }

    @Override
    public List<FlipfitSlot> getAvailableSlotsByCenterAndDay(int centerId, String day) {
        String sql = "SELECT * FROM flipfit_slots WHERE center_id = ? AND day = ? " +
                     "";
        List<FlipfitSlot> slots = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, centerId);
            statement.setString(2, day);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                slots.add(mapResultSetToSlot(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting available slots by center and day: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return slots;
    }

    @Override
    public boolean updateSlotAvailability(int slotId, int availableSeats) {
        String sql = "UPDATE flipfit_slots SET available_seats = ? WHERE slot_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, availableSeats);
            statement.setInt(2, slotId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error updating slot availability: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public int getNextSlotId() {
        String sql = "SELECT MAX(slot_id) FROM flipfit_slots";
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
            throw new DatabaseException("Error getting next slot ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return 1; // Start with 1 if no slots exist
    }

    /**
     * Maps a ResultSet to a FlipfitSlot object
     * @param resultSet The ResultSet containing slot data
     * @return A FlipfitSlot object
     * @throws SQLException if a database access error occurs
     */
    private FlipfitSlot mapResultSetToSlot(ResultSet resultSet) throws SQLException {
        FlipfitSlot slot = new FlipfitSlot();
        slot.setSlotId(resultSet.getInt("slot_id"));
        slot.setCenterId(resultSet.getInt("center_id"));
        slot.setDay(resultSet.getString("day"));
        slot.setStartTime(resultSet.getTime("start_time").toLocalTime());
        slot.setEndTime(resultSet.getTime("end_time").toLocalTime());
        slot.setCapacity(resultSet.getInt("capacity"));
        slot.setAvailableSeats(resultSet.getInt("available_seats"));
        slot.setPrice(resultSet.getDouble("price"));
        return slot;
    }
}
