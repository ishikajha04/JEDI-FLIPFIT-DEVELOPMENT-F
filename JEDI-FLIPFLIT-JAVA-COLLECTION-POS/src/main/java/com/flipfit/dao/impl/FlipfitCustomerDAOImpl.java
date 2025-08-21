package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitCustomer;
import com.flipfit.dao.FlipfitCustomerDAO;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FlipfitCustomerDAOImpl implements FlipfitCustomerDAO {
    private static Map<Integer, FlipfitCustomer> customerMap = new ConcurrentHashMap<>();
    private static Map<String, FlipfitCustomer> emailToCustomerMap = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger(1);

    static {
        // Initialize with some dummy data
        FlipfitCustomer customer1 = new FlipfitCustomer("John Doe", "john@example.com", "1234567890", "password123", 1);
        FlipfitCustomer customer2 = new FlipfitCustomer("Jane Smith", "jane@example.com", "0987654321", "password456", 2);

        customerMap.put(1, customer1);
        customerMap.put(2, customer2);
        emailToCustomerMap.put("john@example.com", customer1);
        emailToCustomerMap.put("jane@example.com", customer2);

        idCounter.set(3);
    }

    @Override
    public FlipfitCustomer getCustomerById(int customerId) {
        return customerMap.get(customerId);
    }

    @Override
    public FlipfitCustomer getCustomerByEmail(String email) {
        return emailToCustomerMap.get(email);
    }

    @Override
    public boolean addCustomer(FlipfitCustomer customer) {
        if (customer == null || emailToCustomerMap.containsKey(customer.getEmail())) {
            return false;
        }
        customer.setCustomerId(getNextCustomerId());
        customer.setUserId(customer.getCustomerId());
        customerMap.put(customer.getCustomerId(), customer);
        emailToCustomerMap.put(customer.getEmail(), customer);
        return true;
    }

    @Override
    public boolean updateCustomer(FlipfitCustomer customer) {
        if (customer == null || !customerMap.containsKey(customer.getCustomerId())) {
            return false;
        }
        FlipfitCustomer existingCustomer = customerMap.get(customer.getCustomerId());
        emailToCustomerMap.remove(existingCustomer.getEmail());

        customerMap.put(customer.getCustomerId(), customer);
        emailToCustomerMap.put(customer.getEmail(), customer);
        return true;
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        FlipfitCustomer customer = customerMap.remove(customerId);
        if (customer != null) {
            emailToCustomerMap.remove(customer.getEmail());
            return true;
        }
        return false;
    }

    @Override
    public List<FlipfitCustomer> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public int getNextCustomerId() {
        return idCounter.getAndIncrement();
    }
}
