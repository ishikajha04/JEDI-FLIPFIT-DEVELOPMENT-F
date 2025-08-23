package com.flipfit.restController;

/**
 * Request object for admin login
 */
public class AdminLoginRequest {
    private String email;
    private String password;

    // Default constructor
    public AdminLoginRequest() {}

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
