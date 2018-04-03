package com.ueehome.aliiot.impl;

import com.aliyuncs.iot.model.v20170420.RegistDeviceResponse;
import com.google.gson.Gson;
import com.ueehome.DBManager;
import com.ueehome.aliiot.AliIotImpl;
import com.ueehome.aliiot.IotClient;
import com.ueehome.aliiot.bean.*;
import com.ueehome.aliiot.model.AliIotDataSource;
import com.ueehome.util.AccountValidatorUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by uee on 2018/2/7.
 */
public class AliIotDataSourceImpl implements AliIotDataSource {
    private  Connection conn;
    private  ResultSet rs;
    private  PreparedStatement stat;

    public AliIotDataSourceImpl() throws SQLException {
        conn = DBManager.getConection();
        conn.setAutoCommit(false);
    }


    @Override
    public boolean isAlreadyRegistPhone(String phoneNumber) throws SQLException {
        String sql="SELECT * FROM uee.ali_iot_uee WHERE uee.ali_iot_uee.uee_phone_number='"+phoneNumber+"' FOR UPDATE;";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sql);
        rs = stat.executeQuery();

        if (rs.next()) {
            if(rs.getRow() >0){
                System.out.println("==========设备"+phoneNumber+"已被注册");
                return true;
            }
        }else {
            System.out.println("==========设备"+phoneNumber+"未被注册");
            conn.rollback();
        }
        return false;
    }

    @Override
    public boolean isAlreadyRegistPhoneName(String phoneName) throws SQLException {
        String sql="SELECT * FROM uee.ali_iot_uee WHERE uee.ali_iot_uee.phone_name='"+phoneName+"' FOR UPDATE;";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sql);
        rs = stat.executeQuery();

        if (rs.next()) {
            if(rs.getRow() >0){
                System.out.println("==========设备"+phoneName+"已被注册");
                return true;
            }
        }else {
            System.out.println("==========设备"+phoneName+"未被注册");
            conn.rollback();
        }
        return false;
    }

    @Override
    public AliBaseBean registerDevice(String productKey, String deviceName) throws SQLException {
        String json="";
        AliBaseBean aliBaseBean=new AliBaseBean();
        if(deviceName.contains("device")){
            RegistDeviceResponse registDeviceResponse = AliIotImpl.registDeviceTest(productKey,deviceName);
            json=new Gson().toJson(registDeviceResponse);
            AliIotRegistDevicePhoneBean aliIotRegistDevicePhoneBean=new Gson().fromJson(json,AliIotRegistDevicePhoneBean.class);
            aliBaseBean.setSuccess(true);
            aliBaseBean.setMessage("请求成功");
            List<AliIotRegistDevicePhoneBean> aliIotRegistDevicePhoneBeans=new ArrayList<>();
            aliIotRegistDevicePhoneBeans.add(aliIotRegistDevicePhoneBean);
            aliBaseBean.settList(aliIotRegistDevicePhoneBeans);
        }else{
            aliBaseBean.settList(null);
            aliBaseBean.setMessage("注册设备失败,命名规则不正确");
            aliBaseBean.setSuccess(false);
        }
        return aliBaseBean;
    }

    @Override
    public AliBaseBean registerPhone(String productKey, String phoneNumber, String phoneName) throws SQLException {
        AliBaseBean aliBaseBean=new AliBaseBean();
        String json="";
            if(AccountValidatorUtil.isMobile(phoneNumber)){
                if(phoneName.startsWith("phone")){
                    if(!isAlreadyRegistPhoneName(phoneName)) {
                        if (!isAlreadyRegistPhone(phoneNumber)) {
                            RegistDeviceResponse registDeviceResponse = AliIotImpl.registDeviceTest(productKey,phoneName);
                            System.out.println(registDeviceResponse.getSuccess()+" ====");
                            json=new Gson().toJson(registDeviceResponse);
                            AliIotRegistDevicePhoneBean aliIotRegistDevicePhoneBean=new Gson().fromJson(json,AliIotRegistDevicePhoneBean.class);
                            List<AliIotRegistDevicePhoneBean> aliIotRegistDevicePhoneBeans=new ArrayList<>();
                            aliIotRegistDevicePhoneBeans.add(aliIotRegistDevicePhoneBean);
                            aliBaseBean.settList(aliIotRegistDevicePhoneBeans);
                            if(registDeviceResponse.getSuccess()){
                                        String addPhoneSql="INSERT INTO uee.ali_iot_uee(uee.ali_iot_uee.uee_phone_number,uee.ali_iot_uee.phone_name) VALUES(?,?);";
                                        stat = conn.prepareStatement(addPhoneSql);
                                        stat.setString(1, phoneNumber);
                                        stat.setString(2, phoneName);
                                        if (stat.executeUpdate() == 1) {
                                            commit(conn, stat);
                                        }else {
                                            conn.rollback();
                                        }

                            }else{
                                aliBaseBean.setMessage("注册失败");
                                aliBaseBean.setSuccess(false);
                                commit(conn,rs,stat);
                            }
                            aliBaseBean.setSuccess(true);
                            aliBaseBean.setMessage("请求成功");
                        }else{
                            aliBaseBean.setMessage("该手机号已被注册");
                            aliBaseBean.setSuccess(false);
                            commit(conn,rs,stat);
                        }
                    }else{
                        aliBaseBean.setMessage("该手机名已被注册");
                        aliBaseBean.setSuccess(false);
                        commit(conn,rs,stat);
                    }
                }else{
                    aliBaseBean.setMessage("注册手机失败,命名规则不正确");
                    aliBaseBean.setSuccess(false);
                    commit(conn,rs,stat);
                }
            }else{
                aliBaseBean.setMessage("非法手机号");
                aliBaseBean.setSuccess(false);
                commit(conn,rs,stat);
        }

        return aliBaseBean;
    }


    @Override
    public AliBaseBean bindDevice(String phoneName, String deviceName) throws SQLException {
        AliBaseBean aliBaseBean=new AliBaseBean();
        if(!deviceOrPhoneIsExsit(deviceName)){
            System.out.println("==========设备"+deviceName+"不存在");
            aliBaseBean.setMessage("设备不存在");
            aliBaseBean.setSuccess(false);
            aliBaseBean.settList(null);
            commit(conn,rs,stat);
        }else if(!deviceOrPhoneIsExsit(phoneName)){
            System.out.println("==========手机"+deviceName+"不存在");
            aliBaseBean.setMessage("手机不存在");
            aliBaseBean.setSuccess(false);
            aliBaseBean.settList(null);
            commit(conn,rs,stat);
        }else if(!isAlreadyRegistPhoneName(phoneName)){
            System.out.println("==========手机名"+deviceName+"与手机号未建立关系");
            aliBaseBean.setMessage("手机名与手机号未建立关系");
            aliBaseBean.setSuccess(false);
            aliBaseBean.settList(null);
            commit(conn,rs,stat);
        }else{
            if(!isAlreadyBindDevice(deviceName)){
                String id= UUID.randomUUID().toString();
                long time=System.currentTimeMillis()/1000;
                String addDeviceSql = "INSERT INTO uee.ali_iot_device_bind (uee.ali_iot_device_bind.phone_name, uee.ali_iot_device_bind.device_name,uee.ali_iot_device_bind.isshare_phone,uee.ali_iot_device_bind.use_time) VALUES (?,?,?,?);";
                stat = conn.prepareStatement(addDeviceSql);
//                stat.setString(1, id);
                stat.setString(1, phoneName);
                stat.setString(2, deviceName);
                stat.setInt(3, 0);
                stat.setLong(4, time);
                if (stat.executeUpdate() == 1) {

                    aliBaseBean.setMessage("绑定成功");
                    aliBaseBean.setSuccess(true);
                    AliIotBindDeviceBean aliIotBindDeviceBean=new AliIotBindDeviceBean();
                    aliIotBindDeviceBean.setId(id);
                    aliIotBindDeviceBean.setPhoneName(phoneName);
                    aliIotBindDeviceBean.setDeviceName(deviceName);
                    aliIotBindDeviceBean.setUsedTime(time);
                    aliIotBindDeviceBean.setIsSharePhone(0);
                    List<AliIotBindDeviceBean> aliIotBindDeviceBeans=new ArrayList<>();
                    aliIotBindDeviceBeans.add(aliIotBindDeviceBean);
                    aliBaseBean.settList(aliIotBindDeviceBeans);
                    commit(conn, stat);

                }else {
                    aliBaseBean.setMessage("绑定失败");
                    aliBaseBean.setSuccess(false);
                    aliBaseBean.settList(null);
                    conn.rollback();
                }
            }else{
                aliBaseBean.setMessage("绑定失败,设备已经被绑定");
                aliBaseBean.setSuccess(false);
                aliBaseBean.settList(null);
                commit(conn,rs,stat);
            }
        }
        return aliBaseBean;
    }

    @Override
    public AliBaseBean unbindDevice(String deviceName) throws SQLException {
//        String sqlQuery="SELECT COUNT(*) AS ";
        AliBaseBean aliBaseBean=new AliBaseBean();
        if(conn.isClosed()){
            conn= DBManager.getConection();
            conn.setAutoCommit(false);
        }
//        stat=conn.prepareStatement(sqlQuery);
//        rs=stat.executeQuery();

        String sqlDelete="DELETE FROM uee.ali_iot_device_bind WHERE uee.ali_iot_device_bind.device_name='"+deviceName+"'";
        stat = conn.prepareStatement(sqlDelete);
        if(stat.executeUpdate()>=1){
            String sqlDeleteShare="DELETE FROM uee.ali_iot_share WHERE  uee.ali_iot_share.share_device='"+deviceName+"'";
            stat=conn.prepareStatement(sqlDeleteShare);
            if(stat.executeUpdate()>=1){
                aliBaseBean.settList(null);
                aliBaseBean.setSuccess(true);
                aliBaseBean.setMessage("解绑成功");
            }else{
                aliBaseBean.settList(null);
                aliBaseBean.setSuccess(false);
                aliBaseBean.setMessage("解绑成功");
            }
        }else{
            conn.rollback();
            aliBaseBean.settList(null);
            aliBaseBean.setSuccess(false);
            aliBaseBean.setMessage("解绑失败2");
        }
        commit(conn,rs,stat);
        return aliBaseBean;
    }

    @Override
    public AliBaseBean queryBindedDevice(String phoneName) throws SQLException {
        return null;
    }

    @Override
    public AliBaseBean queryAllDeviceByPhoneNumber(String phoneNumber) throws SQLException {
        String sql="SELECT ali_iot_device_bind.device_name,ali_iot_device_bind.phone_name,ali_iot_device_bind.use_time,ali_iot_uee.uee_phone_number " +
                "FROM ali_iot_device_bind " +
                "INNER JOIN ali_iot_uee " +
                "ON ali_iot_device_bind.phone_name = ali_iot_uee.phone_name " +
                "WHERE ali_iot_uee.uee_phone_number='"+phoneNumber+"';";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sql);
        rs = stat.executeQuery();
        AliBaseBean aliBaseBean=new AliBaseBean();
        aliBaseBean.setSuccess(true);
        aliBaseBean.setMessage("查询成功");
        List<AliIotAllDeviceByPhoneBean> aliIotAllDeviceByPhoneBeans=new ArrayList<>();
        while (rs.next()){
            AliIotAllDeviceByPhoneBean aliIotAllDeviceByPhoneBean=new AliIotAllDeviceByPhoneBean();
            aliIotAllDeviceByPhoneBean.setDeviceName(rs.getString("device_name"));
            aliIotAllDeviceByPhoneBean.setPhoneName(rs.getString("phone_name"));
            aliIotAllDeviceByPhoneBean.setUeePhoneNumber(rs.getString("uee_phone_number"));
            aliIotAllDeviceByPhoneBean.setUseTime(rs.getLong("use_time"));
            aliIotAllDeviceByPhoneBeans.add(aliIotAllDeviceByPhoneBean);
        }
        aliBaseBean.settList(aliIotAllDeviceByPhoneBeans);
        commit(conn,rs,stat);
        return aliBaseBean;
    }

    @Override
    public boolean isAlreadyBindDevice(String deviceName) throws SQLException {
        String sql= "SELECT * FROM uee.ali_iot_device_bind WHERE uee.ali_iot_device_bind.device_name='"+deviceName+"'FOR UPDATE;";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sql);
        rs = stat.executeQuery();

        if (rs.next()) {
            if(rs.getRow() >0){
                System.out.println("==========设备"+deviceName+"已被绑定");
                return true;
            }
        }else {
            System.out.println("==========设备"+deviceName+"未被绑定");
            conn.rollback();
        }
        return false;
    }
    
    @Override
    public AliBaseBean deviceIsBound(String deviceName) throws SQLException {
        AliBaseBean aliBaseBean=new AliBaseBean();
        if(isAlreadyBindDevice(deviceName)){
            aliBaseBean.setSuccess(true);
            aliBaseBean.setMessage("该设备已被绑定");
        }else{
            aliBaseBean.setSuccess(false);
            aliBaseBean.setMessage("该设备未被绑定");
        }
         commit(conn,rs,stat);
        return aliBaseBean;
    }

    @Override
    public AliBaseBean sharePhone(String masterPhoneName, String sharePhoneNumber, String deviceName) throws SQLException {
        AliBaseBean aliBaseBean=new AliBaseBean();
        if(!AccountValidatorUtil.isMobile(sharePhoneNumber)){
            aliBaseBean.setSuccess(false);
            aliBaseBean.setMessage("被分享手机号格式错误。");
            aliBaseBean.settList(null);
            commit(conn,rs,stat);
            return aliBaseBean;
        }
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        String queryPhoneNumberSql="SELECT * FROM uee.ali_iot_uee WHERE uee.ali_iot_uee.uee_phone_number='"+sharePhoneNumber+"'";
        stat=conn.prepareStatement(queryPhoneNumberSql);
        rs=stat.executeQuery();
        String sharePhoneName="";
        if(rs.next()){
            sharePhoneName=rs.getString("phone_name");
            System.out.println("******** "+sharePhoneName);
        }


        if(masterPhoneName.equals(sharePhoneName)){
            aliBaseBean.setSuccess(false);
            aliBaseBean.setMessage("分享失败，主控手机不可与被分享手机相同。");
            aliBaseBean.settList(null);
            commit(conn,rs,stat);
            return aliBaseBean;
        }

        String alias="count";
        String sqlIsPhoneExist="SELECT COUNT(*) AS "+alias+ " FROM uee.ali_iot_uee WHERE uee.ali_iot_uee.phone_name='"+sharePhoneName+"'";
        stat=conn.prepareStatement(sqlIsPhoneExist);
        rs=stat.executeQuery();
        if(rs.next()){
            int countPhone=Integer.parseInt(rs.getString(alias));
            if(countPhone >=1){
                String sqlSelect= "SELECT COUNT(*) AS "+alias+" " +
                        "FROM uee.`ali_iot_share` " +
                        "WHERE uee.`ali_iot_share`.master_phone_name='"+masterPhoneName+"'" +
                        "AND uee.`ali_iot_share`.share_device='"+deviceName+"' "+
                        "AND uee.`ali_iot_share`.share_phone='"+sharePhoneName+"' FOR UPDATE;";


                stat = conn.prepareStatement(sqlSelect);
                rs = stat.executeQuery();
                if (rs.next()) {
                    int count=Integer.parseInt(rs.getString(alias));
                    if(count>=1){
                        System.out.println(count+"=====有记录=====");
                        aliBaseBean.setSuccess(false);
                        aliBaseBean.setMessage("设备:"+deviceName+"已经被当前主控手机："+masterPhoneName+"分享给被分享手机"+sharePhoneName+"，无需重复分享");
                        aliBaseBean.settList(null);
                    }else{
                        String sqlQueryMasterPhone="SELECT uee.ali_iot_device_bind.device_name FROM uee.ali_iot_device_bind WHERE uee.ali_iot_device_bind.phone_name='"+masterPhoneName+"'";
                        stat=conn.prepareStatement(sqlQueryMasterPhone);
                        rs=stat.executeQuery();

                        while (rs.next()){
                            String querryDeviceName=rs.getString("device_name");
                            if(querryDeviceName.equals(deviceName)){
                                System.out.println(count+"====无记录======");
                                String sqlInsert="INSERT INTO uee.`ali_iot_share`(uee.`ali_iot_share`.master_phone_name,uee.`ali_iot_share`.share_phone,uee.`ali_iot_share`.share_device,uee.`ali_iot_share`.use_time) VALUES(?,?,?,?);";
                                stat = conn.prepareStatement(sqlInsert);
                                stat.setString(1,masterPhoneName);
                                stat.setString(2,sharePhoneName);
                                stat.setString(3,deviceName);
                                stat.setLong(4,System.currentTimeMillis()/1000);

                                if(stat.executeUpdate()==1){
                                    String sql="UPDATE uee.ali_iot_device_bind SET uee.ali_iot_device_bind.isshare_phone=? WHERE uee.ali_iot_device_bind.device_name='"+deviceName+"'";
                                    PreparedStatement updateManuAuthDataStat = conn.prepareStatement(sql);
                                    updateManuAuthDataStat.setInt(1,1);
                                    if(updateManuAuthDataStat.executeUpdate() == 1){
                                        aliBaseBean.setSuccess(true);
                                        aliBaseBean.setMessage("分享成功");
                                        aliBaseBean.settList(null);
                                    }else{
                                        conn.rollback();
                                        aliBaseBean.setSuccess(false);
                                        aliBaseBean.setMessage("分享失败,未找到被分享的设备");
                                        aliBaseBean.settList(null);
                                    }
                                }else {
                                    conn.rollback();
                                    aliBaseBean.setSuccess(false);
                                    aliBaseBean.setMessage("添加分享记录失败");
                                    aliBaseBean.settList(null);
                                }
                                commit(conn,rs,stat);
                                return aliBaseBean;
                            }else{
                                conn.rollback();
                                aliBaseBean.setSuccess(false);
                                aliBaseBean.setMessage(""+masterPhoneName+"与设备："+deviceName+"不是绑定关系，无法分享");
                                aliBaseBean.settList(null);
                            }
                        }
                        conn.rollback();
                        aliBaseBean.setSuccess(false);
                        aliBaseBean.setMessage("查询绑定设备失败");
                        aliBaseBean.settList(null);
                    }
                }else{
                    conn.rollback();
                    aliBaseBean.setSuccess(false);
                    aliBaseBean.setMessage("分享失败,被分享手机不存在");
                    aliBaseBean.settList(null);
                }
            }else{
                conn.rollback();
                aliBaseBean.setSuccess(false);
                aliBaseBean.setMessage("分享失败,被分享手机不存在");
                aliBaseBean.settList(null);
            }
        }else{
            conn.rollback();
            aliBaseBean.setSuccess(false);
            aliBaseBean.setMessage("分享失败");
            aliBaseBean.settList(null);
        }
        commit(conn,rs,stat);
        return aliBaseBean;
    }

    @Override
    public AliBaseBean cancelSharePhone(String masterPhoneName, String sharePhoneName, String deviceName) throws SQLException {
        AliBaseBean aliBaseBean=new AliBaseBean();
        if(conn.isClosed()){
            conn= DBManager.getConection();
            conn.setAutoCommit(false);
        }

        String sqlDelete="DELETE FROM uee.ali_iot_share " +
                "WHERE uee.ali_iot_share.master_phone_name='"+masterPhoneName+"' " +
                "AND uee.ali_iot_share.share_phone='"+sharePhoneName+"' " +
                "AND uee.ali_iot_share.share_device='"+deviceName+"';";
        stat = conn.prepareStatement(sqlDelete);
        String count="count";
        if(stat.executeUpdate()>=1){
            String sqlSelect="SELECT COUNT(*) AS '"+count+"' FROM uee.ali_iot_share WHERE uee.ali_iot_share.share_device='"+deviceName+"'";
            stat=conn.prepareStatement(sqlSelect);
            rs=stat.executeQuery();
            if(rs.next()){
                int queryCount=rs.getInt(count);
                if(queryCount<=0){
                    String sql="UPDATE uee.ali_iot_device_bind SET uee.ali_iot_device_bind.isshare_phone=? WHERE uee.ali_iot_device_bind.device_name='"+deviceName+"'";
                    PreparedStatement updateManuAuthDataStat = conn.prepareStatement(sql);
                    updateManuAuthDataStat.setInt(1,0);
                    if(updateManuAuthDataStat.executeUpdate() >= 1){
                        System.out.println("更改分享状态成功");
                    }else{
                        System.out.println("更改分享状态失败");
                    }
                    System.out.println("设备："+deviceName+"被分享数量："+queryCount);
                }else{
                    aliBaseBean.settList(null);
                    aliBaseBean.setSuccess(true);
                    aliBaseBean.setMessage("撤销分享成功");
                    System.out.println("查询设备分享数量失败");
                }
            }else{
                conn.rollback();
                aliBaseBean.settList(null);
                aliBaseBean.setSuccess(false);
                aliBaseBean.setMessage("撤销分享失败");
            }
            aliBaseBean.settList(null);
            aliBaseBean.setSuccess(true);
            aliBaseBean.setMessage("撤销分享成功");
        }else{
            conn.rollback();
            aliBaseBean.settList(null);
            aliBaseBean.setSuccess(false);
            aliBaseBean.setMessage("撤销分享失败");
        }
        commit(conn,rs,stat);
        return aliBaseBean;
    }

    @Override
    public AliBaseBean querryPhoneNumber(String phoneName) throws SQLException {
        AliBaseBean aliBaseBean=new AliBaseBean();
        String sql= "SELECT * FROM uee.ali_iot_uee WHERE uee.ali_iot_uee.phone_name='"+phoneName+"' OR uee.ali_iot_uee.uee_phone_number='"+phoneName+"'";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sql);
        rs = stat.executeQuery();

        if (rs.next()) {
            String phoneNumber=rs.getString("uee_phone_number");
            String pn=rs.getString("phone_name");
            AliiotQueryPhoneNumberBean aliiotQueryPhoneNumberBean=new AliiotQueryPhoneNumberBean();
            aliiotQueryPhoneNumberBean.setPhoneNumber(phoneNumber);
            aliiotQueryPhoneNumberBean.setPhoneName(pn);
            List<AliiotQueryPhoneNumberBean> aliiotQueryPhoneNumberBeans=new ArrayList<>();
            aliiotQueryPhoneNumberBeans.add(aliiotQueryPhoneNumberBean);
            aliBaseBean.setMessage("查询成功");
            aliBaseBean.setSuccess(true);
            aliBaseBean.settList(aliiotQueryPhoneNumberBeans);
        }else {
            aliBaseBean.setMessage("查询失败");
            aliBaseBean.setSuccess(false);
            aliBaseBean.settList(null);
        }
        commit(conn,rs,stat);
        return aliBaseBean;
    }

    @Override
    public AliBaseBean querryShareDeviceByPhoneNumber(String phoneNumber) throws SQLException {
        AliBaseBean aliBaseBean=new AliBaseBean();
        String sql="SELECT `ali_iot_uee`.`uee_phone_number` " +
                "AS `uee_phone_number`,`ali_iot_share`.`share_device` " +
                "AS `share_device`,`ali_iot_share`.`share_phone` " +
                "AS `share_phone`,`ali_iot_share`.`master_phone_name` " +
                "AS `master_phone_name`,`ali_iot_share`.`use_time` " +
                "AS `use_time` " +
                "FROM (`ali_iot_uee` JOIN `ali_iot_share` ON ((`ali_iot_share`.`share_phone` = `ali_iot_uee`.`phone_name`)))" +
                "WHERE (`ali_iot_uee`.`uee_phone_number` = '"+phoneNumber+"')";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sql);
        rs = stat.executeQuery();
        List<AliIotQueryShareDeviceBean> aliIotQueryShareDeviceBeans=new ArrayList<>();
        while (rs.next()){
            String ueePhoneN=rs.getString("uee_phone_number");
            String shareDevice=rs.getString("share_device");
            String sharePhone=rs.getString("share_phone");
            String masterPhoneName=rs.getString("master_phone_name");
            long useTime=rs.getLong("use_time");
            AliIotQueryShareDeviceBean aliIotQueryShareDeviceBean=new AliIotQueryShareDeviceBean();
            aliIotQueryShareDeviceBean.setUeePhoneNumber(ueePhoneN);
            aliIotQueryShareDeviceBean.setShareDevice(shareDevice);
            aliIotQueryShareDeviceBean.setSharePhone(sharePhone);
            aliIotQueryShareDeviceBean.setMasterPhoneName(masterPhoneName);
            aliIotQueryShareDeviceBean.setUseTime(useTime);
            aliIotQueryShareDeviceBeans.add(aliIotQueryShareDeviceBean);
        }
        aliBaseBean.setMessage("查询成功");
        aliBaseBean.setSuccess(true);
        aliBaseBean.settList(aliIotQueryShareDeviceBeans);
        commit(conn,rs,stat);
        return aliBaseBean;
    }

    @Override
    public AliBaseBean queryDeviceShareToPhone(String masterPhoneName, String deviceName) throws SQLException {
        AliBaseBean aliBaseBean=new AliBaseBean();
        String sql="SELECT " +
                "uee.ali_iot_share.share_phone , uee.ali_iot_share.use_time , uee.ali_iot_uee.uee_phone_number " +
                "FROM " +
                "(uee.ali_iot_share " +
                "JOIN uee.ali_iot_uee " +
                "ON ((uee.ali_iot_share.share_phone= uee.ali_iot_uee.phone_name))) " +
                "WHERE ((uee.ali_iot_share.master_phone_name = '"+masterPhoneName+"') " +
                "AND (uee.ali_iot_share.share_device = '"+deviceName+"'))";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sql);
        rs = stat.executeQuery();
        List<AliIotQueryDeviceShareToPhoneBean> aliIotQueryDeviceShareToPhoneBeans=new ArrayList<>();
        while (rs.next()){
            String share_phone=rs.getString("share_phone");
            String share_phone_number=rs.getString("uee_phone_number");
            long useTime=rs.getLong("use_time");
            AliIotQueryDeviceShareToPhoneBean aliIotQueryShareDeviceBean=new AliIotQueryDeviceShareToPhoneBean();
            aliIotQueryShareDeviceBean.setSharePhone(share_phone);
            aliIotQueryShareDeviceBean.setShare_phone_number(share_phone_number);
            aliIotQueryShareDeviceBean.setUseTime(useTime);
            aliIotQueryDeviceShareToPhoneBeans.add(aliIotQueryShareDeviceBean);
        }
        aliBaseBean.setMessage("查询成功");
        aliBaseBean.setSuccess(true);
        aliBaseBean.settList(aliIotQueryDeviceShareToPhoneBeans);
        commit(conn,rs,stat);
        return aliBaseBean;
    }


    @Override
    public boolean isDeviceIsAlreadyShared(String deviceName) throws SQLException {
        String sql =
                "SELECT * FROM uee.ali_iot_device_bind "
                        + "WHERE "
                        + "(uee.ali_iot_device_bind.device_name) "
                        + "IN  "
                        + "(SELECT uee.ali_iot_device_bind.device_name FROM uee.ali_iot_device_bind "
                        + "WHERE uee.ali_iot_device_bind.device_name='"+deviceName+"' "
                        + "GROUP BY  "
                        + "uee.ali_iot_device_bind.device_name  "
                        + "HAVING count(*) > 1) "
                        + "FOR UPDATE;";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sql);
        rs = stat.executeQuery();

        if (rs.next()) {
            if(rs.getRow() >0){
                System.out.println("==========设备"+deviceName+"已被分享");
                return true;
            }
        }else {
            System.out.println("==========设备"+deviceName+"未分享");
            conn.rollback();
        }
        return false;
    }


    @Override
    public boolean deviceOrPhoneIsExsit(String deviceOrPhoneName) throws SQLException {
        String productKey = IotClient.getProductKey();
        String jsonDevice= AliIotImpl.queryDeviceByNameTest(productKey,deviceOrPhoneName);
        DeviceIsExsitBean deviceIsExsitBean=new Gson().fromJson(jsonDevice,DeviceIsExsitBean.class);

        if(null == deviceIsExsitBean.getDeviceInfo() || "meta device not found".equals(deviceIsExsitBean.getErrorMessage())){
            return false;
        }
        return true;
    }

    @Override
    public List<String> queryMasterPhoneOfDevice(String deviceName) throws SQLException {
        List<String> phoneNameList=new ArrayList<>();
        String sqlQPhoneNumber1 = "SELECT * FROM uee.ali_iot_device_bind WHERE (uee.ali_iot_device_bind.device_name = '"+deviceName+"');";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sqlQPhoneNumber1);
        rs = stat.executeQuery();
        while (rs.next()){
           String phoneName1= rs.getString("phone_name");
            phoneNameList.add(phoneName1);
        }
        String sqlQPhoneNumber2="SELECT * FROM uee.ali_iot_share where (uee.ali_iot_share.share_device = '"+deviceName+"');";
        stat=conn.prepareStatement(sqlQPhoneNumber2);
        rs=stat.executeQuery();
        while (rs.next()){
            String phoneName2=rs.getString("share_phone");
            phoneNameList.add(phoneName2);
        }
        commit(conn,rs,stat);
        return phoneNameList;
    }


    @Override
    public void commit(Connection conn, ResultSet rs, Statement stat) throws SQLException {
        try {
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
        } finally {
            if (rs != null) rs.close();
            if (stat != null) stat.close();
            if (conn != null) conn.close();

        }
    }

    @Override
    public void commit(Connection conn, Statement stat) throws SQLException {
        commit(conn, null, stat);
    }

    public static void main(String[] args) throws Exception {
        String dbUrl = "jdbc:mysql://192.168.100.100/uee?" +
                "serverTimezone=UTC&" +
                "characterEncoding=utf-8&" +
                "user=uee&" +
                "password=a12345."
                ;
        DBManager.init(dbUrl);
        AliIotDataSourceImpl aliIotDataSource=new AliIotDataSourceImpl();
        aliIotDataSource.sharePhone("15502113227","1377777777","device004");
    }
}
