package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitGymCenter;
import com.flipfit.dao.FlipfitGymCenterDAO;
import com.flipfit.exception.DatabaseException;
import com.flipfit.utils.DBUtils;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of FlipfitGymCenterDAO
 */
public class FlipfitGymCenterDAOImpl implements FlipfitGymCenterDAO {

    @Override
    public FlipfitGymCenter getGymCenterById(int centerId) {
        String sql = "SELECT * FROM flipfit_gym_centers WHERE center_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, centerId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToGymCenter(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting gym center by ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return null;
    }

    @Override
    public boolean addGymCenter(FlipfitGymCenter center) {
        String sql = "INSERT INTO flipfit_gym_centers (owner_id, name, location, address, approval_status) " +
                     "VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, center.getOwnerId());
            statement.setString(2, center.getName());
            statement.setString(3, center.getLocation());
            statement.setString(4, center.getAddress());
            statement.setString(5, center.isApproved() ? "APPROVED" : "PENDING");

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    center.setCenterId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error adding gym center: " + e.getMessage(), e);
        } finally {
            if (generatedKeys != null) {
                DBUtils.closeResultSet(generatedKeys);
            }
            DBUtils.closeResources(statement, connection);
        }

        return false;
    }

    @Override
    public boolean updateGymCenter(FlipfitGymCenter center) {
        String sql = "UPDATE flipfit_gym_centers SET name = ?, location = ?, address = ?, approval_status = ? " +
                     "WHERE center_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, center.getName());
            statement.setString(2, center.getLocation());
            statement.setString(3, center.getAddress());
            statement.setString(4, center.isApproved() ? "APPROVED" : "PENDING");
            statement.setInt(5, center.getCenterId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error updating gym center: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public boolean deleteGymCenter(int centerId) {
        String sql = "DELETE FROM flipfit_gym_centers WHERE center_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, centerId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting gym center: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public List<FlipfitGymCenter> getAllGymCenters() {
        String sql = "SELECT * FROM flipfit_gym_centers";
        List<FlipfitGymCenter> centers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                centers.add(mapResultSetToGymCenter(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting all gym centers: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return centers;
    }

    @Override
    public List<FlipfitGymCenter> getGymCentersByOwnerId(int ownerId) {
        String sql = "SELECT * FROM flipfit_gym_centers WHERE owner_id = ?";
        List<FlipfitGymCenter> centers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ownerId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                centers.add(mapResultSetToGymCenter(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting gym centers by owner ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return centers;
    }

    @Override
    public List<FlipfitGymCenter> getGymCentersByLocation(String location) {
        String sql = "SELECT * FROM flipfit_gym_centers WHERE location LIKE ?";
        List<FlipfitGymCenter> centers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + location + "%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                centers.add(mapResultSetToGymCenter(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting gym centers by location: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return centers;
    }

    @Override
    public List<FlipfitGymCenter> getPendingApprovalCenters() {
        String sql = "SELECT * FROM flipfit_gym_centers WHERE approval_status = 'PENDING'";
        List<FlipfitGymCenter> centers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                centers.add(mapResultSetToGymCenter(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting pending approval gym centers: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return centers;
    }

    @Override
    public List<FlipfitGymCenter> getApprovedGymCenters() {
        String sql = "SELECT * FROM flipfit_gym_centers WHERE approval_status = 'APPROVED'";
        List<FlipfitGymCenter> centers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                centers.add(mapResultSetToGymCenter(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting approved gym centers: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return centers;
    }

    @Override
    public boolean approveGymCenter(int centerId) {
        String sql = "UPDATE flipfit_gym_centers SET approval_status = 'APPROVED' WHERE center_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, centerId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error approving gym center: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public int getNextCenterId() {
        String sql = "SELECT MAX(center_id) FROM flipfit_gym_centers";
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
            throw new DatabaseException("Error getting next center ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return 1; // Start with 1 if no centers exist
    }

    /**
     * Maps a ResultSet to a FlipfitGymCenter object
     * @param resultSet The ResultSet containing gym center data
     * @return A FlipfitGymCenter object
     * @throws SQLException if a database access error occurs
     */
    private FlipfitGymCenter mapResultSetToGymCenter(ResultSet resultSet) throws SQLException {
        FlipfitGymCenter center = new FlipfitGymCenter();
        center.setCenterId(resultSet.getInt("center_id"));
        center.setOwnerId(resultSet.getInt("owner_id"));
        center.setName(resultSet.getString("name"));
        center.setLocation(resultSet.getString("location"));
        center.setAddress(resultSet.getString("address"));
        center.setApproved("APPROVED".equals(resultSet.getString("approval_status")));
        return center;
    }
}
