package com.flipfit.business.impl;

import com.flipfit.bean.*;
import com.flipfit.dao.*;
import com.flipfit.dao.impl.*;
import com.flipfit.exception.*;
import com.flipfit.business.FlipfitGymOwnerService;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sukhmani
 * @description Implementation of FlipfitGymOwnerService interface that handles all gym owner operations
 */
public class FlipfitGymOwnerServiceImpl implements FlipfitGymOwnerService {
    private final FlipfitGymOwnerDAO gymOwnerDAO;
    private final FlipfitGymCenterDAO gymCenterDAO;
    private final FlipfitSlotDAO slotDAO;
    private final FlipfitBookingDAO bookingDAO;

    /**
     * @method FlipfitGymOwnerServiceImpl
     * @description Constructor that initializes all required DAO instances
     */
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
                throw new RegistrationNotDoneException("Invalid gym owner data provided");
            }

            // Check if email already exists
            if (gymOwnerDAO.getGymOwnerByEmail(owner.getEmail()) != null) {
                throw new RegistrationNotDoneException("A gym owner with email " + owner.getEmail() + " already exists");
            }

            boolean result = gymOwnerDAO.addGymOwner(owner);
            if (result) {
                System.out.println("Gym owner registered successfully with ID: " + owner.getOwnerId());
                System.out.println("Your registration is pending approval by an administrator");
            } else {
                throw new RegistrationNotDoneException("Failed to register gym owner in the database");
            }
            return result;
        } catch (RegistrationNotDoneException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
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
            if (owner == null) {
                throw new UserNotFoundException("Gym owner with email " + email + " not found");
            }

            if (!owner.getPassword().equals(password)) {
                System.out.println("Invalid password");
                return null;
            }

            return owner;
        } catch (UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
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
                throw new RegistrationNotDoneException("Invalid gym center data provided");
            }

            // Verify owner exists and is approved
            FlipfitGymOwner owner = gymOwnerDAO.getGymOwnerById(center.getOwnerId());
            if (owner == null) {
                throw new UserNotFoundException("Gym owner with ID " + center.getOwnerId() + " not found");
            }

            if (!owner.isApproved()) {
                throw new RegistrationNotDoneException("Your account must be approved before adding gym centers");
            }

            boolean result = gymCenterDAO.addGymCenter(center);
            if (result) {
                System.out.println("Gym center added successfully with ID: " + center.getCenterId());
                System.out.println("Your gym center is pending approval by an administrator");
            } else {
                throw new RegistrationNotDoneException("Failed to add gym center to the database");
            }
            return result;
        } catch (RegistrationNotDoneException | UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error adding gym center: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateGymCenter(FlipfitGymCenter center) {
        try {
            if (center == null) {
                throw new RegistrationNotDoneException("Invalid gym center data provided");
            }

            // Verify center exists
            FlipfitGymCenter existingCenter = gymCenterDAO.getGymCenterById(center.getCenterId());
            if (existingCenter == null) {
                throw new SlotNotFoundException("Gym center with ID " + center.getCenterId() + " not found");
            }

            // Check if the owner owns this center
            if (existingCenter.getOwnerId() != center.getOwnerId()) {
                throw new UserNotFoundException("You do not own this gym center");
            }

            boolean result = gymCenterDAO.updateGymCenter(center);
            if (result) {
                System.out.println("Gym center updated successfully");
            } else {
                throw new DatabaseException("Failed to update gym center in the database");
            }
            return result;
        } catch (SlotNotFoundException | UserNotFoundException | RegistrationNotDoneException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error updating gym center: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean addSlotToCenter(FlipfitSlot slot) {
        try {
            if (slot == null) {
                throw new SlotNotFoundException("Invalid slot data provided");
            }

            // Verify gym center exists and is approved
            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(slot.getCenterId());
            if (center == null) {
                throw new SlotNotFoundException("Gym center with ID " + slot.getCenterId() + " not found");
            }

            if (!center.isApproved()) {
                throw new RegistrationNotDoneException("Gym center must be approved before adding slots");
            }

            boolean result = slotDAO.addSlot(slot);
            if (result) {
                System.out.println("Slot added successfully with ID: " + slot.getSlotId());
            } else {
                throw new SlotNotFoundException("Failed to add slot to the database");
            }
            return result;
        } catch (SlotNotFoundException | UserNotFoundException | RegistrationNotDoneException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error adding slot: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateSlot(FlipfitSlot slot) {
        try {
            if (slot == null) {
                throw new SlotNotFoundException("Invalid slot data provided");
            }

            // Verify slot exists
            FlipfitSlot existingSlot = slotDAO.getSlotById(slot.getSlotId());
            if (existingSlot == null) {
                throw new SlotNotFoundException("Slot with ID " + slot.getSlotId() + " not found");
            }

            // Verify gym center exists
            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(existingSlot.getCenterId());
            if (center == null) {
                throw new SlotNotFoundException("Gym center with ID " + existingSlot.getCenterId() + " not found");
            }

            boolean result = slotDAO.updateSlot(slot);
            if (result) {
                System.out.println("Slot updated successfully");
            } else {
                throw new SlotNotFoundException("Failed to update slot in the database");
            }
            return result;
        } catch (SlotNotFoundException | UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error updating slot: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeSlot(int slotId, int ownerId) {
        try {
            // Verify slot exists
            FlipfitSlot slot = slotDAO.getSlotById(slotId);
            if (slot == null) {
                throw new SlotNotFoundException("Slot with ID " + slotId + " not found");
            }

            // Verify gym center exists
            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(slot.getCenterId());
            if (center == null) {
                throw new SlotNotFoundException("Gym center with ID " + slot.getCenterId() + " not found");
            }

            // Check if the owner owns this center
            if (center.getOwnerId() != ownerId) {
                throw new UserNotFoundException("You do not own this gym center");
            }

            // Check if there are active bookings for this slot
            List<FlipfitBooking> bookings = bookingDAO.getBookingsBySlotId(slotId);
            if (!bookings.isEmpty()) {
                throw new BookingNotConfirmedException("Cannot remove slot with active bookings");
            }

            boolean result = slotDAO.deleteSlot(slotId);
            if (result) {
                System.out.println("Slot removed successfully");
            } else {
                throw new SlotNotFoundException("Failed to remove slot from the database");
            }
            return result;
        } catch (SlotNotFoundException | UserNotFoundException | BookingNotConfirmedException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error removing slot: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeGymCenter(int centerId, int ownerId) {
        try {
            // Verify center exists
            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(centerId);
            if (center == null) {
                throw new SlotNotFoundException("Gym center with ID " + centerId + " not found");
            }

            // Check if the owner owns this center
            if (center.getOwnerId() != ownerId) {
                throw new UserNotFoundException("You do not own this gym center");
            }

            // Check if there are active slots for this center
            List<FlipfitSlot> slots = slotDAO.getSlotsByCenterId(centerId);
            for (FlipfitSlot slot : slots) {
                // Check for bookings on this slot
                List<FlipfitBooking> bookings = bookingDAO.getBookingsBySlotId(slot.getSlotId());
                if (!bookings.isEmpty()) {
                    throw new BookingNotConfirmedException("Cannot remove gym center with active bookings");
                }

                // Remove the slot
                slotDAO.deleteSlot(slot.getSlotId());
            }

            boolean result = gymCenterDAO.deleteGymCenter(centerId);
            if (result) {
                System.out.println("Gym center and associated slots removed successfully");
            } else {
                throw new SlotNotFoundException("Failed to remove gym center from the database");
            }
            return result;
        } catch (SlotNotFoundException | UserNotFoundException | BookingNotConfirmedException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error removing gym center: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<FlipfitGymCenter> viewOwnedGymCenters(int ownerId) {
        try {
            List<FlipfitGymCenter> centers = gymCenterDAO.getGymCentersByOwnerId(ownerId);
            if (centers.isEmpty()) {
                System.out.println("No gym centers found for this owner");
            }
            return centers;
        } catch (Exception e) {
            System.err.println("Unexpected error viewing gym centers: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<FlipfitSlot> viewSlotsForCenter(int centerId, int ownerId) {
        try {
            // Verify gym center exists
            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(centerId);
            if (center == null) {
                throw new SlotNotFoundException("Gym center with ID " + centerId + " not found");
            }

            // Check if the owner owns this center
            if (center.getOwnerId() != ownerId) {
                throw new UserNotFoundException("You do not own this gym center");
            }

            List<FlipfitSlot> slots = slotDAO.getSlotsByCenterId(centerId);
            if (slots.isEmpty()) {
                System.out.println("No slots found for this gym center");
            }
            return slots;
        } catch (SlotNotFoundException | UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Unexpected error viewing slots: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<FlipfitBooking> viewBookingsForCenter(int centerId, int ownerId) {
        try {
            // Verify gym center exists
            FlipfitGymCenter center = gymCenterDAO.getGymCenterById(centerId);
            if (center == null) {
                throw new SlotNotFoundException("Gym center with ID " + centerId + " not found");
            }

            // Check if the owner owns this center
            if (center.getOwnerId() != ownerId) {
                throw new UserNotFoundException("You do not own this gym center");
            }

            // Get all slots for this center
            List<FlipfitSlot> slots = slotDAO.getSlotsByCenterId(centerId);

            // Collect bookings for all slots
            List<FlipfitBooking> allBookings = new ArrayList<>();
            for (FlipfitSlot slot : slots) {
                List<FlipfitBooking> slotBookings = bookingDAO.getBookingsBySlotId(slot.getSlotId());
                allBookings.addAll(slotBookings);
            }

            if (allBookings.isEmpty()) {
                System.out.println("No bookings found for this gym center");
            }
            return allBookings;
        } catch (SlotNotFoundException | UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Unexpected error viewing bookings: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public FlipfitGymOwner getOwnerProfile(int ownerId) {
        try {
            FlipfitGymOwner owner = gymOwnerDAO.getGymOwnerById(ownerId);
            if (owner == null) {
                throw new UserNotFoundException(String.valueOf(ownerId), "Gym Owner");
            }
            return owner;
        } catch (UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error retrieving gym owner profile: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateOwnerProfile(FlipfitGymOwner owner) {
        try {
            if (owner == null) {
                throw new UserNotFoundException("Invalid gym owner data provided");
            }

            // Verify gym owner exists
            FlipfitGymOwner existingOwner = gymOwnerDAO.getGymOwnerById(owner.getOwnerId());
            if (existingOwner == null) {
                throw new UserNotFoundException("Gym owner with ID " + owner.getOwnerId() + " not found");
            }

            boolean result = gymOwnerDAO.updateGymOwner(owner);
            if (result) {
                System.out.println("Gym owner profile updated successfully");
            } else {
                throw new DatabaseException("Failed to update gym owner profile in the database");
            }
            return result;
        } catch (UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error updating gym owner profile: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isOwnerApproved(int ownerId) {
        try {
            FlipfitGymOwner owner = gymOwnerDAO.getGymOwnerById(ownerId);
            if (owner == null) {
                throw new UserNotFoundException(String.valueOf(ownerId), "Gym Owner");
            }
            return owner.isApproved();
        } catch (UserNotFoundException e) {
            String errorMessage = ExceptionHandler.handleException(e);
            System.err.println(errorMessage);
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error checking owner approval status: " + e.getMessage());
            return false;
        }
    }
}
