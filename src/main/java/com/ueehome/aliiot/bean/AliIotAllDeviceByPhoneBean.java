package com.ueehome.aliiot.bean;

/**
 * Created by uee on 2018/2/7.
 */
public class AliIotAllDeviceByPhoneBean extends AliBaseBean<AliIotAllDeviceByPhoneBean>{
    private String deviceName;
    private String phoneName;
    private Long useTime;
    private String ueePhoneNumber;

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public void setUseTime(Long useTime) {
        this.useTime = useTime;
    }

    public void setUeePhoneNumber(String ueePhoneNumber) {
        this.ueePhoneNumber = ueePhoneNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public Long getUseTime() {
        return useTime;
    }

    public String getUeePhoneNumber() {
        return ueePhoneNumber;
    }

    @Override
    public String toString() {
        return "AliIotAllDeviceByPhoneBean{" +
                "deviceName='" + deviceName + '\'' +
                ", phoneName='" + phoneName + '\'' +
                ", useTime=" + useTime +
                ", ueePhoneNumber='" + ueePhoneNumber + '\'' +
                '}';
    }
}
