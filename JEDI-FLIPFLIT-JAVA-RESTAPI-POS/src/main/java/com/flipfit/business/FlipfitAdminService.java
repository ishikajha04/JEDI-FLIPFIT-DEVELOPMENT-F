package com.flipfit.business;

import com.flipfit.bean.*;
import java.util.List;

/**
 * @author Sukhmani
 * @description Service interface for managing administrator operations in the Flipfit system
 */
public interface FlipfitAdminService {
    /**
     * @method authenticateAdmin
     * @parameter email Admin's email address
     * @parameter password Admin's password
     * @return FlipfitAdmin object if authentication successful, null otherwise
     */
    FlipfitAdmin authenticateAdmin(String email, String password);

    /**
     * @method viewPendingGymOwnerRequests
     * @return List of gym owners pending approval
     */
    List<FlipfitGymOwner> viewPendingGymOwnerRequests();

    /**
     * @method approveGymOwner
     * @parameter ownerId ID of the gym owner to approve
     * @return boolean indicating success or failure of approval
     * @exception  if database operation fails
     */
    boolean approveGymOwner(int ownerId);

    /**
     * @method rejectGymOwner
     * @parameter ownerId ID of the gym owner to reject
     * @return boolean indicating success or failure of rejection
     * @exception  if database operation fails
     */
    boolean rejectGymOwner(int ownerId);

    /**
     * @method viewPendingGymCenterRequests
     * @return List of gym centers pending approval
     * @exception  if database operation fails
     */
    List<FlipfitGymCenter> viewPendingGymCenterRequests();

    /**
     * @method approveGymCenter
     * @parameter centerId ID of the gym center to approve
     * @return boolean indicating success or failure of approval
     * @exception  if database operation fails
     */
    boolean approveGymCenter(int centerId);

    /**
     * @method rejectGymCenter
     * @parameter centerId ID of the gym center to reject
     * @return boolean indicating success or failure of rejection
     * @exception  if database operation fails
     */
    boolean rejectGymCenter(int centerId);

    /**
     * @method viewAllGymCenters
     * @return List of all gym centers in the system
     * @exception  if database operation fails
     */
    List<FlipfitGymCenter> viewAllGymCenters();

    /**
     * @method viewAllGymOwners
     * @return List of all gym owners in the system
     * @exception  if database operation fails
     */
    List<FlipfitGymOwner> viewAllGymOwners();

    /**
     * @method viewAllCustomers
     * @return List of all customers in the system
     * @exception  if database operation fails
     */
    List<FlipfitCustomer> viewAllCustomers();
    /**
     * @method getAdminProfile
     * @parameter adminId ID of the admin
     * @return FlipfitAdmin object containing admin profile
     * @exception if database operation fails
     */

    FlipfitGymOwner viewGymOwnerById(int ownerId);

    FlipfitGymCenter viewGymCenterById(int centerId);

    FlipfitCustomer viewCustomerById(int customerId);

    boolean removeGymOwner(int ownerId);

    boolean removeGymCenter(int centerId);

    boolean removeCustomer(int customerId);


    /**
     * @method getAdminProfile
     * @parameter adminId ID of the admin
     * @return FlipfitAdmin object containing admin profile
     * @exception if database operation fails
     */
    FlipfitAdmin getAdminProfile(int adminId);
}
