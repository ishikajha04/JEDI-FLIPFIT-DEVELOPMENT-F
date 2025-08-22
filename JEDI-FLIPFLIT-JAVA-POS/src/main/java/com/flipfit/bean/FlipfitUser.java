package com.flipfit.bean;

/**
 * @author Flipfit Team
 * @description Represents a user in the Flipfit system, including basic user details.
 */
public class FlipfitUser {
    private int userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;

    /**
     * @method FlipfitUser
     * @description Default constructor for FlipfitUser.
     */
    public FlipfitUser() {
    }

    /**
     * @method FlipfitUser
     * @parameter name The name of the user.
     * @parameter email The email address of the user.
     * @parameter phoneNumber The phone number of the user.
     * @description Constructs a FlipfitUser with specified details (without password).
     */
    public FlipfitUser(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * @method FlipfitUser
     * @parameter name The name of the user.
     * @parameter email The email address of the user.
     * @parameter phoneNumber The phone number of the user.
     * @parameter password The password for the user account.
     * @description Constructs a FlipfitUser with specified details (with password).
     */
    public FlipfitUser(String name, String email, String phoneNumber, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    /**
     * @method getUserId
     * @description Gets the user ID.
     * @return The unique user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @method setUserId
     * @parameter userId The unique ID to be set for the user.
     * @description Sets the user ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @method getName
     * @description Gets the name of the user.
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * @method setName
     * @parameter name The name to be set for the user.
     * @description Sets the name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @method getEmail
     * @description Gets the email address of the user.
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @method setEmail
     * @parameter email The email address to be set for the user.
     * @description Sets the email address of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @method getPhoneNumber
     * @description Gets the phone number of the user.
     * @return The phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @method setPhoneNumber
     * @parameter phoneNumber The phone number to be set for the user.
     * @description Sets the phone number of the user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @method getPassword
     * @description Gets the password of the user.
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @method setPassword
     * @parameter password The password to be set for the user.
     * @description Sets the password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}