package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitBooking;
import com.flipfit.dao.FlipfitBookingDAO;
import com.flipfit.exception.DatabaseException;
import com.flipfit.utils.DBUtils;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of FlipfitBookingDAO
 */
public class FlipfitBookingDAOImpl implements FlipfitBookingDAO {

    @Override
    public FlipfitBooking getBookingById(int bookingId) {
        String sql = "SELECT * FROM flipfit_bookings WHERE booking_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, bookingId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToBooking(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting booking by ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return null;
    }

    @Override
    public boolean addBooking(FlipfitBooking booking) {
        String sql = "INSERT INTO flipfit_bookings (customer_id, slot_id, center_id, booking_date, amount, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, booking.getCustomerId());
            statement.setInt(2, booking.getSlotId());
            statement.setInt(3, booking.getCenterId());
            statement.setDate(4, Date.valueOf(booking.getBookingDate()));
            statement.setDouble(5, booking.getAmount());
            statement.setString(6, booking.getStatus().name());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    booking.setBookingId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error adding booking: " + e.getMessage(), e);
        } finally {
            if (generatedKeys != null) {
                DBUtils.closeResultSet(generatedKeys);
            }
            DBUtils.closeResources(statement, connection);
        }

        return false;
    }

    @Override
    public boolean updateBooking(FlipfitBooking booking) {
        String sql = "UPDATE flipfit_bookings SET customer_id = ?, slot_id = ?, center_id = ?, " +
                     "booking_date = ?, amount = ?, status = ? WHERE booking_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, booking.getCustomerId());
            statement.setInt(2, booking.getSlotId());
            statement.setInt(3, booking.getCenterId());
            statement.setDate(4, Date.valueOf(booking.getBookingDate()));
            statement.setDouble(5, booking.getAmount());
            statement.setString(6, booking.getStatus().name());
            statement.setInt(7, booking.getBookingId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error updating booking: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM flipfit_bookings WHERE booking_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, bookingId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting booking: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public List<FlipfitBooking> getAllBookings() {
        String sql = "SELECT * FROM flipfit_bookings";
        List<FlipfitBooking> bookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookings.add(mapResultSetToBooking(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting all bookings: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return bookings;
    }

    @Override
    public List<FlipfitBooking> getBookingsByCustomerId(int customerId) {
        String sql = "SELECT * FROM flipfit_bookings WHERE customer_id = ?";
        List<FlipfitBooking> bookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookings.add(mapResultSetToBooking(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting bookings by customer ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return bookings;
    }

    @Override
    public List<FlipfitBooking> getBookingsBySlotId(int slotId) {
        String sql = "SELECT * FROM flipfit_bookings WHERE slot_id = ?";
        List<FlipfitBooking> bookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, slotId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookings.add(mapResultSetToBooking(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting bookings by slot ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return bookings;
    }

    @Override
    public List<FlipfitBooking> getBookingsByDate(LocalDate date) {
        String sql = "SELECT * FROM flipfit_bookings WHERE booking_date = ?";
        List<FlipfitBooking> bookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(date));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookings.add(mapResultSetToBooking(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting bookings by date: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return bookings;
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE flipfit_bookings SET status = 'CANCELLED' WHERE booking_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, bookingId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error cancelling booking: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public int getNextBookingId() {
        String sql = "SELECT MAX(booking_id) FROM flipfit_bookings";
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
            throw new DatabaseException("Error getting next booking ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return 1; // Start with 1 if no bookings exist
    }

    /**
     * Maps a ResultSet to a FlipfitBooking object
     * @param resultSet The ResultSet containing booking data
     * @return A FlipfitBooking object
     * @throws SQLException if a database access error occurs
     */
    private FlipfitBooking mapResultSetToBooking(ResultSet resultSet) throws SQLException {
        FlipfitBooking booking = new FlipfitBooking();
        booking.setBookingId(resultSet.getInt("booking_id"));
        booking.setCustomerId(resultSet.getInt("customer_id"));
        booking.setSlotId(resultSet.getInt("slot_id"));
        booking.setCenterId(resultSet.getInt("center_id"));
        booking.setBookingDate(resultSet.getDate("booking_date").toLocalDate());
        booking.setAmount(resultSet.getDouble("amount"));
        booking.setStatus(FlipfitBooking.BookingStatus.valueOf(resultSet.getString("status")));

        // Set booking time if available
        Timestamp bookingTime = resultSet.getTimestamp("booking_time");
        if (bookingTime != null) {
            booking.setBookingTime(bookingTime.toLocalDateTime());
        }

        return booking;
    }
}
