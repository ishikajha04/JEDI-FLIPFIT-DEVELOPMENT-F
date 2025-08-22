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

    FlipfitGymOwner viewGymOwnerById(int ownerId);

    FlipfitGymCenter viewGymCenterById(int centerId);

    FlipfitCustomer viewCustomerById(int customerId);

    boolean removeGymOwner(int ownerId);

    boolean removeGymCenter(int centerId);

    boolean removeCustomer(int customerId);

    FlipfitAdmin getAdminProfile(int adminId);
}
