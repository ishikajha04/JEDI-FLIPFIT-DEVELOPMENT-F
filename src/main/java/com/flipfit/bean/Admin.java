package com.flipfit.bean;

public class Admin extends User{
    private String name;
    private String phoneNumber;
    private int adminId;

    public Admin(String email, String password, String roleName, String name, String phoneNumber, int adminId) {
        super(email, password, roleName);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.adminId = adminId;
    }

    public Admin() {
        super();
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void approveCenter(GymCenter center) {
    }

    public void defineSlot(GymCenter center, Slot slot) {
    }

    public void updateOwnerRequest() {
    }
}
