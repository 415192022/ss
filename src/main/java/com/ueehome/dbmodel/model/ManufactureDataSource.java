package com.ueehome.dbmodel.model;

import com.ueehome.apibean.WXDeviceBean;
import com.ueehome.apibean.WXOpenidListBean;
import com.ueehome.dbmodel.bean.*;
import com.ueehome.logic.bean.QQIOTBean;
import com.ueehome.logic.bean.UEEIOTBean;
import com.ueehome.logic.bean.WXIOTBean;

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
    UeeProductData registUEEIOT(String password) throws SQLException;
    WXProductData registWXIOT(String password) throws SQLException;


    ManuAuthData getManuAuthData(String password) throws SQLException;
    UeeManuAuthData getUeeManuAuthData(String password) throws SQLException;
    WXManuAuthData getWXManuAuthData(String password) throws SQLException;

    Boolean importQQIOTLicence(QQIOTBean[] datalist) throws SQLException;
    Boolean importUEEIOTLicence(UEEIOTBean[] datalist) throws SQLException;
    Boolean importWXIOTLicence(WXIOTBean[] datalist) throws SQLException;

    WXSaveTokenData saveWXToken() throws SQLException;

    Boolean wxDeviceBindUnbindDevice(WXDeviceBean wxDeviceBean) throws SQLException;
    WXOpenidListBean wxGetOpenId(String deviceId) throws SQLException;

    void commit(Connection conn, ResultSet rs, Statement stat) throws SQLException;

    void commit(Connection conn, Statement stat) throws SQLException;

}
