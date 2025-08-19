package com.flipkart.business;

import com.flipkart.bean.Customer;

public class CustomerBusiness
{
    public void createCustomer(Customer customer)
    {

    }
    public  boolean updateCustomer(Customer customer)
    {
        System.out.println("updateCustomer");
        return true;
    }
    public boolean deleteCustomer(Customer customer)
    {
        return true;
    }
    public Customer listCustomer(Customer customer)
    {
        System.out.println("listCustomer");
        return null;
    }
}
