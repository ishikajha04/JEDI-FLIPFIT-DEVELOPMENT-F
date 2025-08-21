package com.flipfit.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for database connection management
 */
public class DatabaseConnection {
    private static final Properties properties = new Properties();
    private static boolean propertiesLoaded = false;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        if (!propertiesLoaded) {
            try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
                if (input == null) {
                    System.out.println("Sorry, unable to find database.properties");
                    return;
                }

                // Load the properties file
                properties.load(input);

                // Load JDBC driver
                try {
                    Class.forName(properties.getProperty("db.driver"));
                } catch (ClassNotFoundException e) {
                    System.err.println("JDBC Driver not found: " + e.getMessage());
                }

                propertiesLoaded = true;
            } catch (IOException ex) {
                System.err.println("Error loading database properties: " + ex.getMessage());
            }
        }
    }

    /**
     * Get a connection to the database
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        if (!propertiesLoaded) {
            loadProperties();
        }

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.username"); // Corrected from db.user
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}