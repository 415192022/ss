package com.ueehome.dbmodel.modelimpl;

import com.ueehome.dbmodel.bean.ManuAuthData;
import com.ueehome.dbmodel.bean.ProductData;
import com.ueehome.dbmodel.model.ManufactureDataSource;

/**
 * Created by TangWei on 2017/4/5.
 * ManufactureDataSourceImpl
 */
public class ManufactureDataSourceImpl implements ManufactureDataSource {

    @Override
    public ProductData registQQIOT(String password) {
        return new ProductData("", "", "", 0L, "", "");
    }

    @Override
    public ManuAuthData getManuAuthData(String password) {
        return new ManuAuthData("", "", 0L, 0L, 0, 0, true);
    }
}
