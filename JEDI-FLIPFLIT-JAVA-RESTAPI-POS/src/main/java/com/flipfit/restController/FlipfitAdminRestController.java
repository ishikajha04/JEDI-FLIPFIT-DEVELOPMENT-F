package com.flipfit.restController;

import com.flipfit.bean.*;
import com.flipfit.business.FlipfitAdminService;
import com.flipfit.business.impl.FlipfitAdminServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * REST Controller for Flipfit Admin operations
 * @author FlipFit Team
 * @description Provides REST API endpoints for admin functionalities
 */
@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FlipfitAdminRestController {

    private final FlipfitAdminService adminService;

    public FlipfitAdminRestController() {
        this.adminService = new FlipfitAdminServiceImpl();
    }

    /**
     * Authenticate admin login
     * @param loginRequest Object containing email and password
     * @return Response with admin details or error
     */
    @POST
    @Path("/login")
    public Response loginAdmin(AdminLoginRequest loginRequest) {
        try {
            FlipfitAdmin admin = adminService.authenticateAdmin(
                    loginRequest.getEmail(), loginRequest.getPassword());
            if (admin != null) {
                return Response.ok(admin).build();
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
     * Get all pending gym owner requests
     * @return Response with list of pending gym owners
     */
    @GET
    @Path("/gym-owners/pending")
    public Response getPendingGymOwnerRequests() {
        try {
            List<FlipfitGymOwner> pendingOwners = adminService.viewPendingGymOwnerRequests();
            return Response.ok(pendingOwners).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Approve a gym owner
     * @param ownerId ID of the gym owner to approve
     * @return Response with success/failure message
     */
    @PUT
    @Path("/gym-owners/{ownerId}/approve")
    public Response approveGymOwner(@PathParam("ownerId") int ownerId) {
        try {
            boolean result = adminService.approveGymOwner(ownerId);
            if (result) {
                return Response.ok("{\"message\": \"Gym owner approved successfully\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Failed to approve gym owner\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Reject a gym owner
     * @param ownerId ID of the gym owner to reject
     * @return Response with success/failure message
     */
    @PUT
    @Path("/gym-owners/{ownerId}/reject")
    public Response rejectGymOwner(@PathParam("ownerId") int ownerId) {
        try {
            boolean result = adminService.rejectGymOwner(ownerId);
            if (result) {
                return Response.ok("{\"message\": \"Gym owner rejected successfully\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Failed to reject gym owner\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get all pending gym center requests
     * @return Response with list of pending gym centers
     */
    @GET
    @Path("/gym-centers/pending")
    public Response getPendingGymCenterRequests() {
        try {
            List<FlipfitGymCenter> pendingCenters = adminService.viewPendingGymCenterRequests();
            return Response.ok(pendingCenters).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Approve a gym center
     * @param centerId ID of the gym center to approve
     * @return Response with success/failure message
     */
    @PUT
    @Path("/gym-centers/{centerId}/approve")
    public Response approveGymCenter(@PathParam("centerId") int centerId) {
        try {
            boolean result = adminService.approveGymCenter(centerId);
            if (result) {
                return Response.ok("{\"message\": \"Gym center approved successfully\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Failed to approve gym center\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Reject a gym center
     * @param centerId ID of the gym center to reject
     * @return Response with success/failure message
     */
    @PUT
    @Path("/gym-centers/{centerId}/reject")
    public Response rejectGymCenter(@PathParam("centerId") int centerId) {
        try {
            boolean result = adminService.rejectGymCenter(centerId);
            if (result) {
                return Response.ok("{\"message\": \"Gym center rejected successfully\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Failed to reject gym center\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get all gym centers in the system
     * @return Response with list of all gym centers
     */
    @GET
    @Path("/gym-centers")
    public Response getAllGymCenters() {
        try {
            List<FlipfitGymCenter> centers = adminService.viewAllGymCenters();
            return Response.ok(centers).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get all gym owners in the system
     * @return Response with list of all gym owners
     */
    @GET
    @Path("/gym-owners")
    public Response getAllGymOwners() {
        try {
            List<FlipfitGymOwner> owners = adminService.viewAllGymOwners();
            return Response.ok(owners).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get all customers in the system
     * @return Response with list of all customers
     */
    @GET
    @Path("/customers")
    public Response getAllCustomers() {
        try {
            List<FlipfitCustomer> customers = adminService.viewAllCustomers();
            return Response.ok(customers).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get admin profile
     * @param adminId ID of the admin
     * @return Response with admin profile
     */
    @GET
    @Path("/profile/{adminId}")
    public Response getAdminProfile(@PathParam("adminId") int adminId) {
        try {
            FlipfitAdmin admin = adminService.getAdminProfile(adminId);
            if (admin != null) {
                return Response.ok(admin).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Admin not found\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get system statistics
     * @return Response with system statistics
     */
    @GET
    @Path("/statistics")
    public Response getSystemStatistics() {
        try {
            // This endpoint provides overall system statistics
            List<FlipfitCustomer> customers = adminService.viewAllCustomers();
            List<FlipfitGymOwner> owners = adminService.viewAllGymOwners();
            List<FlipfitGymCenter> centers = adminService.viewAllGymCenters();
            List<FlipfitGymOwner> pendingOwners = adminService.viewPendingGymOwnerRequests();
            List<FlipfitGymCenter> pendingCenters = adminService.viewPendingGymCenterRequests();

            String statistics = String.format(
                "{\"totalCustomers\": %d, \"totalGymOwners\": %d, \"totalGymCenters\": %d, " +
                "\"pendingOwnerRequests\": %d, \"pendingCenterRequests\": %d}",
                customers.size(), owners.size(), centers.size(),
                pendingOwners.size(), pendingCenters.size()
            );

            return Response.ok(statistics).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
