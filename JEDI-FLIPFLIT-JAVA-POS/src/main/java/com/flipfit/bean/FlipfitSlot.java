package com.flipfit.bean;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Flipfit Team
 * @description Represents a slot for booking at a gym center, including time, capacity, price, and waitlist.
 */
public class FlipfitSlot {
    private int slotId;
    private int centerId;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private int availableSeats;
    private String day;
    private double price;
    private Queue<Integer> waitlist;

    /**
     * @method FlipfitSlot
     * @description Default constructor for FlipfitSlot.
     */
    public FlipfitSlot() {
        this.waitlist = new LinkedList<>();
    }

    /**
     * @method FlipfitSlot
     * @parameter slotId The unique slot ID.
     * @parameter centerId The gym center ID.
     * @parameter startTime The start time of the slot.
     * @parameter endTime The end time of the slot.
     * @parameter capacity The total capacity of the slot.
     * @parameter day The day of the slot.
     * @parameter price The price for booking the slot.
     * @description Constructs a FlipfitSlot with specified details.
     */
    public FlipfitSlot(int slotId, int centerId, LocalTime startTime, LocalTime endTime,
                      int capacity, String day, double price) {
        this.slotId = slotId;
        this.centerId = centerId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.availableSeats = capacity; // Initially all seats are available
        this.day = day;
        this.price = price;
        this.waitlist = new LinkedList<>();
    }

    // Getters and Setters
    /**
     * @method getSlotId
     * @description Gets the slot ID.
     * @return The unique slot ID.
     */
    public int getSlotId() {
        return slotId;
    }

    /**
     * @method setSlotId
     * @parameter slotId The unique slot ID to set.
     * @description Sets the slot ID.
     */
    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    /**
     * @method getCenterId
     * @description Gets the gym center ID.
     * @return The gym center ID.
     */
    public int getCenterId() {
        return centerId;
    }

    /**
     * @method setCenterId
     * @parameter centerId The gym center ID to set.
     * @description Sets the gym center ID.
     */
    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    /**
     * @method getStartTime
     * @description Gets the start time of the slot.
     * @return The start time of the slot.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * @method setStartTime
     * @parameter startTime The start time to set.
     * @description Sets the start time of the slot.
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @method getEndTime
     * @description Gets the end time of the slot.
     * @return The end time of the slot.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * @method setEndTime
     * @parameter endTime The end time to set.
     * @description Sets the end time of the slot.
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * @method getCapacity
     * @description Gets the total capacity of the slot.
     * @return The total capacity of the slot.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @method setCapacity
     * @parameter capacity The total capacity to set.
     * @description Sets the total capacity of the slot.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @method getAvailableSeats
     * @description Gets the number of available seats in the slot.
     * @return The number of available seats.
     */
    public int getAvailableSeats() {
        return availableSeats;
    }

    /**
     * @method setAvailableSeats
     * @parameter availableSeats The number of available seats to set.
     * @description Sets the number of available seats in the slot.
     */
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    /**
     * @method getDay
     * @description Gets the day of the slot.
     * @return The day of the slot.
     */
    public String getDay() {
        return day;
    }

    /**
     * @method setDay
     * @parameter day The day to set.
     * @description Sets the day of the slot.
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * @method getPrice
     * @description Gets the price for booking the slot.
     * @return The price for booking the slot.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @method setPrice
     * @parameter price The price to set.
     * @description Sets the price for booking the slot.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @method isAvailable
     * @description Checks if the slot is available for booking.
     * @return True if the slot is available, false otherwise.
     */
    public boolean isAvailable() {
        return availableSeats > 0;
    }

    /**
     * @method getWaitlist
     * @description Gets the waitlist for the slot.
     * @return The waitlist for the slot.
     */
    public Queue<Integer> getWaitlist() {
        return waitlist;
    }

    /**
     * @method setWaitlist
     * @parameter waitlist The waitlist to set.
     * @description Sets the waitlist for the slot.
     */
    public void setWaitlist(Queue<Integer> waitlist) {
        this.waitlist = waitlist;
    }

    /**
     * @method toString
     * @description Returns a string representation of the FlipfitSlot object.
     * @return A string representation of the FlipfitSlot object.
     */
    @Override
    public String toString() {
        return "FlipfitSlot{" +
                "slotId=" + slotId +
                ", centerId=" + centerId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", capacity=" + capacity +
                ", availableSeats=" + availableSeats +
                ", day='" + day + '\'' +
                ", price=" + price +
                ", waitlistSize=" + waitlist.size() +
                '}';
    }
}