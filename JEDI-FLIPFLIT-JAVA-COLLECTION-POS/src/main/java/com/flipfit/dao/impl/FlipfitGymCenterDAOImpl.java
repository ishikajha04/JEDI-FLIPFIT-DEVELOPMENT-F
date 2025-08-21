package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitGymCenter;
import com.flipfit.dao.FlipfitGymCenterDAO;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FlipfitGymCenterDAOImpl implements FlipfitGymCenterDAO {
    private static Map<Integer, FlipfitGymCenter> centerMap = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger(1);

    static {
        // Initialize with some dummy data
        FlipfitGymCenter center1 = new FlipfitGymCenter(1, 1, "PowerFit Gym", "Downtown", "123 Main St");
        center1.setApproved(true);
        FlipfitGymCenter center2 = new FlipfitGymCenter(2, 1, "FitZone", "Uptown", "456 Oak Ave");
        center2.setApproved(true);
        FlipfitGymCenter center3 = new FlipfitGymCenter(3, 2, "StrengthHub", "Midtown", "789 Pine Rd");
        center3.setApproved(false); // Pending approval

        centerMap.put(1, center1);
        centerMap.put(2, center2);
        centerMap.put(3, center3);

        idCounter.set(4);
    }

    @Override
    public FlipfitGymCenter getGymCenterById(int centerId) {
        return centerMap.get(centerId);
    }

    @Override
    public boolean addGymCenter(FlipfitGymCenter center) {
        if (center == null) {
            return false;
        }
        center.setCenterId(getNextCenterId());
        centerMap.put(center.getCenterId(), center);
        return true;
    }

    @Override
    public boolean updateGymCenter(FlipfitGymCenter center) {
        if (center == null || !centerMap.containsKey(center.getCenterId())) {
            return false;
        }
        centerMap.put(center.getCenterId(), center);
        return true;
    }

    @Override
    public boolean deleteGymCenter(int centerId) {
        return centerMap.remove(centerId) != null;
    }

    @Override
    public List<FlipfitGymCenter> getAllGymCenters() {
        return new ArrayList<>(centerMap.values());
    }

    @Override
    public List<FlipfitGymCenter> getGymCentersByOwnerId(int ownerId) {
        return centerMap.values().stream()
                .filter(center -> center.getOwnerId() == ownerId)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlipfitGymCenter> getGymCentersByLocation(String location) {
        return centerMap.values().stream()
                .filter(center -> center.getLocation().equalsIgnoreCase(location))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlipfitGymCenter> getPendingApprovalCenters() {
        return centerMap.values().stream()
                .filter(center -> !center.isApproved())
                .collect(Collectors.toList());
    }

    @Override
    public List<FlipfitGymCenter> getApprovedGymCenters() {
        return centerMap.values().stream()
                .filter(FlipfitGymCenter::isApproved)
                .collect(Collectors.toList());
    }

    @Override
    public boolean approveGymCenter(int centerId) {
        FlipfitGymCenter center = centerMap.get(centerId);
        if (center != null) {
            center.setApproved(true);
            return true;
        }
        return false;
    }

    @Override
    public int getNextCenterId() {
        return idCounter.getAndIncrement();
    }
}
