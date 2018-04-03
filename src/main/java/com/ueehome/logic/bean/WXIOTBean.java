package com.ueehome.logic.bean;

/**
 * Created by Ryan Wu on 2017/4/11.
 */
public class WXIOTBean {
        private String deviceid;
        private String qrticket;
        private String device_type;

    public String getDeviceid() {
        return deviceid;
    }

    public String getQrticket() {
        return qrticket;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public void setQrticket(String qrticket) {
        this.qrticket = qrticket;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    @Override
    public String toString() {
        return "WXIOTBean{" +
                "deviceid='" + deviceid + '\'' +
                ", qrticket='" + qrticket + '\'' +
                ", device_type='" + device_type + '\'' +
                '}';
    }
}
