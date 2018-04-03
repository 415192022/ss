package com.ueehome.dbmodel.modelimpl;

import com.google.gson.Gson;
import com.ueehome.DBManager;
import com.ueehome.apibean.WXDeviceBean;
import com.ueehome.apibean.WXOpenidListBean;
import com.ueehome.dbmodel.bean.*;
import com.ueehome.dbmodel.model.ManufactureDataSource;
import com.ueehome.logic.bean.QQIOTBean;
import com.ueehome.logic.bean.UEEIOTBean;
import com.ueehome.logic.bean.WXIOTBean;
import com.ueehome.util.GetWXToken;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by TangWei on 2017/4/5.
 * ManufactureDataSourceImpl
 */
public class ManufactureDataSourceImpl implements ManufactureDataSource {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement stat;
    private GetWXToken getWXToken;

    public ManufactureDataSourceImpl() throws SQLException {
        conn = DBManager.getConection();
        conn.setAutoCommit(false);
        getWXToken=new GetWXToken();
    }

    @Override
    public ProductData registQQIOT(String password) throws SQLException {
        ManuAuthData manuAuthData = getManuAuthData(password);

        String ueeId = null;
        String pid = null;
        String pidSub = null;
        Long prodDate = null;
        String qqiotGuid = null;
        String qqiotLicense = null;

        String sql = "SELECT qqiot_licence.guid, qqiot_licence.licence, qqiot_licence.used, qqiot_licence.use_time FROM qqiot_licence WHERE used = 0 LIMIT 1 FOR UPDATE";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }

        stat = conn.prepareStatement(sql);
        rs = stat.executeQuery();
        if (rs.next()) {
            ueeId = UUID.randomUUID().toString();
            pid = manuAuthData.pid;
            pidSub = manuAuthData.pidSub;
            prodDate = manuAuthData.prodDate;
            qqiotGuid = rs.getString(1);
            qqiotLicense = rs.getString(2);

            String addDeviceSql = "INSERT INTO `uee`.`device` (`uee_id`, `pid`, `pid_sub`, `prod_date`, `qqiot_guid`, `qqiot_licence`) VALUES (?, ?, ?, ?, ?, ?)";
            stat = conn.prepareStatement(addDeviceSql);
            stat.setString(1, ueeId);
            stat.setString(2, pid);
            stat.setString(3, pidSub);
            stat.setLong(4, prodDate);
            stat.setString(5, qqiotGuid);
            stat.setString(6, qqiotLicense);

            if (stat.executeUpdate() == 1) {
                String updateQqIotSql = "UPDATE `uee`.`qqiot_licence` SET `used` = '1', `use_time` = ? WHERE (`guid` = ?) LIMIT 1";
                PreparedStatement updateQqIotStat = conn.prepareStatement(updateQqIotSql);
                updateQqIotStat.setLong(1, System.currentTimeMillis() / 1000);
                updateQqIotStat.setString(2, qqiotGuid);

                if (updateQqIotStat.executeUpdate() == 1) {
                    String updateManuAuthData = "UPDATE `uee`.`manu_auth` SET used_count = used_count + 1 WHERE (`passwd` = ?) LIMIT 1";
                    PreparedStatement updateManuAuthDataStat = conn.prepareStatement(updateManuAuthData);
                    updateManuAuthDataStat.setString(1, password);
                    if (updateManuAuthDataStat.executeUpdate() == 1) {
                        commit(conn, stat);
                    } else conn.rollback();
                } else conn.rollback();
            } else conn.rollback();
        } else conn.rollback();


