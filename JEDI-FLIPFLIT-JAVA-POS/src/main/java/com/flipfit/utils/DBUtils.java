package com.flipfit.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class for database operations and resource management
 */
public class DBUtils {

    /**
     * Closes the ResultSet silently without throwing exceptions
     * @param resultSet The ResultSet to close
     */
    public static void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing ResultSet: " + e.getMessage());
        }
    }

    /**
     * Closes the PreparedStatement silently without throwing exceptions
     * @param statement The PreparedStatement to close
     */
    public static void closeStatement(PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing PreparedStatement: " + e.getMessage());
        }
    }

    /**
     * Closes the Connection silently without throwing exceptions
     * @param connection The Connection to close
     */
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing Connection: " + e.getMessage());
        }
    }
    
    /**
     * Closes all database resources silently
     * @param resultSet The ResultSet to close
     * @param statement The PreparedStatement to close
     * @param connection The Connection to close
     */
    public static void closeResources(ResultSet resultSet, PreparedStatement statement, Connection connection) {
        closeResultSet(resultSet);
        closeStatement(statement);
        closeConnection(connection);
    }
    
    /**
     * Closes statement and connection resources silently
     * @param statement The PreparedStatement to close
     * @param connection The Connection to close
     */
    public static void closeResources(PreparedStatement statement, Connection connection) {
        closeStatement(statement);
        closeConnection(connection);
    }
}
