package com.flipfit.bean;

public class FlipfitAdmin extends FlipfitUser{
    private String name;
    private String phoneNumber;
    private int adminId;

    public FlipfitAdmin(String email, String password, String roleName, String name, String phoneNumber, int adminId) {
        super(email, password, roleName);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.adminId = adminId;
    }

    public FlipfitAdmin() {
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

    public void approveCenter(FlipfitGymCenter center) {
    }

    public void defineSlot(FlipfitGymCenter center, FlipfitSlot slot) {
    }

    public void updateOwnerRequest() {
    }
}
