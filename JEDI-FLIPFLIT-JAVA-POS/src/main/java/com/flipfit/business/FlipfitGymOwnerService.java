package com.flipfit.business;

import com.flipfit.bean.FlipfitGymOwner;
import com.flipfit.bean.FlipfitGymCenter;
import com.flipfit.bean.FlipfitSlot;
import com.flipfit.bean.FlipfitBooking;
import java.util.List;

public interface FlipfitGymOwnerService {
    boolean registerGymOwner(FlipfitGymOwner owner);
    FlipfitGymOwner authenticateGymOwner(String email, String password);
    boolean addGymCenter(FlipfitGymCenter center);
    boolean updateGymCenter(FlipfitGymCenter center);
    boolean removeGymCenter(int centerId, int ownerId);
    List<FlipfitGymCenter> viewOwnedGymCenters(int ownerId);
    boolean addSlotToCenter(FlipfitSlot slot);
    boolean updateSlot(FlipfitSlot slot);
    boolean removeSlot(int slotId, int ownerId);
    List<FlipfitSlot> viewSlotsForCenter(int centerId, int ownerId);
    List<FlipfitBooking> viewBookingsForCenter(int centerId, int ownerId);
    FlipfitGymOwner getOwnerProfile(int ownerId);
    boolean updateOwnerProfile(FlipfitGymOwner owner);
    boolean isOwnerApproved(int ownerId);
}
