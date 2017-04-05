package com.ueehome;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by TangWei on 2017/3/29.
 * 数据库管理
 */
class DBManager {
    private static Connection connection;

    static void init(String url) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url);
    }

    static Connection getConection() {
        return connection;
    }
}
