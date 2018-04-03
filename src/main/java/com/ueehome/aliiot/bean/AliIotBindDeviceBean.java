package com.ueehome.aliiot.bean;

/**
 * Created by uee on 2018/2/7.
 */
public class AliIotBindDeviceBean extends AliBaseBean<AliIotBindDeviceBean>{
    private String id;
    private String phoneName;
    private String deviceName;
    private Integer isSharePhone;
    private Long usedTime;

    public void setId(String id) {
        this.id = id;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setUsedTime(Long usedTime) {
        this.usedTime = usedTime;
    }

    public String getId() {
        return id;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Long getUsedTime() {
        return usedTime;
    }

    public Integer getIsSharePhone() {
        return isSharePhone;
    }

    public void setIsSharePhone(Integer isSharePhone) {
        this.isSharePhone = isSharePhone;
    }

    @Override
    public String toString() {
        return "AliIotBindDeviceBean{" +
                "id='" + id + '\'' +
                ", phoneName='" + phoneName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", isSharePhone=" + isSharePhone +
                ", usedTime=" + usedTime +
                '}';
    }
}
