package com.flipfit.business;

public class SlotBusiness {
    public boolean isSlotAvailable(int id){
        System.out.println("Checking if slot available for id " + id);
        return true;
    }
    public void releaseSlot(int id){
        System.out.println("Releasing slot for id " + id);
    }
    public void bookSlot(int id){
        System.out.println("Booking slot for id " + id);
    }
}
