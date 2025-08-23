package com.flipfit.bean;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Flipfit Team
 * @description Represents a waitlist for a slot, managing customer IDs in the queue.
 */
public class FlipfitWaitlist {
    private int waitlistId;
    private int slotId;
    private Queue<Integer> customerIds;

    /**
     * @method FlipfitWaitlist
     * @description Default constructor for FlipfitWaitlist.
     */
    public FlipfitWaitlist() {
        this.customerIds = new LinkedList<>();
    }

    /**
     * @method getWaitlistId
     * @description Gets the waitlist ID.
     * @return The unique waitlist ID.
     */
    public int getWaitlistId() {
        return waitlistId;
    }

    /**
     * @method setWaitlistId
     * @parameter waitlistId The unique waitlist ID to set.
     * @description Sets the waitlist ID.
     */
    public void setWaitlistId(int waitlistId) {
        this.waitlistId = waitlistId;
    }

    /**
     * @method getSlotId
     * @description Gets the slot ID associated with the waitlist.
     * @return The slot ID.
     */
    public int getSlotId() {
        return slotId;
    }

    /**
     * @method setSlotId
     * @parameter slotId The slot ID to set.
     * @description Sets the slot ID for the waitlist.
     */
    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    /**
     * @method getCustomerIds
     * @description Gets the queue of customer IDs in the waitlist.
     * @return The queue of customer IDs.
     */
    public Queue<Integer> getCustomerIds() {
        return customerIds;
    }

    /**
     * @method setCustomerIds
     * @parameter customerIds The queue of customer IDs to set.
     * @description Sets the queue of customer IDs for the waitlist.
     */
    public void setCustomerIds(Queue<Integer> customerIds) {
        this.customerIds = customerIds;
    }

    /**
     * @method addToWaitlist
     * @parameter userId The user ID to add to the waitlist.
     * @description Adds a user to the waitlist and returns the new size.
     * @return The size of the waitlist after addition.
     */
    public int addToWaitlist(int userId) {
        this.customerIds.add(userId);
        return this.customerIds.size();
    }

    /**
     * @method getNextCustomer
     * @description Retrieves and removes the next customer from the waitlist.
     * @return The user ID of the next customer, or null if empty.
     */
    public Integer getNextCustomer() {
        return this.customerIds.poll();
    }

    /**
     * @method removeFromWaitlist
     * @parameter userId The user ID to remove from the waitlist.
     * @description Removes a user from the waitlist.
     * @return True if the user was removed, false otherwise.
     */
    public boolean removeFromWaitlist(int userId) {
        return this.customerIds.remove(userId);
    }
}