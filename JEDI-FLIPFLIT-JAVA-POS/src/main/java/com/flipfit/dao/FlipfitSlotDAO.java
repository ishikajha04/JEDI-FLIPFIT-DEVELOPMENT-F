package com.flipfit.dao;

import com.flipfit.bean.FlipfitSlot;
import java.util.List;

public interface FlipfitSlotDAO {
    FlipfitSlot getSlotById(int slotId);
    boolean addSlot(FlipfitSlot slot);
    boolean updateSlot(FlipfitSlot slot);
    boolean deleteSlot(int slotId);
    List<FlipfitSlot> getAllSlots();
    List<FlipfitSlot> getSlotsByCenterId(int centerId);
    List<FlipfitSlot> getAvailableSlotsByCenterAndDay(int centerId, String day);
    boolean updateSlotAvailability(int slotId, int availableSeats);
    int getNextSlotId();
}
