package com.ueehome.aliiot.model;

import com.ueehome.aliiot.bean.AliBaseBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by uee on 2018/2/7.
 */
public interface AliIotDataSource {
    boolean isAlreadyRegistPhone(String phoneNumber) throws SQLException;
    boolean isAlreadyRegistPhoneName(String phoneName) throws SQLException;
    AliBaseBean registerDevice(String productKey, String deviceName)throws SQLException;
    AliBaseBean registerPhone(String productKey, String phoneNumber, String phoneName)throws SQLException;
    AliBaseBean bindDevice(String phoneName, String deviceName)throws SQLException;
    AliBaseBean unbindDevice(String deviceName)throws SQLException;
    AliBaseBean queryBindedDevice(String phoneName)throws SQLException;
    AliBaseBean queryAllDeviceByPhoneNumber(String phoneNumber)throws SQLException;
    boolean isAlreadyBindDevice(String deviceName)throws SQLException;
    AliBaseBean deviceIsBound(String deviceName)throws SQLException;
    AliBaseBean sharePhone(String masterPhoneName, String sharePhoneName, String deviceName) throws SQLException;
    AliBaseBean cancelSharePhone(String masterPhoneName, String sharePhoneName, String deviceName) throws SQLException;
    AliBaseBean querryPhoneNumber(String phoneName) throws SQLException;
    AliBaseBean querryShareDeviceByPhoneNumber(String phoneNumber) throws SQLException;
    AliBaseBean queryDeviceShareToPhone(String masterPhoneName, String deviceName) throws SQLException;
    boolean isDeviceIsAlreadyShared(String deviceName)throws SQLException;
    boolean deviceOrPhoneIsExsit(String deviceOrPhoneName)throws SQLException;
    List<String> queryMasterPhoneOfDevice(String deviceName) throws SQLException;
    void commit(Connection conn, ResultSet rs, Statement stat) throws SQLException;
    void commit(Connection conn, Statement stat) throws SQLException;

}