        return new ProductData(ueeId, pid, pidSub, prodDate, qqiotGuid, qqiotLicense);
    }

    @Override
    public UeeProductData registUEEIOT(String password) throws SQLException {
        UeeManuAuthData manuAuthData = getUeeManuAuthData(password);

        String ueeId = null;
        String pid = null;
        String pidSub = null;
        Long prodDate = null;
        String ueeiot_licence = null;

        String sql = "SELECT  ueeiot_licence.licence, ueeiot_licence.used, ueeiot_licence.use_time FROM ueeiot_licence WHERE used = 0 LIMIT 1 "
                + "FOR UPDATE"
                ;
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }

        stat = conn.prepareStatement(sql);
        rs = stat.executeQuery();
        if (rs.next()) {
            ueeId = UUID.randomUUID().toString()+"-001-"+"0120";
            pid = manuAuthData.pid;
            pidSub = manuAuthData.pidSub;
            prodDate = manuAuthData.prodDate;
            ueeiot_licence = rs.getString(1);

            String addDeviceSql = "INSERT INTO `uee`.`device_uee` (`uee_id`, `pid`, `pid_sub`, `prod_date`, `ueeiot_licence`) VALUES (?, ?, ?, ?, ?)";
            stat = conn.prepareStatement(addDeviceSql);
            stat.setString(1, ueeId);
            stat.setString(2, pid);
            stat.setString(3, pidSub);
            stat.setLong(4, prodDate);
            stat.setString(5, ueeiot_licence);

            if (stat.executeUpdate() == 1) {
                String updateQqIotSql = "UPDATE `uee`.`ueeiot_licence` SET `used` = '1', `use_time` = ?" +
                        " WHERE (`licence` = ?)" +
                        " LIMIT 1";
                PreparedStatement updateQqIotStat = conn.prepareStatement(updateQqIotSql);
                updateQqIotStat.setLong(1, System.currentTimeMillis() / 1000);
                updateQqIotStat.setString(2, ueeiot_licence);
                if (updateQqIotStat.executeUpdate() == 1) {
                    String updateManuAuthData = "UPDATE `uee`.`manu_auth_uee` SET used_count = used_count + 1 WHERE (`passwd` = ?) LIMIT 1";
                    PreparedStatement updateManuAuthDataStat = conn.prepareStatement(updateManuAuthData);
                    updateManuAuthDataStat.setString(1, password);
                    if (updateManuAuthDataStat.executeUpdate() == 1) {
                        commit(conn, stat);
                    } else conn.rollback();
                } else conn.rollback();
            } else conn.rollback();
        } else conn.rollback();


        return new UeeProductData(ueeId, pid, pidSub, prodDate, ueeiot_licence);
    }


    @Override
    public WXProductData registWXIOT(String password) throws SQLException {
        WXManuAuthData wxManuAuthData = getWXManuAuthData(password);

        String ueeId = null;
        String pid = null;
        String pidSub = null;
        Long prodDate = null;
        String qrticket = null;
        String deviceid = null;
        String device_type = null;

        String sql = "SELECT  " +
                "wx_licence.deviceid" +
                ",wx_licence.qrticket" +
                ",wx_licence.device_type" +
                ",wx_licence.used" +
                ",wx_licence.use_time " +
                "FROM wx_licence " +
                "WHERE used = 0 LIMIT 1 " +
                "FOR UPDATE"
                ;
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }

        stat = conn.prepareStatement(sql);
        rs = stat.executeQuery();
        if (rs.next()) {
            ueeId = UUID.randomUUID().toString()+"-001-"+"0120";
            pid = wxManuAuthData.pid;
            pidSub = wxManuAuthData.pidSub;
            prodDate = wxManuAuthData.prodDate;
            deviceid=rs.getString(1);
            qrticket = rs.getString(2);
            device_type=rs.getString(3);

            String addDeviceSql = "INSERT INTO `uee`.`device_wx` (`uee_id`, `pid`, `pid_sub`, `prod_date`, `deviceid`, `qrticket`, `device_type`) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stat = conn.prepareStatement(addDeviceSql);
            stat.setString(1, ueeId);
            stat.setString(2, pid);
            stat.setString(3, pidSub);
            stat.setLong(4, prodDate);
            stat.setString(5, deviceid);
            stat.setString(6, qrticket);
            stat.setString(7, device_type);

            if (stat.executeUpdate() == 1) {
                String updateQqIotSql = "UPDATE `uee`.`wx_licence` SET `used` = '1', `use_time` = ?" +
                        " WHERE (`qrticket` = ?)" +
                        " LIMIT 1";
                PreparedStatement updateQqIotStat = conn.prepareStatement(updateQqIotSql);
                updateQqIotStat.setLong(1, System.currentTimeMillis() / 1000);
                updateQqIotStat.setString(2, qrticket);
                if (updateQqIotStat.executeUpdate() == 1) {
                    String updateManuAuthData = "UPDATE `uee`.`manu_auth_wx` SET used_count = used_count + 1 WHERE (`passwd` = ?) LIMIT 1";
                    PreparedStatement updateManuAuthDataStat = conn.prepareStatement(updateManuAuthData);
                    updateManuAuthDataStat.setString(1, password);
                    if (updateManuAuthDataStat.executeUpdate() == 1) {
                        commit(conn, stat);
                    } else conn.rollback();
                } else conn.rollback();
            } else conn.rollback();
        } else conn.rollback();

        return new WXProductData(ueeId, pid, pidSub, prodDate, deviceid,qrticket,device_type);
    }






    @Override
    public ManuAuthData getManuAuthData(String password) throws SQLException {
        String pid = null, pidSub = null;
        Long prodDate = 0L, prodDeadline = 0L;
        Integer count = null, usedCount = null;
        Boolean enable = false;
        String sql = "SELECT manu_auth.passwd, manu_auth.pid, manu_auth.pid_sub, manu_auth.prod_date, manu_auth.prod_deadline, manu_auth.count, manu_auth.used_count, manu_auth.`enable` FROM manu_auth "
                + "WHERE manu_auth.passwd = ? AND manu_auth.count >= manu_auth.used_count";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sql);
        stat.setString(1, password);
        rs = stat.executeQuery();
        if (rs.next()) {
            pid = rs.getString(2);
            pidSub = rs.getString(3);
            prodDate = rs.getLong(4);
            prodDeadline = rs.getLong(5);
            count = rs.getInt(6);
            usedCount = rs.getInt(7);
            enable = rs.getBoolean(8);
        } else conn.rollback();
        commit(conn, rs, stat);
        return new ManuAuthData(pid, pidSub, prodDate, prodDeadline, count, usedCount, enable);
    }

    @Override
    public UeeManuAuthData getUeeManuAuthData(String password) throws SQLException {
        String pid = null, pidSub = null;
        Long prodDate = 0L, prodDeadline = 0L;
        Integer count = null, usedCount = null;
        Boolean enable = false;
        String sql = "SELECT manu_auth_uee.passwd, manu_auth_uee.pid, manu_auth_uee.pid_sub, manu_auth_uee.prod_date, manu_auth_uee.prod_deadline, manu_auth_uee.count, manu_auth_uee.used_count, manu_auth_uee.`enable` FROM manu_auth_uee " + "WHERE manu_auth_uee.passwd = ? AND manu_auth_uee.count >= manu_auth_uee.used_count";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sql);
        stat.setString(1, password);
        rs = stat.executeQuery();
        if (rs.next()) {
            pid = rs.getString(2);
            pidSub = rs.getString(3);
            prodDate = rs.getLong(4);
            prodDeadline = rs.getLong(5);
            count = rs.getInt(6);
            usedCount = rs.getInt(7);
            enable = rs.getBoolean(8);
        } else conn.rollback();
        commit(conn, rs, stat);
        return new UeeManuAuthData(pid, pidSub, prodDate, prodDeadline, count, usedCount, enable);
    }

    @Override
    public WXManuAuthData getWXManuAuthData(String password) throws SQLException {
        String pid = null, pidSub = null;
        Long prodDate = 0L, prodDeadline = 0L;
        Integer count = null, usedCount = null;
        Boolean enable = false;
        String sql = "SELECT manu_auth_wx.passwd, manu_auth_wx.pid, manu_auth_wx.pid_sub, manu_auth_wx.prod_date, manu_auth_wx.prod_deadline, manu_auth_wx.count, manu_auth_wx.used_count, manu_auth_wx.`enable` FROM manu_auth_wx " + "WHERE manu_auth_wx.passwd = ? AND manu_auth_wx.count >= manu_auth_wx.used_count";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sql);
        stat.setString(1, password);
        rs = stat.executeQuery();
        if (rs.next()) {
            pid = rs.getString(2);
            pidSub = rs.getString(3);
            prodDate = rs.getLong(4);
            prodDeadline = rs.getLong(5);
            count = rs.getInt(6);
            usedCount = rs.getInt(7);
            enable = rs.getBoolean(8);
        } else conn.rollback();
        commit(conn, rs, stat);
        return new WXManuAuthData(pid, pidSub, prodDate, prodDeadline, count, usedCount, enable);
    }

    @Override
    public Boolean importQQIOTLicence(QQIOTBean[] datalist) throws SQLException {
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        String sql = "INSERT INTO `uee`.`qqiot_licence` (`guid`, `licence`, `used`, `use_time`) VALUES (?, ?, '0', NULL)";
        PreparedStatement stat = conn.prepareStatement(sql);
        for (QQIOTBean item : datalist) {
            stat.setString(1, item.getQq_guid());
            stat.setString(2, item.getQq_licence());
            stat.addBatch();
        }
        try {
            int[] result = stat.executeBatch();


            boolean allEqual = true;
            for (int s : result) {
                if (s != 1)
                    allEqual = false;
            }
            if (allEqual) {
                commit(conn, stat);
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
        }

    }

    @Override
    public Boolean importUEEIOTLicence(UEEIOTBean[] datalist) throws SQLException {
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        String sql = "INSERT INTO `uee`.`ueeiot_licence` ( `licence`, `used`, `use_time`) VALUES ( ?, '0', NULL)";
        PreparedStatement stat = conn.prepareStatement(sql);
        for (UEEIOTBean item : datalist) {
            stat.setString(1, item.getUee_licence());
            stat.addBatch();
        }
        try {
            int[] result = stat.executeBatch();
            boolean allEqual = true;
            for (int s : result) {
                if (s != 1)
                    allEqual = false;
            }
            if (allEqual) {
                commit(conn, stat);
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
        }
    }

    @Override
    public Boolean importWXIOTLicence(WXIOTBean[] datalist) throws SQLException {
        return null;
    }

    @Override
    public WXSaveTokenData saveWXToken() throws SQLException {
        String sql_token = "SELECT uee.wx_token.access_token, uee.wx_token.expires_in, uee.wx_token.use_time FROM uee.wx_token  LIMIT 1 "
//                + "FOR UPDATE"
                ;
        String sql_count = "SELECT count(uee.wx_token.access_token) from uee.wx_token LIMIT 1 "
//                + "FOR UPDATE"
                ;
        int count=0;
        String access_token;
        Long expires_in;
        Long use_time;
        WXSaveTokenData wxSaveTokenData=null;
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sql_count);
        rs = stat.executeQuery();
        if (rs.next()) {
            count=rs.getInt(1);
            if(count>=1){
                stat = conn.prepareStatement(sql_token);
                rs=stat.executeQuery();
                if(rs.next()){
                    access_token=rs.getString(1);
                    expires_in=rs.getLong(2);
                    use_time=rs.getLong(3);
                    if(System.currentTimeMillis()/1000-use_time>= expires_in){
                        //token过期
                        String tokenJson=getWXToken.getToken();
                        wxSaveTokenData=new Gson().fromJson(tokenJson,WXSaveTokenData.class);
                        wxSaveTokenData.setRemaining_expiration(expires_in-(System.currentTimeMillis()/1000-use_time));
                            String updateWxTokenSQL = "UPDATE `uee`.`wx_token` SET `uee`.`wx_token`.`access_token` = ?, `uee`.`wx_token`.`expires_in` = ?, `uee`.`wx_token`.`use_time` = ?" + " LIMIT 1";
                            PreparedStatement updateWXTokenStat = conn.prepareStatement(updateWxTokenSQL);
                        updateWXTokenStat.setString(1,wxSaveTokenData.getAccess_token());
                        updateWXTokenStat.setLong(2,wxSaveTokenData.getExpires_in());
                        updateWXTokenStat.setLong(3, System.currentTimeMillis() / 1000);
                        int up=updateWXTokenStat.executeUpdate();
                        if (up == 1) {
                            }else conn.rollback();

                    }else{
                        wxSaveTokenData=new WXSaveTokenData();
                        wxSaveTokenData.setAccess_token(access_token);
                        wxSaveTokenData.setExpires_in(expires_in);
                        wxSaveTokenData.setRemaining_expiration(expires_in-(System.currentTimeMillis()/1000-use_time));
                    }
                    System.out.println(System.currentTimeMillis()/1000-use_time);
                }else conn.rollback();
                commit(conn, rs, stat);
            }else{
                String tokenJson=getWXToken.getToken();
                 wxSaveTokenData=new Gson().fromJson(tokenJson,WXSaveTokenData.class);
                 access_token=wxSaveTokenData.getAccess_token();
                expires_in=wxSaveTokenData.getExpires_in();
                use_time = System.currentTimeMillis()/1000;
                wxSaveTokenData.setRemaining_expiration(expires_in-(System.currentTimeMillis()/1000-use_time));
                String sql_addtoken = "INSERT INTO `uee`.`wx_token` (`access_token`, `expires_in`, `use_time`) VALUES (?, ?, ?)";
                System.out.println(wxSaveTokenData);
                stat = conn.prepareStatement(sql_addtoken);
                stat.setString(1, access_token);
                stat.setLong(2, expires_in);
                stat.setLong(3, use_time);
                if (stat.executeUpdate() == 1) {
                    commit(conn, stat);
                }
            }
        }else conn.rollback();

        return wxSaveTokenData;
    }

    @Override
    public Boolean wxDeviceBindUnbindDevice(WXDeviceBean wxDeviceBean) throws SQLException {
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        if(wxDeviceBean.getMsg_type().equals("bind")){
            String sql_binddevice = "INSERT INTO"
                    + " `uee`.`wx_openid` "
                    + "(`device_id`, `device_type`, `msg_id`, `msg_type`, `create_time`, `open_id`, `session_id`, `content`, `qrcode_suffix_data`)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stat = conn.prepareStatement(sql_binddevice);
            stat.setString(1, wxDeviceBean.getDevice_id());
            stat.setString(2, wxDeviceBean.getDevice_type());
            stat.setString(3, wxDeviceBean.getMsg_id());
            stat.setString(4, wxDeviceBean.getMsg_type());
            stat.setLong(5, wxDeviceBean.getCreate_time());
            stat.setString(6, wxDeviceBean.getOpen_id());
            stat.setString(7, wxDeviceBean.getSession_id());
            stat.setString(8, wxDeviceBean.getContent());
            stat.setString(9, wxDeviceBean.getQrcode_suffix_data());
            if (stat.executeUpdate() == 1) {
                commit(conn, stat);
            }else conn.rollback();
            return true;

        }else if(wxDeviceBean.getMsg_type().equals("unbind")){
            String sql_deletebinddevice="DELETE FROM"
                    + " `uee`.`wx_openid` "
                    + "WHERE "
                    + "open_id = '"+wxDeviceBean.getOpen_id()+"'";
            stat = conn.prepareStatement(sql_deletebinddevice);
            if (stat.executeUpdate() == 1) {
                commit(conn, stat);
            }else conn.rollback();
            return true;
        }

        return false;
    }

    @Override
    public WXOpenidListBean wxGetOpenId(String deviceId) throws SQLException {
        String sql_token="SELECT * FROM uee.wx_openid WHERE device_id = '"+deviceId+"' ";
        if (conn.isClosed()) {
            conn = DBManager.getConection();
            conn.setAutoCommit(false);
        }
        stat = conn.prepareStatement(sql_token);
        rs=stat.executeQuery();
        WXOpenidListBean wxOpenidListBean=new WXOpenidListBean();
        List<WXDeviceBean> wxDeviceBeans=new ArrayList<>();
        while (rs.next()){
            WXDeviceBean wxDeviceBean=new WXDeviceBean();
            wxDeviceBean.setDevice_id(rs.getString(1));
            wxDeviceBean.setDevice_type(rs.getString(2));
            wxDeviceBean.setMsg_id(rs.getString(3));
            wxDeviceBean.setMsg_type(rs.getString(4));
            wxDeviceBean.setCreate_time(rs.getLong(5));
            wxDeviceBean.setOpen_id(rs.getString(6));
            wxDeviceBean.setSession_id(rs.getString(7));
            wxDeviceBean.setContent(rs.getString(8));
            wxDeviceBean.setQrcode_suffix_data(rs.getString(9));
            wxDeviceBeans.add(wxDeviceBean);
        }
        commit(conn, stat);
        wxOpenidListBean.setWxDeviceBeans(wxDeviceBeans);
        return wxOpenidListBean;
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

}
