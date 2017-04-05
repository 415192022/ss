package com.ueehome.util;

import java.sql.*;

/**
 * Created by Ryan Wu on 2017/4/5.
 */
public class JDBCUtil {

    static final String DB_URL = "jdbc:mysql://localhost:3306/goisgod?serverTimezone=UTC&characterEncoding=utf-8";
    static final String USER = "root";
    static final String PASS = "alone";

    public void init(String where) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM Users WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, where);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id  = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                Time datetime = rs.getTime("createtime");

                System.out.print("ID: " + id);
                System.out.print(", username: " + username);
                System.out.print(", password: " + password);
                System.out.println(", createtime: " + datetime);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
