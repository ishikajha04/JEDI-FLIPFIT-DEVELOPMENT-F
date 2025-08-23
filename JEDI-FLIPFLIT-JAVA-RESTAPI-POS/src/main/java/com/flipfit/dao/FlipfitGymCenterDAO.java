package com.flipfit.dao;

import com.flipfit.bean.FlipfitGymCenter;
import java.util.List;

/**
 * @author Flipfit Team
 * @description DAO interface for gym center-related database operations in the Flipfit system.
 */
public interface FlipfitGymCenterDAO {
    /**
     * @method getGymCenterById
     * @parameter centerId The ID of the gym center to retrieve.
     * @description Retrieves a gym center by its ID.
     * @return FlipfitGymCenter object if found, null otherwise.
     */
    FlipfitGymCenter getGymCenterById(int centerId);

    /**
     * @method addGymCenter
     * @parameter center The gym center object to add.
     * @description Adds a new gym center to the database.
     * @return True if added successfully, false otherwise.
     */
    boolean addGymCenter(FlipfitGymCenter center);

    /**
     * @method updateGymCenter
     * @parameter center The gym center object with updated details.
     * @description Updates an existing gym center in the database.
     * @return True if updated successfully, false otherwise.
     */
    boolean updateGymCenter(FlipfitGymCenter center);

    /**
     * @method deleteGymCenter
     * @parameter centerId The ID of the gym center to delete.
     * @description Deletes a gym center from the database.
     * @return True if deleted successfully, false otherwise.
     */
    boolean deleteGymCenter(int centerId);

    /**
     * @method getAllGymCenters
     * @description Retrieves all gym centers from the database.
     * @return List of FlipfitGymCenter objects.
     */
    List<FlipfitGymCenter> getAllGymCenters();

    /**
     * @method getGymCentersByOwnerId
     * @parameter ownerId The owner ID to filter gym centers.
     * @description Retrieves gym centers by owner ID.
     * @return List of FlipfitGymCenter objects for the owner.
     */
    List<FlipfitGymCenter> getGymCentersByOwnerId(int ownerId);

    /**
     * @method getGymCentersByLocation
     * @parameter location The location to filter gym centers.
     * @description Retrieves gym centers by location.
     * @return List of FlipfitGymCenter objects for the location.
     */
    List<FlipfitGymCenter> getGymCentersByLocation(String location);

    /**
     * @method getPendingApprovalCenters
     * @description Retrieves gym centers pending approval.
     * @return List of FlipfitGymCenter objects pending approval.
     */
    List<FlipfitGymCenter> getPendingApprovalCenters();

    /**
     * @method getApprovedGymCenters
     * @description Retrieves approved gym centers.
     * @return List of approved FlipfitGymCenter objects.
     */
    List<FlipfitGymCenter> getApprovedGymCenters();

    /**
     * @method approveGymCenter
     * @parameter centerId The ID of the gym center to approve.
     * @description Approves a gym center in the database.
     * @return True if approved successfully, false otherwise.
     */
    boolean approveGymCenter(int centerId);

    /**
     * @method getNextCenterId
     * @description Retrieves the next available gym center ID for insertion.
     * @return The next gym center ID.
     */
    int getNextCenterId();
}
