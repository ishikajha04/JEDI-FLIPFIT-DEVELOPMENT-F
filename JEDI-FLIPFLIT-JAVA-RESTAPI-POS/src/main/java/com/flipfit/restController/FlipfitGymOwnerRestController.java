package com.flipfit.restController;

import com.flipfit.bean.*;
import com.flipfit.business.FlipfitGymOwnerService;
import com.flipfit.business.impl.FlipfitGymOwnerServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * REST Controller for Flipfit Gym Owner operations
 * @author FlipFit Team
 */
@Path("/gym-owner")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FlipfitGymOwnerRestController {

    private final FlipfitGymOwnerService gymOwnerService;

    public FlipfitGymOwnerRestController() {
        this.gymOwnerService = new FlipfitGymOwnerServiceImpl();
    }

    /**
     * Register a new gym owner
     * @param owner FlipfitGymOwner object with registration details
     * @return Response with success/failure message
     */
    @POST
    @Path("/register")
    public Response registerGymOwner(FlipfitGymOwner owner) {
        try {
            boolean result = gymOwnerService.registerGymOwner(owner);
            if (result) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Gym owner registered successfully. Pending admin approval.\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Registration failed. Email might already exist\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Authenticate gym owner login
     * @param loginRequest Object containing email and password
     * @return Response with gym owner details or error
     */
    @POST
    @Path("/login")
    public Response loginGymOwner(OwnerLoginRequest loginRequest) {
        try {
            FlipfitGymOwner owner = gymOwnerService.authenticateGymOwner(
                    loginRequest.getEmail(), loginRequest.getPassword());
            if (owner != null) {
                return Response.ok(owner).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\": \"Invalid credentials\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Check if gym owner is approved
     * @param ownerId ID of the gym owner
     * @return Response with approval status
     */
    @GET
    @Path("/approval-status/{ownerId}")
    public Response checkApprovalStatus(@PathParam("ownerId") int ownerId) {
        try {
            boolean isApproved = gymOwnerService.isOwnerApproved(ownerId);
            return Response.ok("{\"approved\": " + isApproved + "}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Add a new gym center
     * @param center FlipfitGymCenter object with center details
     * @return Response with success/failure message
     */
    @POST
    @Path("/gym-centers")
    public Response addGymCenter(FlipfitGymCenter center) {
        try {
            boolean result = gymOwnerService.addGymCenter(center);
            if (result) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Gym center added successfully. Pending admin approval.\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Failed to add gym center\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Update an existing gym center
     * @param center FlipfitGymCenter object with updated details
     * @return Response with success/failure message
     */
    @PUT
    @Path("/gym-centers")
    public Response updateGymCenter(FlipfitGymCenter center) {
        try {
            boolean result = gymOwnerService.updateGymCenter(center);
            if (result) {
                return Response.ok("{\"message\": \"Gym center updated successfully\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Failed to update gym center\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Remove a gym center
     * @param centerId ID of the center to remove
     * @param ownerId ID of the owner
     * @return Response with success/failure message
     */
    @DELETE
    @Path("/gym-centers/{centerId}/owner/{ownerId}")
    public Response removeGymCenter(@PathParam("centerId") int centerId,
                                  @PathParam("ownerId") int ownerId) {
        try {
            boolean result = gymOwnerService.removeGymCenter(centerId, ownerId);
            if (result) {
                return Response.ok("{\"message\": \"Gym center removed successfully\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Failed to remove gym center\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get all gym centers owned by a specific owner
     * @param ownerId ID of the gym owner
     * @return Response with list of owned gym centers
     */
    @GET
    @Path("/gym-centers/owner/{ownerId}")
    public Response getOwnedGymCenters(@PathParam("ownerId") int ownerId) {
        try {
            List<FlipfitGymCenter> centers = gymOwnerService.viewOwnedGymCenters(ownerId);
            return Response.ok(centers).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Add a new slot to a gym center
     * @param slot FlipfitSlot object with slot details
     * @return Response with success/failure message
     */
    @POST
    @Path("/slots")
    public Response addSlotToCenter(FlipfitSlot slot) {
        try {
            boolean result = gymOwnerService.addSlotToCenter(slot);
            if (result) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Slot added successfully\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Failed to add slot\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Update an existing slot
     * @param slot FlipfitSlot object with updated details
     * @return Response with success/failure message
     */
    @PUT
    @Path("/slots")
    public Response updateSlot(FlipfitSlot slot) {
        try {
            boolean result = gymOwnerService.updateSlot(slot);
            if (result) {
                return Response.ok("{\"message\": \"Slot updated successfully\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Failed to update slot\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Remove a slot
     * @param slotId ID of the slot to remove
     * @param ownerId ID of the owner
     * @return Response with success/failure message
     */
    @DELETE
    @Path("/slots/{slotId}/owner/{ownerId}")
    public Response removeSlot(@PathParam("slotId") int slotId,
                             @PathParam("ownerId") int ownerId) {
        try {
            boolean result = gymOwnerService.removeSlot(slotId, ownerId);
            if (result) {
                return Response.ok("{\"message\": \"Slot removed successfully\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Failed to remove slot\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get all slots for a specific gym center
     * @param centerId ID of the gym center
     * @param ownerId ID of the owner
     * @return Response with list of slots
     */
    @GET
    @Path("/slots/center/{centerId}/owner/{ownerId}")
    public Response getSlotsForCenter(@PathParam("centerId") int centerId,
                                    @PathParam("ownerId") int ownerId) {
        try {
            List<FlipfitSlot> slots = gymOwnerService.viewSlotsForCenter(centerId, ownerId);
            return Response.ok(slots).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get all bookings for a specific gym center
     * @param centerId ID of the gym center
     * @param ownerId ID of the owner
     * @return Response with list of bookings
     */
    @GET
    @Path("/bookings/center/{centerId}/owner/{ownerId}")
    public Response getBookingsForCenter(@PathParam("centerId") int centerId,
                                       @PathParam("ownerId") int ownerId) {
        try {
            List<FlipfitBooking> bookings = gymOwnerService.viewBookingsForCenter(centerId, ownerId);
            return Response.ok(bookings).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get gym owner profile
     * @param ownerId ID of the gym owner
     * @return Response with owner profile
     */
    @GET
    @Path("/profile/{ownerId}")
    public Response getGymOwnerProfile(@PathParam("ownerId") int ownerId) {
        try {
            FlipfitGymOwner owner = gymOwnerService.getOwnerProfile(ownerId);
            if (owner != null) {
                return Response.ok(owner).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Gym owner not found\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Update gym owner profile
     * @param owner FlipfitGymOwner object with updated details
     * @return Response with success/failure message
     */
    @PUT
    @Path("/profile")
    public Response updateGymOwnerProfile(FlipfitGymOwner owner) {
        try {
            boolean result = gymOwnerService.updateOwnerProfile(owner);
            if (result) {
                return Response.ok("{\"message\": \"Profile updated successfully\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Profile update failed\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
