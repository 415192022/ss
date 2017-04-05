package com.ueehome.dbmodel.model;

import com.ueehome.dbmodel.bean.ManuAuthData;
import com.ueehome.dbmodel.bean.ProductData;

/**
 * Created by TangWei on 2017/4/5.
 * ManufactureDataSource
 */
public interface ManufactureDataSource {
    ProductData registQQIOT(String password);

    ManuAuthData getManuAuthData(String password);
}
