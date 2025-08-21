package com.flipfit.dao;

import com.flipfit.bean.FlipfitCustomer;
import java.util.List;

public interface FlipfitCustomerDAO {
    FlipfitCustomer getCustomerById(int customerId);
    FlipfitCustomer getCustomerByEmail(String email);
    boolean addCustomer(FlipfitCustomer customer);
    boolean updateCustomer(FlipfitCustomer customer);
    boolean deleteCustomer(int customerId);
    List<FlipfitCustomer> getAllCustomers();
    int getNextCustomerId();
}

