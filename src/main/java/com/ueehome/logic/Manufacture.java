package com.ueehome.logic;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ueehome.UEEException;
import com.ueehome.apibean.WXDeviceBean;
import com.ueehome.apibean.WXOpenidListBean;
import com.ueehome.dbmodel.bean.*;
import com.ueehome.dbmodel.modelimpl.ManufactureDataSourceImpl;
import com.ueehome.logic.bean.*;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * Created by TangWei on 2017/3/27.
 * 生产
 */
public class Manufacture {

    private ManufactureDataSourceImpl manufactureDataSource;

    public Manufacture() throws SQLException {
        manufactureDataSource = new ManufactureDataSourceImpl();
    }

    public RegisterQQIOTLicenceData registerQQIOTLicence(String password) throws SQLException, UEEException {
        if (validPassword(password)) {
            ProductData productData = manufactureDataSource.registQQIOT(password);
            ManuAuthData manuAuthData = manufactureDataSource.getManuAuthData(password);

            if (productData.ueeID == null && productData.pid == null && productData.pidSub == null && productData.prodDate == null && productData.qqiotGUID == null && productData.qqiotLicence == null)
                throw new UEEException(2001007);//QQ物联授权已用完
            return new RegisterQQIOTLicenceData(productData.ueeID, productData.qqiotGUID, productData.qqiotLicence, manuAuthData.count - manuAuthData.usedCount);
        } else
            throw new UEEException(2001003); //密码错误
    }




    public RegisterUEEIOTLicenceData registerUEEIOTLicence(String password) throws SQLException, UEEException {
        if (validUeePassword(password)) {
            UeeProductData productData = manufactureDataSource.registUEEIOT(password);
            UeeManuAuthData ueeManuAuthData = manufactureDataSource.getUeeManuAuthData(password);

            if (productData.ueeID == null && productData.pid == null && productData.pidSub == null && productData.prodDate == null  && productData.ueeiotLicence == null)
                throw new UEEException(2001007);//授权已用完
            return new RegisterUEEIOTLicenceData(productData.ueeID,  productData.ueeiotLicence, ueeManuAuthData.count - ueeManuAuthData.usedCount);
        } else
            throw new UEEException(2001003); //密码错误
    }


    public RegisterWXIOTLicenceData registerWXIOTLicence(String password) throws SQLException, UEEException {
        if (validWXPassword(password)) {
            WXProductData wxProductData = manufactureDataSource.registWXIOT(password);
            WXManuAuthData wxManuAuthData = manufactureDataSource.getWXManuAuthData(password);

            if (wxProductData.ueeID == null && wxProductData.pid == null && wxProductData.pidSub == null && wxProductData.prodDate == null  && wxProductData.deviceid == null && wxProductData.qrticket == null&& wxProductData.device_type == null)
                throw new UEEException(2001007);//授权已用完
            return new RegisterWXIOTLicenceData(wxProductData.deviceid,  wxProductData.qrticket,wxProductData.device_type, wxManuAuthData.count - wxManuAuthData.usedCount);
        } else
            throw new UEEException(2001003); //密码错误
    }

    public WXSaveTokenData getWXToken() throws SQLException{
        return manufactureDataSource.saveWXToken();
    }






    private boolean validPassword(String password) throws SQLException, UEEException {

        ManuAuthData manuAuthData = manufactureDataSource.getManuAuthData(password);

        if (manuAuthData.pid == null && manuAuthData.pidSub == null &&
                manuAuthData.prodDate == 0L && manuAuthData.prodDeadline == 0L
                && manuAuthData.count == null && manuAuthData.usedCount == null
                && manuAuthData.enable.equals(false)) {
            throw new UEEException(2001003);//password错误或不存在
        } else {
            if (!manuAuthData.enable) throw new UEEException(2001006); //password授权已关闭
            Long now = Calendar.getInstance().getTimeInMillis() / 1000;
            if (manuAuthData.prodDeadline - now < 0)
                throw new UEEException(2001005); //password授权已过期
            if (manuAuthData.count.equals(manuAuthData.usedCount))
                throw new UEEException(2001004);//password授权数已用完
        }

        return true;
    }
    private boolean validUeePassword(String password) throws SQLException, UEEException {

        UeeManuAuthData manuAuthData = manufactureDataSource.getUeeManuAuthData(password);

        if (manuAuthData.pid == null && manuAuthData.pidSub == null &&
                manuAuthData.prodDate == 0L && manuAuthData.prodDeadline == 0L
                && manuAuthData.count == null && manuAuthData.usedCount == null
                && manuAuthData.enable.equals(false)) {
            throw new UEEException(2001003);//password错误或不存在
        } else {
            if (!manuAuthData.enable) throw new UEEException(2001006); //password授权已关闭
            Long now = Calendar.getInstance().getTimeInMillis() / 1000;
            if (manuAuthData.prodDeadline - now < 0)
                throw new UEEException(2001005); //password授权已过期
            if (manuAuthData.count.equals(manuAuthData.usedCount))
                throw new UEEException(2001004);//password授权数已用完
        }

        return true;
    }

