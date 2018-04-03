package com.ueehome.apibean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by uee on 2017/11/6.
 */
public class WXIOTResponse {
    @SerializedName("deviceid")
    @Expose
    public String deviceid;


    @SerializedName("qrticket")
    @Expose
    public String qrticket;

    @SerializedName("device_type")
    @Expose
    public String device_type;


    @SerializedName("remaining")
    @Expose
    public Integer remaining;

    public String getDeviceid() {
        return deviceid;
    }

    public String getQrticket() {
        return qrticket;
    }

    public String getDevice_type() {
        return device_type;
    }

    public Integer getRemaining() {
        return remaining;
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

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    @Override
    public String toString() {
        return "UEEIOTResponse{" +
                "deviceid='" + deviceid + '\'' +
                ", qrticket='" + qrticket + '\'' +
                ", device_type='" + device_type + '\'' +
                ", remaining=" + remaining +
                '}';
    }
    public WXIOTResponse(String deviceid,  String qrticket,String device_type, Integer remaining) {
        super();
        this.deviceid = deviceid;
        this.qrticket = qrticket;
        this.device_type=device_type;
        this.remaining = remaining;
    }
}
