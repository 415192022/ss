package com.ueehome.logic.bean;

public class RegisterWXIOTLicenceData {
    public String deviceid;
    public String qrticket;
    public String device_type;
    public Integer remaining;


    public RegisterWXIOTLicenceData(String deviceid, String qrticket,String device_type,Integer remaining) {
        this.deviceid = deviceid;
        this.qrticket = qrticket;
        this.device_type=device_type;
        this.remaining = remaining;
    }
}