    private boolean validWXPassword(String password) throws SQLException, UEEException {

        WXManuAuthData wxManuAuthData = manufactureDataSource.getWXManuAuthData(password);

        if (wxManuAuthData.pid == null && wxManuAuthData.pidSub == null &&
                wxManuAuthData.prodDate == 0L && wxManuAuthData.prodDeadline == 0L
                && wxManuAuthData.count == null && wxManuAuthData.usedCount == null
                && wxManuAuthData.enable.equals(false)) {
            throw new UEEException(2001003);//password错误或不存在
        } else {
            if (!wxManuAuthData.enable) throw new UEEException(2001006); //password授权已关闭
            Long now = Calendar.getInstance().getTimeInMillis() / 1000;
            if (wxManuAuthData.prodDeadline - now < 0)
                throw new UEEException(2001005); //password授权已过期
            if (wxManuAuthData.count.equals(wxManuAuthData.usedCount))
                throw new UEEException(2001004);//password授权数已用完
        }
        return true;
    }

    public void importQQIOTLicence(String password, String data) throws SQLException, UEEException {
        if (!Objects.equals(password, "&m9OBKlE2N5Z")) throw new UEEException(2002006);
        manufactureDataSource = new ManufactureDataSourceImpl();
        try {
            QQIOTBean[] datalist = new Gson().fromJson(data, QQIOTBean[].class);
            if (datalist.length > 1000) {
                throw new UEEException(2002005); //一次批量插入不能超过1000条
            }
            for (QQIOTBean item : datalist) {
                if (item.getQq_guid() == null || item.getQq_guid().isEmpty())
                    throw new UEEException(2002001); //缺少GUID
                if (item.getQq_licence() == null || item.getQq_licence().isEmpty())
                    throw new UEEException(2002002); //缺少Licence
            }
            manufactureDataSource.importQQIOTLicence(datalist);
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 1062:
                    throw new UEEException(2002004); //GUID或Licence不唯一
                default:
                    throw halt(500);
            }
        } catch (JsonSyntaxException e) {
            throw new UEEException(2002003); //参数格式校验不通过
        }

    }
    public void importUEEIOTLicence(String password, String data) throws SQLException, UEEException {
        if (!Objects.equals(password, "&m9OBKlE2N5Z")) throw new UEEException(2002006);
        manufactureDataSource = new ManufactureDataSourceImpl();
        try {
            UEEIOTBean[] datalist = new Gson().fromJson(data, UEEIOTBean[].class);
            if (datalist.length > 1000) {
                throw new UEEException(2002005); //一次批量插入不能超过1000条
            }
            for (UEEIOTBean item : datalist) {
                if (item.getUee_licence() == null )
                if (item.getUee_licence() == null || item.getUee_licence().isEmpty())
                    throw new UEEException(2002002); //缺少Licence
            }
            manufactureDataSource.importUEEIOTLicence(datalist);
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 1062:
                    throw new UEEException(2002004); //GUID或Licence不唯一
                default:
                    throw halt(500);
            }
        } catch (JsonSyntaxException e) {
            throw new UEEException(2002003); //参数格式校验不通过
        }

    }
    public Boolean wxDeviceBindUnbindDevice(WXDeviceBean wxDeviceBean) throws SQLException{
        return manufactureDataSource.wxDeviceBindUnbindDevice(wxDeviceBean);
    }

    public WXOpenidListBean wxGetOpenId(String deviceId )throws SQLException{
        return manufactureDataSource.wxGetOpenId(deviceId);
    }
}
