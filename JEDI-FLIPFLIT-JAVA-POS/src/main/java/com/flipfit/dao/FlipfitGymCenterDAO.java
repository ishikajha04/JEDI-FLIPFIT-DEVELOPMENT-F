package com.flipfit.dao;

import com.flipfit.bean.FlipfitGymCenter;
import java.util.List;

public interface FlipfitGymCenterDAO {
    FlipfitGymCenter getGymCenterById(int centerId);
    boolean addGymCenter(FlipfitGymCenter center);
    boolean updateGymCenter(FlipfitGymCenter center);
    boolean deleteGymCenter(int centerId);
    List<FlipfitGymCenter> getAllGymCenters();
    List<FlipfitGymCenter> getGymCentersByOwnerId(int ownerId);
    List<FlipfitGymCenter> getGymCentersByLocation(String location);
    List<FlipfitGymCenter> getPendingApprovalCenters();
    List<FlipfitGymCenter> getApprovedGymCenters();
    boolean approveGymCenter(int centerId);
    int getNextCenterId();
}
