package com.ueehome.logic;

import com.ueehome.logic.bean.RegisterQQIOTLicenseData;
import com.ueehome.util.JDBCUtil;

/**
 * Created by TangWei on 2017/3/27.
 * 生产
 */
public class Manufacture {
    public RegisterQQIOTLicenseData registerQQIOTLicense() {
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.init("Shanghai_VPN");
        return new RegisterQQIOTLicenseData("1", "1", "1");
    }
}
