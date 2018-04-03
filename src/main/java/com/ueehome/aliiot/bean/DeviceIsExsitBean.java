package com.ueehome.aliiot.bean;

/**
 * Created by uee on 2018/2/6.
 */
public class DeviceIsExsitBean {

    private DeviceInfoBean deviceInfo;
    private String requestId;
    private String errorMessage;
    private boolean success;

    public DeviceInfoBean getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoBean deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    @Override
    public String toString() {
        return "DeviceIsExsitBean{" +
                "deviceInfo=" + deviceInfo +
                ", requestId='" + requestId + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", success=" + success +
                '}';
    }

    public static class DeviceInfoBean {

        private String deviceId;
        private String deviceName;
        private String deviceSecret;
        private String gmtCreate;
        private String productKey;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getDeviceSecret() {
            return deviceSecret;
        }

        public void setDeviceSecret(String deviceSecret) {
            this.deviceSecret = deviceSecret;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getProductKey() {
            return productKey;
        }

        public void setProductKey(String productKey) {
            this.productKey = productKey;
        }

        @Override
        public String toString() {
            return "DeviceInfoBean{" +
                    "deviceId='" + deviceId + '\'' +
                    ", deviceName='" + deviceName + '\'' +
                    ", deviceSecret='" + deviceSecret + '\'' +
                    ", gmtCreate='" + gmtCreate + '\'' +
                    ", productKey='" + productKey + '\'' +
                    '}';
        }
    }
}
