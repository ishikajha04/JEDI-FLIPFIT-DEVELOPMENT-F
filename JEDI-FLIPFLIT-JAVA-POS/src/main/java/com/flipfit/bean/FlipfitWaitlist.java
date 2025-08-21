package com.flipfit.bean;

import java.util.LinkedList;
import java.util.Queue;

public class FlipfitWaitlist {
    private int waitlistId;
    private int slotId;
    private Queue<Integer> customerIds;

    public FlipfitWaitlist() {
        this.customerIds = new LinkedList<>();
    }

    public int getWaitlistId() {
        return waitlistId;
    }

    public void setWaitlistId(int waitlistId) {
        this.waitlistId = waitlistId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public Queue<Integer> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(Queue<Integer> customerIds) {
        this.customerIds = customerIds;
    }

    public int addToWaitlist(int userId) {
        this.customerIds.add(userId);
        return this.customerIds.size();
    }

    public Integer getNextCustomer() {
        return this.customerIds.poll();
    }

    public boolean removeFromWaitlist(int userId) {
        return this.customerIds.remove(userId);
    }
}