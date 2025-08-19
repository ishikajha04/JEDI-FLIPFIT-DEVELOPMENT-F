package com.flipkart.client;

import com.flipkart.business.CustomerBusiness;

public class CustomerClient {
    public static void main(String[] args) {
        // customer b
        CustomerBusiness customerBusiness = new CustomerBusiness();
        customerBusiness.createCustomer(null);
        System.out.println("Customer "+customerBusiness.updateCustomer(null));
        System.out.println("Customer "+customerBusiness.deleteCustomer(null));
        customerBusiness.listCustomer(null);
    }
}
