package com.ueehome.aliiot.bean;

/**
 * Created by uee on 2018/3/6.
 */
public class AliIotQueryShareDeviceBean extends AliBaseBean<AliIotQueryShareDeviceBean> {
    private String ueePhoneNumber;
    private String shareDevice;
    private String sharePhone;
    private String masterPhoneName;
    private Long useTime;

    public void setUeePhoneNumber(String ueePhoneNumber) {
        this.ueePhoneNumber = ueePhoneNumber;
    }

    public void setShareDevice(String shareDevice) {
        this.shareDevice = shareDevice;
    }

    public void setSharePhone(String sharePhone) {
        this.sharePhone = sharePhone;
    }

    public void setMasterPhoneName(String masterPhoneName) {
        this.masterPhoneName = masterPhoneName;
    }

    public String getUeePhoneNumber() {
        return ueePhoneNumber;
    }

    public String getShareDevice() {
        return shareDevice;
    }

    public String getSharePhone() {
        return sharePhone;
    }

    public String getMasterPhoneName() {
        return masterPhoneName;
    }

    public Long getUseTime() {
        return useTime;
    }

    public void setUseTime(Long useTime) {
        this.useTime = useTime;
    }

    @Override
    public String toString() {
        return "AliIotQueryShareDeviceBean{" +
                "ueePhoneNumber='" + ueePhoneNumber + '\'' +
                ", shareDevice='" + shareDevice + '\'' +
                ", sharePhone='" + sharePhone + '\'' +
                ", masterPhoneName='" + masterPhoneName + '\'' +
                ", useTime='" + useTime + '\'' +
                '}';
    }
}
