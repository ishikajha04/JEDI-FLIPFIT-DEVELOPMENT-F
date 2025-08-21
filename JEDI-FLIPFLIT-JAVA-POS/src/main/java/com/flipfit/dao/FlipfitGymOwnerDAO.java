package com.flipfit.dao;

import com.flipfit.bean.FlipfitGymOwner;
import java.util.List;

public interface FlipfitGymOwnerDAO {
    FlipfitGymOwner getGymOwnerById(int ownerId);
    FlipfitGymOwner getGymOwnerByEmail(String email);
    boolean addGymOwner(FlipfitGymOwner owner);
    boolean updateGymOwner(FlipfitGymOwner owner);
    boolean deleteGymOwner(int ownerId);
    List<FlipfitGymOwner> getAllGymOwners();
    List<FlipfitGymOwner> getPendingApprovalOwners();
    boolean approveGymOwner(int ownerId);
    int getNextOwnerId();
}
