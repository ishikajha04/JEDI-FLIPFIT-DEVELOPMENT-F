package com.flipfit.business.impl;

import com.flipfit.bean.*;
import com.flipfit.dao.*;
import com.flipfit.dao.impl.*;
import com.flipfit.exception.DatabaseException;
import com.flipfit.business.FlipfitGymOwnerService;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of FlipfitGymOwnerService that uses MySQL DAO implementations
 */
public class FlipfitGymOwnerServiceImpl implements FlipfitGymOwnerService {
    private final FlipfitGymOwnerDAO gymOwnerDAO;
    private final FlipfitGymCenterDAO gymCenterDAO;
    private final FlipfitSlotDAO slotDAO;
    private final FlipfitBookingDAO bookingDAO;

    public FlipfitGymOwnerServiceImpl() {
        this.gymOwnerDAO = new FlipfitGymOwnerDAOImpl();
        this.gymCenterDAO = new FlipfitGymCenterDAOImpl();
        this.slotDAO = new FlipfitSlotDAOImpl();
        this.bookingDAO = new FlipfitBookingDAOImpl();
    }

    @Override
    public boolean registerGymOwner(FlipfitGymOwner owner) {
        try {
            if (owner == null || owner.getEmail() == null || owner.getPassword() == null) {
                System.out.println("Error: Invalid gym owner data provided");
                return false;
            }

            // Check if email already exists
            if (gymOwnerDAO.getGymOwnerByEmail(owner.getEmail()) != null) {
                System.out.println("Error: A gym owner with this email already exists");
                return false;
            }

            boolean result = gymOwnerDAO.addGymOwner(owner);
            if (result) {
                System.out.println("Gym owner registered successfully with ID: " + owner.getOwnerId());
                System.out.println("Your registration is pending approval by an administrator");
            } else {
                System.out.println("Failed to register gym owner");
            }
            return result;
        } catch (DatabaseException e) {
            System.err.println("Database error during gym owner registration: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error during gym owner registration: " + e.getMessage());
            return false;
        }
    }

    @Override
    public FlipfitGymOwner authenticateGymOwner(String email, String password) {
        try {
            FlipfitGymOwner owner = gymOwnerDAO.getGymOwnerByEmail(email);
            if (owner != null && owner.getPassword().equals(password)) {
                return owner;
            }
            System.out.println("Invalid email or password");
            return null;
        } catch (DatabaseException e) {
            System.err.println("Database error during authentication: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error during authentication: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean addGymCenter(FlipfitGymCenter center) {
        try {
            if (center == null) {
                System.out.println("Error: Invalid gym center data provided");
                return false;
            }

            // Verify owner exists and is approved
            FlipfitGymOwner owner = gymOwnerDAO.getGymOwnerById(center.getOwnerId());
            if (owner == null) {
                System.out.println("Error: Gym owner not found");
                return false;
            }

            if (!owner.isApproved()) {
                System.out.println("Error: Your account must be approved before adding gym centers");
                return false;
            }

            boolean result = gymCenterDAO.addGymCenter(center);
            if (result) {
                System.out.println("Gym center added successfully with ID: " + center.getCenterId());
                System.out.println("Your gym center is pending approval by an administrator");
            } else {
                System.out.println("Failed to add gym center");
            }
            return result;
        } catch (DatabaseException e) {
            System.err.println("Database error while adding gym center: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while adding gym center: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateGymCenter(FlipfitGymCenter center) {
        try {
            if (center == null) {
                System.out.println("Error: Invalid gym center data provided");
                return false;
            }

            // Verify ownership
            FlipfitGymCenter existingCenter = gymCenterDAO.getGymCenterById(center.getCenterId());
            if (existingCenter == null) {
                System.out.println("Error: Gym center not found");
                return false;
            }

            if (existingCenter.getOwnerId() != center.getOwnerId()) {
                System.out.println("Error: You don't have permission to update this gym center");
                return false;
            }

            boolean result = gymCenterDAO.updateGymCenter(center);
            if (result) {
                System.out.println("Gym center updated successfully");
            } else {
                System.out.println("Failed to update gym center");
            }
            return result;
        } catch (DatabaseException e) {
            System.err.println("Database error while updating gym center: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while updating gym center: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeGymCenter(int centerId, int ownerId) {
        try {
            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(centerId);
            if (center == null) {
                System.out.println("Error: Gym center not found");
                return false;
            }

            if (center.getOwnerId() != ownerId) {
                System.out.println("Error: You don't have permission to remove this gym center");
                return false;
            }

            boolean result = gymCenterDAO.deleteGymCenter(centerId);
            if (result) {
                System.out.println("Gym center removed successfully");
            } else {
                System.out.println("Failed to remove gym center");
            }
            return result;
        } catch (DatabaseException e) {
            System.err.println("Database error while removing gym center: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while removing gym center: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<FlipfitGymCenter> viewOwnedGymCenters(int ownerId) {
        try {
            List<FlipfitGymCenter> centers = gymCenterDAO.getGymCentersByOwnerId(ownerId);
            if (centers.isEmpty()) {
                System.out.println("You don't have any registered gym centers yet");
            }
            return centers;
        } catch (DatabaseException e) {
            System.err.println("Database error while retrieving gym centers: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Unexpected error while retrieving gym centers: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean addSlotToCenter(FlipfitSlot slot) {
        try {
            if (slot == null) {
                System.out.println("Error: Invalid slot data provided");
                return false;
            }

            // Verify center exists and is approved
            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(slot.getCenterId());
            if (center == null) {
                System.out.println("Error: Gym center not found");
                return false;
            }

            if (!center.isApproved()) {
                System.out.println("Error: Your gym center must be approved before adding slots");
                return false;
            }

            boolean result = slotDAO.addSlot(slot);
            if (result) {
                System.out.println("Slot added successfully to center ID: " + slot.getCenterId());
            } else {
                System.out.println("Failed to add slot");
            }
            return result;
        } catch (DatabaseException e) {
            System.err.println("Database error while adding slot: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while adding slot: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateSlot(FlipfitSlot slot) {
        try {
            if (slot == null) {
                System.out.println("Error: Invalid slot data provided");
                return false;
            }

            // Verify center ownership through existing slot
            FlipfitSlot existingSlot = slotDAO.getSlotById(slot.getSlotId());
            if (existingSlot == null) {
                System.out.println("Error: Slot not found");
                return false;
            }

            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(existingSlot.getCenterId());
            if (center == null) {
                System.out.println("Error: Gym center not found");
                return false;
            }

            boolean result = slotDAO.updateSlot(slot);
            if (result) {
                System.out.println("Slot updated successfully");
            } else {
                System.out.println("Failed to update slot");
            }
            return result;
        } catch (DatabaseException e) {
            System.err.println("Database error while updating slot: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while updating slot: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeSlot(int slotId, int ownerId) {
        try {
            FlipfitSlot slot = slotDAO.getSlotById(slotId);
            if (slot == null) {
                System.out.println("Error: Slot not found");
                return false;
            }

            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(slot.getCenterId());
            if (center == null) {
                System.out.println("Error: Gym center not found");
                return false;
            }

            if (center.getOwnerId() != ownerId) {
                System.out.println("Error: You don't have permission to remove this slot");
                return false;
            }

            boolean result = slotDAO.deleteSlot(slotId);
            if (result) {
                System.out.println("Slot removed successfully");
            } else {
                System.out.println("Failed to remove slot");
            }
            return result;
        } catch (DatabaseException e) {
            System.err.println("Database error while removing slot: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error while removing slot: " + e.getMessage());
            return false;
        }
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
