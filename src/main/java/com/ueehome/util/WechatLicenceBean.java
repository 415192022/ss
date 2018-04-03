package com.ueehome.util;

import java.util.List;

/**
 * Created by uee on 2017/11/3.
 */
public class WechatLicenceBean {
    private List<WechatLicenceBeanItem> wechatLicenceBeanItems;

    public List<WechatLicenceBeanItem> getWechatLicenceBeanItems() {
        return wechatLicenceBeanItems;
    }

    public void setWechatLicenceBeanItems(List<WechatLicenceBeanItem> wechatLicenceBeanItems) {
        this.wechatLicenceBeanItems = wechatLicenceBeanItems;
    }

    @Override
    public String toString() {
        return "WechatLicenceBean{" +
                "wechatLicenceBeanItems=" + wechatLicenceBeanItems +
                '}';
    }

    public static class WechatLicenceBeanItem{
       private String deviceid;
       private String qrticket;
       private String device_type;

        public void setDeviceid(String deviceid) {
            this.deviceid = deviceid;
        }

        public void setQrticket(String qrticket) {
            this.qrticket = qrticket;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getDeviceid() {
            return deviceid;
        }

        public String getQrticket() {
            return qrticket;
        }

        public String getDevice_type() {
            return device_type;
        }

        @Override
        public String toString() {
            return "WechatLicenceBeanItem{" +
                    "deviceid='" + deviceid + '\'' +
                    ", qrticket='" + qrticket + '\'' +
                    ", device_type='" + device_type + '\'' +
                    '}';
        }
    }
}
