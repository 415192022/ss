package com.ueehome.dbmodel.modelimpl;

import com.ueehome.DBManager;
import com.ueehome.dbmodel.bean.ManuAuthData;
import com.ueehome.dbmodel.bean.ProductData;
import com.ueehome.dbmodel.model.ManufactureDataSource;
import com.ueehome.logic.bean.QQIOTBean;

import java.sql.*;
import java.util.UUID;

/**
 * Created by TangWei on 2017/4/5.
 * ManufactureDataSourceImpl
 */
public class ManufactureDataSourceImpl implements ManufactureDataSource {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement stat;

    public ManufactureDataSourceImpl() throws SQLException {
        conn = DBManager.getConection();
        conn.setAutoCommit(false);
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

        String sql = "SELECT qqiot_licence.guid, qqiot_licence.licence, qqiot_licence.used, qqiot_licence.use_time FROM qqiot_licence WHERE used = 0 LIMIT 1";
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
                updateQqIotStat.setLong(1, System.currentTimeMillis());
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
