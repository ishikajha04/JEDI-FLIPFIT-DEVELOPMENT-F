package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitAdmin;
import com.flipfit.dao.FlipfitAdminDAO;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FlipfitAdminDAOImpl implements FlipfitAdminDAO {
    private static Map<Integer, FlipfitAdmin> adminMap = new ConcurrentHashMap<>();
    private static Map<String, FlipfitAdmin> emailToAdminMap = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger(1);

    static {
        // Initialize with some dummy admin data
        FlipfitAdmin admin1 = new FlipfitAdmin("Admin User", "admin@flipfit.com", "9999999999", "admin123", 1);

        adminMap.put(1, admin1);
        emailToAdminMap.put("admin@flipfit.com", admin1);

        idCounter.set(2);
    }

    @Override
    public FlipfitAdmin getAdminById(int adminId) {
        return adminMap.get(adminId);
    }

    @Override
    public FlipfitAdmin getAdminByEmail(String email) {
        return emailToAdminMap.get(email);
    }

    @Override
    public boolean addAdmin(FlipfitAdmin admin) {
        if (admin == null || emailToAdminMap.containsKey(admin.getEmail())) {
            return false;
        }
        admin.setAdminId(getNextAdminId());
        admin.setUserId(admin.getAdminId());
        adminMap.put(admin.getAdminId(), admin);
        emailToAdminMap.put(admin.getEmail(), admin);
        return true;
    }

    @Override
    public boolean updateAdmin(FlipfitAdmin admin) {
        if (admin == null || !adminMap.containsKey(admin.getAdminId())) {
            return false;
        }
        FlipfitAdmin existingAdmin = adminMap.get(admin.getAdminId());
        emailToAdminMap.remove(existingAdmin.getEmail());

        adminMap.put(admin.getAdminId(), admin);
        emailToAdminMap.put(admin.getEmail(), admin);
        return true;
    }

    @Override
    public boolean deleteAdmin(int adminId) {
        FlipfitAdmin admin = adminMap.remove(adminId);
        if (admin != null) {
            emailToAdminMap.remove(admin.getEmail());
            return true;
        }
        return false;
    }

    @Override
    public List<FlipfitAdmin> getAllAdmins() {
        return new ArrayList<>(adminMap.values());
    }

    @Override
    public int getNextAdminId() {
        return idCounter.getAndIncrement();
    }
}
