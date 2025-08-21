package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitAdmin;
import com.flipfit.dao.FlipfitAdminDAO;
import com.flipfit.exception.DatabaseException;
import com.flipfit.utils.DBUtils;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of FlipfitAdminDAO
 */
public class FlipfitAdminDAOImpl implements FlipfitAdminDAO {

    @Override
    public FlipfitAdmin getAdminById(int adminId) {
        String sql = "SELECT u.*, a.* FROM flipfit_users u " +
                     "JOIN flipfit_admins a ON u.user_id = a.user_id " +
                     "WHERE a.admin_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, adminId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToAdmin(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting admin by ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return null;
    }

    @Override
    public FlipfitAdmin getAdminByEmail(String email) {
        String sql = "SELECT u.*, a.* FROM flipfit_users u " +
                     "JOIN flipfit_admins a ON u.user_id = a.user_id " +
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
                return mapResultSetToAdmin(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting admin by email: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return null;
    }

    @Override
    public boolean addAdmin(FlipfitAdmin admin) {
        Connection connection = null;
        PreparedStatement userStatement = null;
        PreparedStatement adminStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // First insert into flipfit_users table
            String userSql = "INSERT INTO flipfit_users (email, password, phone_number, role) " +
                            "VALUES (?, ?, ?, 'ADMIN')";

            userStatement = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, admin.getEmail());
            userStatement.setString(2, admin.getPassword());
            userStatement.setString(3, admin.getPhoneNumber());

            int userRowsAffected = userStatement.executeUpdate();

            if (userRowsAffected > 0) {
                generatedKeys = userStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    admin.setUserId(userId);

                    // Then insert into flipfit_admins table
                    String adminSql = "INSERT INTO flipfit_admins (user_id, name) VALUES (?, ?)";
                    adminStatement = connection.prepareStatement(adminSql, Statement.RETURN_GENERATED_KEYS);
                    adminStatement.setInt(1, userId);
                    adminStatement.setString(2, admin.getName());

                    int adminRowsAffected = adminStatement.executeUpdate();

                    if (adminRowsAffected > 0) {
                        ResultSet adminKeys = adminStatement.getGeneratedKeys();
                        if (adminKeys.next()) {
                            admin.setAdminId(adminKeys.getInt(1));
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
            throw new DatabaseException("Error adding admin: " + e.getMessage(), e);
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
            DBUtils.closeStatement(adminStatement);
            DBUtils.closeConnection(connection);
        }
    }

    @Override
    public boolean updateAdmin(FlipfitAdmin admin) {
        Connection connection = null;
        PreparedStatement userStatement = null;
        PreparedStatement adminStatement = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            // Update flipfit_users table
            String userSql = "UPDATE flipfit_users SET email = ?, password = ?, phone_number = ? WHERE user_id = ?";
            userStatement = connection.prepareStatement(userSql);
            userStatement.setString(1, admin.getEmail());
            userStatement.setString(2, admin.getPassword());
            userStatement.setString(3, admin.getPhoneNumber());
            userStatement.setInt(4, admin.getUserId());

            int userRowsAffected = userStatement.executeUpdate();

            // Update flipfit_admins table
            String adminSql = "UPDATE flipfit_admins SET name = ? WHERE admin_id = ?";
            adminStatement = connection.prepareStatement(adminSql);
            adminStatement.setString(1, admin.getName());
            adminStatement.setInt(2, admin.getAdminId());

            int adminRowsAffected = adminStatement.executeUpdate();

            if (userRowsAffected > 0 && adminRowsAffected > 0) {
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
            throw new DatabaseException("Error updating admin: " + e.getMessage(), e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error resetting auto-commit: " + e.getMessage(), e);
            }
            DBUtils.closeStatement(userStatement);
            DBUtils.closeStatement(adminStatement);
            DBUtils.closeConnection(connection);
        }
    }

    @Override
    public boolean deleteAdmin(int adminId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Get the user_id first
            String getUserIdSql = "SELECT user_id FROM flipfit_admins WHERE admin_id = ?";
            PreparedStatement getUserIdStatement = connection.prepareStatement(getUserIdSql);
            getUserIdStatement.setInt(1, adminId);
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
            throw new DatabaseException("Error deleting admin: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(statement, connection);
        }
    }

    @Override
    public List<FlipfitAdmin> getAllAdmins() {
        String sql = "SELECT u.*, a.* FROM flipfit_users u " +
                     "JOIN flipfit_admins a ON u.user_id = a.user_id";

        List<FlipfitAdmin> admins = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                admins.add(mapResultSetToAdmin(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting all admins: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return admins;
    }

    @Override
    public int getNextAdminId() {
        String sql = "SELECT MAX(admin_id) FROM flipfit_admins";
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
            throw new DatabaseException("Error getting next admin ID: " + e.getMessage(), e);
        } finally {
            DBUtils.closeResources(resultSet, statement, connection);
        }

        return 1; // Start with 1 if no admins exist
    }

    /**
     * Maps a ResultSet to a FlipfitAdmin object
     * @param resultSet The ResultSet containing admin data
     * @return A FlipfitAdmin object
     * @throws SQLException if a database access error occurs
     */
    private FlipfitAdmin mapResultSetToAdmin(ResultSet resultSet) throws SQLException {
        FlipfitAdmin admin = new FlipfitAdmin();

        // Set User properties
        admin.setUserId(resultSet.getInt("user_id"));
        admin.setEmail(resultSet.getString("email"));
        admin.setPhoneNumber(resultSet.getString("phone_number"));
        admin.setPassword(resultSet.getString("password"));

        // Set Admin properties
        admin.setAdminId(resultSet.getInt("admin_id"));
        admin.setName(resultSet.getString("name"));

        return admin;
    }
}
