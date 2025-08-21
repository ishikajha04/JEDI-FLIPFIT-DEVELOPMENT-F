package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitSlot;
import com.flipfit.dao.FlipfitSlotDAO;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FlipfitSlotDAOImpl implements FlipfitSlotDAO {
    private static Map<Integer, FlipfitSlot> slotMap = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger(1);

    static {
        // Initialize with some dummy data for approved centers
        FlipfitSlot slot1 = new FlipfitSlot(1, 1, LocalTime.of(6, 0), LocalTime.of(7, 0), 20, "Monday", 500.0);
        FlipfitSlot slot2 = new FlipfitSlot(2, 1, LocalTime.of(7, 0), LocalTime.of(8, 0), 20, "Monday", 500.0);
        FlipfitSlot slot3 = new FlipfitSlot(3, 1, LocalTime.of(18, 0), LocalTime.of(19, 0), 25, "Monday", 600.0);
        FlipfitSlot slot4 = new FlipfitSlot(4, 2, LocalTime.of(6, 0), LocalTime.of(7, 0), 15, "Monday", 450.0);
        FlipfitSlot slot5 = new FlipfitSlot(5, 2, LocalTime.of(19, 0), LocalTime.of(20, 0), 15, "Monday", 450.0);

        // Add same slots for other days
        FlipfitSlot slot6 = new FlipfitSlot(6, 1, LocalTime.of(6, 0), LocalTime.of(7, 0), 20, "Tuesday", 500.0);
        FlipfitSlot slot7 = new FlipfitSlot(7, 1, LocalTime.of(18, 0), LocalTime.of(19, 0), 25, "Tuesday", 600.0);

        slotMap.put(1, slot1);
        slotMap.put(2, slot2);
        slotMap.put(3, slot3);
        slotMap.put(4, slot4);
        slotMap.put(5, slot5);
        slotMap.put(6, slot6);
        slotMap.put(7, slot7);

        idCounter.set(8);
    }

    @Override
    public FlipfitSlot getSlotById(int slotId) {
        return slotMap.get(slotId);
    }

    @Override
    public boolean addSlot(FlipfitSlot slot) {
        if (slot == null) {
            return false;
        }
        slot.setSlotId(getNextSlotId());
        slotMap.put(slot.getSlotId(), slot);
        return true;
    }

    @Override
    public boolean updateSlot(FlipfitSlot slot) {
        if (slot == null || !slotMap.containsKey(slot.getSlotId())) {
            return false;
        }
        slotMap.put(slot.getSlotId(), slot);
        return true;
    }

    @Override
    public boolean deleteSlot(int slotId) {
        return slotMap.remove(slotId) != null;
    }

    @Override
    public List<FlipfitSlot> getAllSlots() {
        return new ArrayList<>(slotMap.values());
    }

    @Override
    public List<FlipfitSlot> getSlotsByCenterId(int centerId) {
        return slotMap.values().stream()
                .filter(slot -> slot.getCenterId() == centerId)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlipfitSlot> getAvailableSlotsByCenterAndDay(int centerId, String day) {
        return slotMap.values().stream()
                .filter(slot -> slot.getCenterId() == centerId &&
                               slot.getDay().equalsIgnoreCase(day) &&
                               slot.isAvailable())
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateSlotAvailability(int slotId, int availableSeats) {
        FlipfitSlot slot = slotMap.get(slotId);
        if (slot != null) {
            slot.setAvailableSeats(availableSeats);
            return true;
        }
        return false;
    }

    @Override
    public int getNextSlotId() {
        return idCounter.getAndIncrement();
    }
}
