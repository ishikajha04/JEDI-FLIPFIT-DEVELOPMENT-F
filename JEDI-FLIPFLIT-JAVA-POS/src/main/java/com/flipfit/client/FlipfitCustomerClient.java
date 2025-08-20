package com.flipfit.client;

import com.flipfit.business.CustomerBusiness;

public class CustomerClient {
    public static void main(String[] args){
        // Create the instance of the customer business
        CustomerBusiness business = new CustomerBusiness();
        business.createCustomer();
        System.out.println("Update Customer"+business.updateCustomer(101));
        System.out.println("Delete Customer"+business.deleteCustomer(102));

    }
}