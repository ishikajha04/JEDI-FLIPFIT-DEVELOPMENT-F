package com.flipfit.business;

import com.flipfit.utils.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcBusinessOperation implements JdbcBusinessInterface{
    public void addUser(int id , String name, String email) {
        String sql = "INSERT INTO user1 (id, name, email) VALUES (?, ? , ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id );
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
