package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitGymOwner;
import com.flipfit.dao.FlipfitGymOwnerDAO;
import com.flipfit.exception.DatabaseException;
import com.flipfit.utils.DBUtils;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of FlipfitGymOwnerDAO
 */
public class FlipfitGymOwnerDAOImpl implements FlipfitGymOwnerDAO {

    @Override
    public FlipfitGymOwner getGymOwnerById(int ownerId) {
        String sql = "SELECT u.*, o.* FROM flipfit_users u " +
                     "JOIN flipfit_gym_owners o ON u.user_id = o.user_id " +
                     "WHERE o.owner_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ownerId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToGymOwner(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting gym owner by ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return null;
    }

    @Override
    public FlipfitGymOwner getGymOwnerByEmail(String email) {
        String sql = "SELECT u.*, o.* FROM flipfit_users u " +
                     "JOIN flipfit_gym_owners o ON u.user_id = o.user_id " +
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
                return mapResultSetToGymOwner(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting gym owner by email: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return null;
    }

    @Override
    public boolean addGymOwner(FlipfitGymOwner owner) {
        Connection connection = null;
        PreparedStatement userStatement = null;
        PreparedStatement ownerStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // First insert into flipfit_users table
            String userSql = "INSERT INTO flipfit_users (email, password, phone_number, role) " +
                            "VALUES (?, ?, ?, 'GYM_OWNER')";

            userStatement = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, owner.getEmail());
            userStatement.setString(2, owner.getPassword());
            userStatement.setString(3, owner.getPhoneNumber());

            int userRowsAffected = userStatement.executeUpdate();

            if (userRowsAffected > 0) {
                generatedKeys = userStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    owner.setUserId(userId);

                    // Then insert into flipfit_gym_owners table
                    String ownerSql = "INSERT INTO flipfit_gym_owners (user_id, name, pan_id, aadhar_number, verification_status) " +
                                     "VALUES (?, ?, ?, ?, ?)";
                    ownerStatement = connection.prepareStatement(ownerSql, Statement.RETURN_GENERATED_KEYS);
                    ownerStatement.setInt(1, userId);
                    ownerStatement.setString(2, owner.getName());
                    ownerStatement.setString(3, "PENDING"); // Default PAN ID
                    ownerStatement.setString(4, "PENDING"); // Default Aadhar number
                    ownerStatement.setString(5, owner.isApproved() ? "APPROVED" : "PENDING");

                    int ownerRowsAffected = ownerStatement.executeUpdate();

                    if (ownerRowsAffected > 0) {
                        ResultSet ownerKeys = ownerStatement.getGeneratedKeys();
                        if (ownerKeys.next()) {
                            owner.setOwnerId(ownerKeys.getInt(1));
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
            throw new DatabaseException("Error adding gym owner: " + e.getMessage(), e);
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
            DBUtils.closeStatement(ownerStatement);
            DBUtils.closeConnection(connection);
        }
    }

    @Override
    public boolean updateGymOwner(FlipfitGymOwner owner) {
        Connection connection = null;
        PreparedStatement userStatement = null;
        PreparedStatement ownerStatement = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            // Update flipfit_users table
            String userSql = "UPDATE flipfit_users SET email = ?, password = ?, phone_number = ? WHERE user_id = ?";
            userStatement = connection.prepareStatement(userSql);
            userStatement.setString(1, owner.getEmail());
            userStatement.setString(2, owner.getPassword());
            userStatement.setString(3, owner.getPhoneNumber());
            userStatement.setInt(4, owner.getUserId());

            int userRowsAffected = userStatement.executeUpdate();

            // Update flipfit_gym_owners table
            String ownerSql = "UPDATE flipfit_gym_owners SET name = ?, verification_status = ? WHERE owner_id = ?";
            ownerStatement = connection.prepareStatement(ownerSql);
            ownerStatement.setString(1, owner.getName());
            ownerStatement.setString(2, owner.isApproved() ? "APPROVED" : "PENDING");
            ownerStatement.setInt(3, owner.getOwnerId());

            int ownerRowsAffected = ownerStatement.executeUpdate();

            if (userRowsAffected > 0 && ownerRowsAffected > 0) {
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
            throw new DatabaseException("Error updating gym owner: " + e.getMessage(), e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error resetting auto-commit: " + e.getMessage(), e);
            }
            DBUtils.closeStatement(userStatement);
            DBUtils.closeStatement(ownerStatement);
            DBUtils.closeConnection(connection);
        }
    }

    @Override
    public boolean deleteGymOwner(int ownerId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Get the user_id first
            String getUserIdSql = "SELECT user_id FROM flipfit_gym_owners WHERE owner_id = ?";
            PreparedStatement getUserIdStatement = connection.prepareStatement(getUserIdSql);
            getUserIdStatement.setInt(1, ownerId);
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
            throw new DatabaseException("Error deleting gym owner: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public List<FlipfitGymOwner> getAllGymOwners() {
        String sql = "SELECT u.*, o.* FROM flipfit_users u " +
                     "JOIN flipfit_gym_owners o ON u.user_id = o.user_id";

        List<FlipfitGymOwner> owners = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                owners.add(mapResultSetToGymOwner(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting all gym owners: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return owners;
    }

    @Override
    public List<FlipfitGymOwner> getPendingApprovalOwners() {
        String sql = "SELECT u.*, o.* FROM flipfit_users u " +
                     "JOIN flipfit_gym_owners o ON u.user_id = o.user_id " +
                     "WHERE o.verification_status = 'PENDING'";

        List<FlipfitGymOwner> owners = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                owners.add(mapResultSetToGymOwner(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting pending approval gym owners: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return owners;
    }

    @Override
    public boolean approveGymOwner(int ownerId) {
        String sql = "UPDATE flipfit_gym_owners SET verification_status = 'APPROVED' WHERE owner_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ownerId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error approving gym owner: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public int getNextOwnerId() {
        String sql = "SELECT MAX(owner_id) FROM flipfit_gym_owners";
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
            throw new DatabaseException("Error getting next owner ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return 1; // Start with 1 if no owners exist
    }

    /**
     * Maps a ResultSet to a FlipfitGymOwner object
     * @param resultSet The ResultSet containing gym owner data
     * @return A FlipfitGymOwner object
     * @throws SQLException if a database access error occurs
     */
    private FlipfitGymOwner mapResultSetToGymOwner(ResultSet resultSet) throws SQLException {
        FlipfitGymOwner owner = new FlipfitGymOwner();

        // Set User properties
        owner.setUserId(resultSet.getInt("user_id"));
        owner.setEmail(resultSet.getString("email"));
        owner.setPhoneNumber(resultSet.getString("phone_number"));
        owner.setPassword(resultSet.getString("password"));

        // Set Owner properties
        owner.setOwnerId(resultSet.getInt("owner_id"));
        owner.setName(resultSet.getString("name"));
        owner.setApproved("APPROVED".equals(resultSet.getString("verification_status")));

        return owner;
    }
}
