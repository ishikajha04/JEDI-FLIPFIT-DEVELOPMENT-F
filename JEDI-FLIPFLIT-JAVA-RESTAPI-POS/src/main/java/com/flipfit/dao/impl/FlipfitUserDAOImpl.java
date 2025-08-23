package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitUser;
import com.flipfit.dao.FlipfitUserDAO;
import com.flipfit.exception.DatabaseException;
import com.flipfit.utils.DBUtils;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of FlipfitUserDAO
 */
public class FlipfitUserDAOImpl implements FlipfitUserDAO {

    @Override
    public FlipfitUser getUserById(int userId) {
        String sql = "SELECT * FROM flipfit_users WHERE user_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting user by ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return null;
    }

    @Override
    public FlipfitUser getUserByEmail(String email) {
        String sql = "SELECT * FROM flipfit_users WHERE email = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting user by email: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return null;
    }

    @Override
    public boolean addUser(FlipfitUser user) {
        String sql = "INSERT INTO flipfit_users (email, password, phone_number, role) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, "CUSTOMER"); // Default role

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error adding user: " + e.getMessage(), e);
        } finally {
            if (generatedKeys != null) {
                DBUtils.closeResultSet(generatedKeys);
            }
            DBUtils.closeResources(statement, connection);
        }

        return false;
    }

    @Override
    public boolean updateUser(FlipfitUser user) {
        String sql = "UPDATE flipfit_users SET email = ?, password = ?, phone_number = ? WHERE user_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPhoneNumber());
            statement.setInt(4, user.getUserId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error updating user: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM flipfit_users WHERE user_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting user: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public List<FlipfitUser> getAllUsers() {
        String sql = "SELECT * FROM flipfit_users";
        List<FlipfitUser> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting all users: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return users;
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        String sql = "SELECT * FROM flipfit_users WHERE email = ? AND password = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            return resultSet.next(); // If there's a result, authentication succeeded
        } catch (SQLException e) {
            throw new DatabaseException("Error authenticating user: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }
    }

    /**
     * Maps a ResultSet to a FlipfitUser object
     *
     * @param resultSet The ResultSet containing user data
     * @return A FlipfitUser object
     * @throws SQLException if a database access error occurs
     */
    private FlipfitUser mapResultSetToUser(ResultSet resultSet) throws SQLException {
        FlipfitUser user = new FlipfitUser();
        user.setUserId(resultSet.getInt("user_id"));
        // Note: name is in the respective entities (customer, gym_owner, admin) tables
        user.setEmail(resultSet.getString("email"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}
