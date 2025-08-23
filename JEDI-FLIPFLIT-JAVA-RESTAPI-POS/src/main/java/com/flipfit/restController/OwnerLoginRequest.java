package com.flipfit.restController;

/**
 * Request object for gym owner login
 */
public class OwnerLoginRequest {
    private String email;
    private String password;

    // Default constructor
    public OwnerLoginRequest() {}

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
