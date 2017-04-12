package com.ueehome;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by TangWei on 2017/3/29.
 * 数据库管理
 */
public class DBManager {
    private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

    public static void init(String url) throws PropertyVetoException {
        comboPooledDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        comboPooledDataSource.setJdbcUrl(url);
    }

    public static Connection getConection() throws SQLException {
        return comboPooledDataSource.getConnection();
    }
}
