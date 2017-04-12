package com.ueehome.logic;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ueehome.UEEException;
import com.ueehome.apibean.QQIOTImportResponse;
import com.ueehome.dbmodel.bean.ManuAuthData;
import com.ueehome.dbmodel.bean.ProductData;
import com.ueehome.dbmodel.modelimpl.ManufactureDataSourceImpl;
import com.ueehome.logic.bean.QQIOTBean;
import com.ueehome.logic.bean.RegisterQQIOTLicenceData;

import java.sql.SQLException;
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
            throw halt(500);
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
            if (manuAuthData.prodDeadline - manuAuthData.prodDate < 0)
                throw new UEEException(2001005); //password授权已过期
            if (manuAuthData.count.equals(manuAuthData.usedCount))
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
}
