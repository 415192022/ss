package com.ueehome.dbmodel.model;

import com.ueehome.dbmodel.bean.ManuAuthData;
import com.ueehome.dbmodel.bean.ProductData;
import com.ueehome.logic.bean.QQIOTBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by TangWei on 2017/4/5.
 * ManufactureDataSource
 */
public interface ManufactureDataSource {
    ProductData registQQIOT(String password) throws SQLException;

    ManuAuthData getManuAuthData(String password) throws SQLException;

    Boolean importQQIOTLicence(QQIOTBean[] datalist) throws SQLException;

    void commit(Connection conn, ResultSet rs, Statement stat) throws SQLException;

    void commit(Connection conn, Statement stat) throws SQLException;

}
