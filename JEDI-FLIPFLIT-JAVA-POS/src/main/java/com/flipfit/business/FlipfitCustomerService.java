package com.flipfit.business;

public class FlipfitCustomerService {
    public void createCustomer(){
        System.out.println("uCreating Customer");
    }

    public boolean updateCustomer(int id){
        System.out.println("updated customer with id" + id);

        return true;
    }

    public boolean deleteCustomer(int id){
        System.out.println("deleting customer with id" + id);

        return true;
    }


}
