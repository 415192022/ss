package com.ueehome.logic;

import com.ueehome.dbmodel.bean.ManuAuthData;
import com.ueehome.dbmodel.bean.ProductData;
import com.ueehome.dbmodel.modelimpl.ManufactureDataSourceImpl;
import com.ueehome.logic.bean.RegisterQQIOTLicenceData;

/**
 * Created by TangWei on 2017/3/27.
 * 生产
 */
public class Manufacture {
    public RegisterQQIOTLicenceData registerQQIOTLicence(String password) {
        ManufactureDataSourceImpl manufactureDataSource = new ManufactureDataSourceImpl();
        ProductData productData = manufactureDataSource.registQQIOT(password);
        ManuAuthData manuAuthData = manufactureDataSource.getManuAuthData(password);
        return new RegisterQQIOTLicenceData(productData.pid, productData.qqiotGUID, productData.qqiotLicence, manuAuthData.count-manuAuthData.usedCount);
    }
}
