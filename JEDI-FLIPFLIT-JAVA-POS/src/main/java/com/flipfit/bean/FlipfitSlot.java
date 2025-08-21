package com.flipfit.bean;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

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

    public FlipfitSlot() {
        this.waitlist = new LinkedList<>();
    }

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
    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return availableSeats > 0;
    }

    public Queue<Integer> getWaitlist() {
        return waitlist;
    }

    public void setWaitlist(Queue<Integer> waitlist) {
        this.waitlist = waitlist;
    }

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