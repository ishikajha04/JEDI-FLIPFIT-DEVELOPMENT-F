package com.flipfit.bean;

/**
 * Represents a user in the Flipfit system, including basic user details
 * @author Sukhmani
 * @description This class serves as the base class for all user types in the Flipfit system,
 * containing common attributes like name, email, phone number, and password
 */
public class FlipfitUser {
    private int userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;

    /**
     * Default constructor for FlipfitUser
     * @method FlipfitUser
     * @description Creates an empty FlipfitUser object with default values
     */
    public FlipfitUser() {
    }

    /**
     * Constructs a FlipfitUser with specified details (without password)
     * @method FlipfitUser
     * @param name The name of the user
     * @param email The email address of the user
     * @param phoneNumber The phone number of the user
     * @description Creates a new user with basic identity information but no password
     */
    public FlipfitUser(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructs a FlipfitUser with specified details (with password)
     * @method FlipfitUser
     * @param name The name of the user
     * @param email The email address of the user
     * @param phoneNumber The phone number of the user
     * @param password The password for the user account
     * @description Creates a complete user profile with authentication credentials
     */
    public FlipfitUser(String name, String email, String phoneNumber, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    /**
     * Gets the user ID
     * @method getUserId
     * @return The unique identifier for this user
     * @description Retrieves the system-generated unique ID for this user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID
     * @method setUserId
     * @param userId The unique identifier to set for this user
     * @description Updates the user's unique identifier in the system
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's name
     * @method getName
     * @return The name of the user
     * @description Retrieves the full name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name
     * @method setName
     * @param name The new name for the user
     * @description Updates the user's name in the system
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's email address
     * @method getEmail
     * @return The email address of the user
     * @description Retrieves the email address associated with this user account
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address
     * @method setEmail
     * @param email The new email address for the user
     * @description Updates the email address associated with this user account
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's phone number
     * @method getPhoneNumber
     * @return The phone number of the user
     * @description Retrieves the phone number associated with this user account
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the user's phone number
     * @method setPhoneNumber
     * @param phoneNumber The new phone number for the user
     * @description Updates the phone number associated with this user account
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the user's password
     * @method getPassword
     * @return The password for the user account
     * @description Retrieves the password associated with this user account
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password
     * @method setPassword
     * @param password The new password for the user account
     * @description Updates the password associated with this user account
     */
    public void setPassword(String password) {
        this.password = password;
    }
}