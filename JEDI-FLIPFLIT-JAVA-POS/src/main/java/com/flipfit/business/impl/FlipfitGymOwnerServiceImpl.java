package com.flipfit.business.impl;

import com.flipfit.bean.*;
import com.flipfit.dao.*;
import com.flipfit.dao.impl.*;
import com.flipfit.business.FlipfitGymOwnerService;
import java.util.List;

public class FlipfitGymOwnerServiceImpl implements FlipfitGymOwnerService {
    private FlipfitGymOwnerDAO gymOwnerDAO;
    private FlipfitGymCenterDAO gymCenterDAO;
    private FlipfitSlotDAO slotDAO;
    private FlipfitBookingDAO bookingDAO;

    public FlipfitGymOwnerServiceImpl() {
        this.gymOwnerDAO = new FlipfitGymOwnerDAOImpl();
        this.gymCenterDAO = new FlipfitGymCenterDAOImpl();
        this.slotDAO = new FlipfitSlotDAOImpl();
        this.bookingDAO = new FlipfitBookingDAOImpl();
    }

    @Override
    public boolean registerGymOwner(FlipfitGymOwner owner) {
        if (owner == null || owner.getEmail() == null || owner.getPassword() == null) {
            return false;
        }

        // Check if email already exists
        if (gymOwnerDAO.getGymOwnerByEmail(owner.getEmail()) != null) {
            return false;
        }

        return gymOwnerDAO.addGymOwner(owner);
    }

    @Override
    public FlipfitGymOwner authenticateGymOwner(String email, String password) {
        FlipfitGymOwner owner = gymOwnerDAO.getGymOwnerByEmail(email);
        if (owner != null && owner.getPassword().equals(password)) {
            return owner;
        }
        return null;
    }

    @Override
    public boolean addGymCenter(FlipfitGymCenter center) {
        if (center == null) {
            return false;
        }

        // Verify owner exists and is approved
        FlipfitGymOwner owner = gymOwnerDAO.getGymOwnerById(center.getOwnerId());
        if (owner == null || !owner.isApproved()) {
            return false;
        }

        return gymCenterDAO.addGymCenter(center);
    }

    @Override
    public boolean updateGymCenter(FlipfitGymCenter center) {
        if (center == null) {
            return false;
        }

        // Verify ownership
        FlipfitGymCenter existingCenter = gymCenterDAO.getGymCenterById(center.getCenterId());
        if (existingCenter == null || existingCenter.getOwnerId() != center.getOwnerId()) {
            return false;
        }

        return gymCenterDAO.updateGymCenter(center);
    }

    @Override
    public boolean removeGymCenter(int centerId, int ownerId) {
        FlipfitGymCenter center = gymCenterDAO.getGymCenterById(centerId);
        if (center == null || center.getOwnerId() != ownerId) {
            return false;
        }

        return gymCenterDAO.deleteGymCenter(centerId);
    }

    @Override
    public List<FlipfitGymCenter> viewOwnedGymCenters(int ownerId) {
        return gymCenterDAO.getGymCentersByOwnerId(ownerId);
    }

    @Override
    public boolean addSlotToCenter(FlipfitSlot slot) {
        if (slot == null) {
            return false;
        }

        // Verify center exists and is owned by the owner
        FlipfitGymCenter center = gymCenterDAO.getGymCenterById(slot.getCenterId());
        if (center == null || !center.isApproved()) {
            return false;
        }

        return slotDAO.addSlot(slot);
    }

    @Override
    public boolean updateSlot(FlipfitSlot slot) {
        if (slot == null) {
            return false;
        }

        // Verify center ownership through existing slot
        FlipfitSlot existingSlot = slotDAO.getSlotById(slot.getSlotId());
        if (existingSlot == null) {
            return false;
        }

        FlipfitGymCenter center = gymCenterDAO.getGymCenterById(existingSlot.getCenterId());
        if (center == null) {
            return false;
        }

        return slotDAO.updateSlot(slot);
    }

    @Override
    public boolean removeSlot(int slotId, int ownerId) {
        FlipfitSlot slot = slotDAO.getSlotById(slotId);
        if (slot == null) {
            return false;
        }

        FlipfitGymCenter center = gymCenterDAO.getGymCenterById(slot.getCenterId());
        if (center == null || center.getOwnerId() != ownerId) {
            return false;
        }

        return slotDAO.deleteSlot(slotId);
    }

    @Override
    public List<FlipfitSlot> viewSlotsForCenter(int centerId, int ownerId) {
        FlipfitGymCenter center = gymCenterDAO.getGymCenterById(centerId);
        if (center == null || center.getOwnerId() != ownerId) {
            return List.of();
        }

        return slotDAO.getSlotsByCenterId(centerId);
    }

    @Override
    public List<FlipfitBooking> viewBookingsForCenter(int centerId, int ownerId) {
        FlipfitGymCenter center = gymCenterDAO.getGymCenterById(centerId);
        if (center == null || center.getOwnerId() != ownerId) {
            return List.of();
        }

        return bookingDAO.getAllBookings().stream()
                .filter(booking -> booking.getCenterId() == centerId)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public FlipfitGymOwner getOwnerProfile(int ownerId) {
        return gymOwnerDAO.getGymOwnerById(ownerId);
    }

    @Override
    public boolean updateOwnerProfile(FlipfitGymOwner owner) {
        return gymOwnerDAO.updateGymOwner(owner);
    }

    @Override
    public boolean isOwnerApproved(int ownerId) {
        FlipfitGymOwner owner = gymOwnerDAO.getGymOwnerById(ownerId);
        return owner != null && owner.isApproved();
    }
}
