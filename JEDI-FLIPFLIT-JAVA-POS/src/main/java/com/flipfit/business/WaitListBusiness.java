package com.flipfit.business;

public class WaitListBusiness {
    public void removeFromWaitlist(int waitlistid){
        System.out.println("Removing WL status for id " + waitlistid);
    }
    public void notifyClearUser(int userId){
        System.out.println("Clearing WL status for id " + userId);
    }
    public void updateWaitlistPosition(int waitlistid){
        System.out.println("Updating WL status for id " + waitlistid);
    }
}
