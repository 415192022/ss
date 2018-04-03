package com.ueehome.aliiot.bean;

/**
 * Created by uee on 2018/2/8.
 */
public class AliIotRegistDevicePhoneBean extends AliBaseBean<AliIotRegistDevicePhoneBean> {
    private String requestId;
    private Boolean success;
    private String errorMessage;
    private String deviceId;
    private String deviceSecret;
    private String deviceStatus;
    private String deviceName;

    public AliIotRegistDevicePhoneBean() {
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceSecret() {
        return this.deviceSecret;
    }

    public void setDeviceSecret(String deviceSecret) {
        this.deviceSecret = deviceSecret;
    }

    public String getDeviceStatus() {
        return this.deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

}
