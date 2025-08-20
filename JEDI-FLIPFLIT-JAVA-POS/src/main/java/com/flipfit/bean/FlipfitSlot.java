package com.flipfit.bean;

import java.util.Date;

public class Slot {
    private int slotId;
    private int startTime;
    private int seatCapacity;
    private Date date;
    private int centerId;
    private boolean isDisabled;

    public Slot() {
    }

    public Slot(int slotId, int startTime, int seatCapacity, Date date, int centerId, boolean isDisabled) {
        this.slotId = slotId;
        this.startTime = startTime;
        this.seatCapacity = seatCapacity;
        this.date = date;
        this.centerId = centerId;
        this.isDisabled = isDisabled;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public boolean isSlotAvailable() {
        return false;
    }

    public boolean bookSlot() {
        return false;
    }
}