package com.flipfit.business;

import com.flipfit.bean.FlipfitAdmin;
import com.flipfit.bean.FlipfitGymOwner;
import com.flipfit.bean.FlipfitGymCenter;
import com.flipfit.bean.FlipfitCustomer;
import java.util.List;

public interface FlipfitAdminService {
    FlipfitAdmin authenticateAdmin(String email, String password);
    List<FlipfitGymOwner> viewPendingGymOwnerRequests();
    boolean approveGymOwner(int ownerId);
    boolean rejectGymOwner(int ownerId);
    List<FlipfitGymCenter> viewPendingGymCenterRequests();
    boolean approveGymCenter(int centerId);
    boolean rejectGymCenter(int centerId);
    List<FlipfitGymCenter> viewAllGymCenters();
    List<FlipfitGymOwner> viewAllGymOwners();
    List<FlipfitCustomer> viewAllCustomers();
    FlipfitAdmin getAdminProfile(int adminId);
}
