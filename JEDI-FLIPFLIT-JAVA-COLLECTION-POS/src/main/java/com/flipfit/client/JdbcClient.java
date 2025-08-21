package com.flipfit.client;

import com.flipfit.business.JdbcBusinessInterface;
import com.flipfit.business.JdbcBusinessOperation;

public class JdbcClient {
    public static void main(String[] args) {
        // JbcBusinessOperation jdbcBusinessOperations = new JbcBusinessOperation();
        JdbcBusinessInterface jdbcBusinessOperations = new JdbcBusinessOperation();

        jdbcBusinessOperations.addUser(11, "admin", "admin@gmail.com");


    }
}
