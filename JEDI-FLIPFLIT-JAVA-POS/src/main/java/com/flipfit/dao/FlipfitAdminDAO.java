package com.flipfit.dao;

import com.flipfit.bean.FlipfitAdmin;
import java.util.List;

public interface FlipfitAdminDAO {
    FlipfitAdmin getAdminById(int adminId);
    FlipfitAdmin getAdminByEmail(String email);
    boolean addAdmin(FlipfitAdmin admin);
    boolean updateAdmin(FlipfitAdmin admin);
    boolean deleteAdmin(int adminId);
    List<FlipfitAdmin> getAllAdmins();
    int getNextAdminId();
}
