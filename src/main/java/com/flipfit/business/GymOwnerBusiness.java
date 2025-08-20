package com.flipfit.business;

public class GymOwnerBusiness {
    public void registerCenter(int id, String centerName){
        System.out.println("Registering center with id" + id);
    }
    public void removeCenter(int id){
        System.out.println("Removing center with id" + id);
    }
    public void viewBooking(int id){
        System.out.println("Viewing booking with id" + id);
    }
}
