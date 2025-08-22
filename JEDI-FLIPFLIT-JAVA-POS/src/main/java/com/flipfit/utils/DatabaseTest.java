package com.flipfit.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Flipfit Team
 * @description Simple test class to verify database connection and table existence in the Flipfit system.
 */
public class DatabaseTest {
    /**
     * @method main
     * @parameter args Command-line arguments.
     * @description Main method to test database connection and list tables.
     * @exception SQLException if a database access error occurs
     */
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            
            System.out.println("Database connection successful!");
            
            // Test query to check if tables exist
            String sql = "SHOW TABLES";
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                System.out.println("Tables in database:");
                while (resultSet.next()) {
                    System.out.println("- " + resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
