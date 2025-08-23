package com.flipfit.restController;

import com.flipfit.bean.*;
import com.flipfit.business.FlipfitCustomerService;
import com.flipfit.business.impl.FlipfitCustomerServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for Flipfit Customer operations
 * @author FlipFit Team
 */
@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FlipfitCustomerRestController {

    private final FlipfitCustomerService customerService;

    public FlipfitCustomerRestController() {
        this.customerService = new FlipfitCustomerServiceImpl();
    }

    /**
     * Register a new customer
     * @param customer FlipfitCustomer object with registration details
     * @return Response with success/failure message
     */
    @POST
    @Path("/register")
    public Response registerCustomer(FlipfitCustomer customer) {
        try {
            boolean result = customerService.registerCustomer(customer);
            if (result) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Customer registered successfully\"}")
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
     * Authenticate customer login
     * @param loginRequest Object containing email and password
     * @return Response with customer details or error
     */
    @POST
    @Path("/login")
    public Response loginCustomer(LoginRequest loginRequest) {
        try {
            FlipfitCustomer customer = customerService.authenticateCustomer(
                    loginRequest.getEmail(), loginRequest.getPassword());
            if (customer != null) {
                return Response.ok(customer).build();
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
     * Get all available gym centers
     * @return Response with list of gym centers
     */
    @GET
    @Path("/gym-centers")
    public Response getAvailableGymCenters() {
        try {
            List<FlipfitGymCenter> centers = customerService.viewAvailableGymCenters();
            return Response.ok(centers).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Search gym centers by location
     * @param location Location to search for
     * @return Response with list of gym centers at the location
     */
    @GET
    @Path("/gym-centers/location/{location}")
    public Response getGymCentersByLocation(@PathParam("location") String location) {
        try {
            List<FlipfitGymCenter> centers = customerService.viewGymCentersByLocation(location);
            return Response.ok(centers).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get available slots for a center on a specific day
     * @param centerId ID of the gym center
     * @param day Day of the week
     * @return Response with list of available slots
     */
    @GET
    @Path("/slots/{centerId}/{day}")
    public Response getAvailableSlots(@PathParam("centerId") int centerId,
                                    @PathParam("day") String day) {
        try {
            List<FlipfitSlot> slots = customerService.viewAvailableSlots(centerId, day);
            return Response.ok(slots).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Book a slot for a customer
     * @param bookingRequest Object containing booking details
     * @return Response with booking details or error
     */
    @POST
    @Path("/book-slot")
    public Response bookSlot(BookingRequest bookingRequest) {
        try {
            FlipfitBooking booking = customerService.bookSlot(
                    bookingRequest.getCustomerId(),
                    bookingRequest.getSlotId(),
                    LocalDate.parse(bookingRequest.getBookingDate()));
            if (booking != null) {
                return Response.status(Response.Status.CREATED).entity(booking).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Booking failed\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Cancel a booking
     * @param bookingId ID of the booking to cancel
     * @param customerId ID of the customer
     * @return Response with success/failure message
     */
    @DELETE
    @Path("/bookings/{bookingId}/customer/{customerId}")
    public Response cancelBooking(@PathParam("bookingId") int bookingId,
                                @PathParam("customerId") int customerId) {
        try {
            boolean result = customerService.cancelBooking(bookingId, customerId);
            if (result) {
                return Response.ok("{\"message\": \"Booking cancelled successfully\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Cancellation failed\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get all bookings for a customer
     * @param customerId ID of the customer
     * @return Response with list of bookings
     */
    @GET
    @Path("/bookings/{customerId}")
    public Response getCustomerBookings(@PathParam("customerId") int customerId) {
        try {
            List<FlipfitBooking> bookings = customerService.viewBookings(customerId);
            return Response.ok(bookings).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get bookings for a customer on a specific date
     * @param customerId ID of the customer
     * @param date Date to filter bookings (YYYY-MM-DD format)
     * @return Response with list of bookings for the date
     */
    @GET
    @Path("/bookings/{customerId}/date/{date}")
    public Response getCustomerBookingsByDate(@PathParam("customerId") int customerId,
                                            @PathParam("date") String date) {
        try {
            List<FlipfitBooking> bookings = customerService.viewBookingsByDate(
                    customerId, LocalDate.parse(date));
            return Response.ok(bookings).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Get customer profile
     * @param customerId ID of the customer
     * @return Response with customer profile
     */
    @GET
    @Path("/profile/{customerId}")
    public Response getCustomerProfile(@PathParam("customerId") int customerId) {
        try {
            FlipfitCustomer customer = customerService.getCustomerProfile(customerId);
            if (customer != null) {
                return Response.ok(customer).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Customer not found\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Update customer profile
     * @param customer FlipfitCustomer object with updated details
     * @return Response with success/failure message
     */
    @PUT
    @Path("/profile")
    public Response updateCustomerProfile(FlipfitCustomer customer) {
        try {
            boolean result = customerService.updateCustomerProfile(customer);
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

    /**
     * Add to waitlist
     * @param waitlistRequest Object containing waitlist details
     * @return Response with waitlist booking details
     */
    @POST
    @Path("/waitlist")
    public Response addToWaitlist(WaitlistRequest waitlistRequest) {
        try {
            FlipfitBooking booking = customerService.addToWaitlist(
                    waitlistRequest.getCustomerId(),
                    waitlistRequest.getSlotId(),
                    LocalDate.parse(waitlistRequest.getBookingDate()));
            if (booking != null) {
                return Response.status(Response.Status.CREATED).entity(booking).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Failed to add to waitlist\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Add payment card
     * @param card FlipfitCard object with card details
     * @return Response with success/failure message
     */
    @POST
    @Path("/payment/card")
    public Response addCard(FlipfitCard card) {
        try {
            boolean result = customerService.addCard(card);
            if (result) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Card added successfully\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Failed to add card\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
