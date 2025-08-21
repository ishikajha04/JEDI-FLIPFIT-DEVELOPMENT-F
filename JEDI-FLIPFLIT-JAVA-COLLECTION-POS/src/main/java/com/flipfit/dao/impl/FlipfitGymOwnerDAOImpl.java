package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitGymOwner;
import com.flipfit.dao.FlipfitGymOwnerDAO;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FlipfitGymOwnerDAOImpl implements FlipfitGymOwnerDAO {
    private static Map<Integer, FlipfitGymOwner> ownerMap = new ConcurrentHashMap<>();
    private static Map<String, FlipfitGymOwner> emailToOwnerMap = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger(1);

    static {
        // Initialize with some dummy data
        FlipfitGymOwner owner1 = new FlipfitGymOwner("Mike Johnson", "mike@gym.com", "1111111111", "owner123", 1);
        owner1.setApproved(true);
        FlipfitGymOwner owner2 = new FlipfitGymOwner("Sarah Wilson", "sarah@fitness.com", "2222222222", "owner456", 2);
        owner2.setApproved(false); // Pending approval

        ownerMap.put(1, owner1);
        ownerMap.put(2, owner2);
        emailToOwnerMap.put("mike@gym.com", owner1);
        emailToOwnerMap.put("sarah@fitness.com", owner2);

        idCounter.set(3);
    }

    @Override
    public FlipfitGymOwner getGymOwnerById(int ownerId) {
        return ownerMap.get(ownerId);
    }

    @Override
    public FlipfitGymOwner getGymOwnerByEmail(String email) {
        return emailToOwnerMap.get(email);
    }

    @Override
    public boolean addGymOwner(FlipfitGymOwner owner) {
        if (owner == null || emailToOwnerMap.containsKey(owner.getEmail())) {
            return false;
        }
        owner.setOwnerId(getNextOwnerId());
        owner.setUserId(owner.getOwnerId());
        ownerMap.put(owner.getOwnerId(), owner);
        emailToOwnerMap.put(owner.getEmail(), owner);
        return true;
    }

    @Override
    public boolean updateGymOwner(FlipfitGymOwner owner) {
        if (owner == null || !ownerMap.containsKey(owner.getOwnerId())) {
            return false;
        }
        FlipfitGymOwner existingOwner = ownerMap.get(owner.getOwnerId());
        emailToOwnerMap.remove(existingOwner.getEmail());

        ownerMap.put(owner.getOwnerId(), owner);
        emailToOwnerMap.put(owner.getEmail(), owner);
        return true;
    }

    @Override
    public boolean deleteGymOwner(int ownerId) {
        FlipfitGymOwner owner = ownerMap.remove(ownerId);
        if (owner != null) {
            emailToOwnerMap.remove(owner.getEmail());
            return true;
        }
        return false;
    }

    @Override
    public List<FlipfitGymOwner> getAllGymOwners() {
        return new ArrayList<>(ownerMap.values());
    }

    @Override
    public List<FlipfitGymOwner> getPendingApprovalOwners() {
        return ownerMap.values().stream()
                .filter(owner -> !owner.isApproved())
                .collect(Collectors.toList());
    }

    @Override
    public boolean approveGymOwner(int ownerId) {
        FlipfitGymOwner owner = ownerMap.get(ownerId);
        if (owner != null) {
            owner.setApproved(true);
            return true;
        }
        return false;
    }

    @Override
    public int getNextOwnerId() {
        return idCounter.getAndIncrement();
    }
}
