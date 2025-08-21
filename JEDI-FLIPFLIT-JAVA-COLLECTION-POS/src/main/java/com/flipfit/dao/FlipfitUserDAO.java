package com.flipfit.dao;

import com.flipfit.bean.FlipfitUser;
import java.util.List;

public interface FlipfitUserDAO {
    FlipfitUser getUserById(int userId);
    FlipfitUser getUserByEmail(String email);
    boolean addUser(FlipfitUser user);
    boolean updateUser(FlipfitUser user);
    boolean deleteUser(int userId);
    List<FlipfitUser> getAllUsers();
    boolean authenticateUser(String email, String password);
}
