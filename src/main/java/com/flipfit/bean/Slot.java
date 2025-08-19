package com.flipfit.bean;

public class Slot {
    private String slotId;
    private String gymId;
    private String startTime;
    private String endTime;
    private String trainer;
    private int numOfSeats;
    private int numOfSeatsBooked;

    public Slot()
    {

    }

    public Slot(String slotId,String startTime,String endTime,int numOfSeats,String trainer,String gymId)
    {
        this.slotId=slotId;
        this.startTime=startTime;
        this.endTime=endTime;
        this.numOfSeats=numOfSeats;
        this.trainer=trainer;
        this.gymId=gymId;
        this.numOfSeatsBooked=0;
    }
    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setNumOfSeatsBooked(int numOfSeatsBooked)
    {
        this.numOfSeatsBooked=numOfSeatsBooked;
    }

    public int getNumOfSeatsBooked()
    {
        return numOfSeatsBooked;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }
    public String getGymId() {
        return gymId;
    }

    public void setGymId(String gymId) {
        this.gymId = gymId;
    }
}