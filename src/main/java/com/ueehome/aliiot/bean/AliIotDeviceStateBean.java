package com.ueehome.aliiot.bean;

/**
 * Created by uee on 2018/1/30.
 */
public class AliIotDeviceStateBean {
    private String payload;
    private String messagetype;
    private long messageid;
    private int timestamp;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(String messagetype) {
        this.messagetype = messagetype;
    }

    public long getMessageid() {
        return messageid;
    }

    public void setMessageid(long messageid) {
        this.messageid = messageid;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "AliIotDeviceStateBean{" +
                "payload='" + payload + '\'' +
                ", messagetype='" + messagetype + '\'' +
                ", messageid=" + messageid +
                ", timestamp=" + timestamp +
                '}';
    }

    public static class PayloadBean{
        private String lastTime;
        private String time;
        private String productKey;
        private String deviceName;
        private String status;

        public String getLastTime() {
            return lastTime;
        }

        public void setLastTime(String lastTime) {
            this.lastTime = lastTime;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getProductKey() {
            return productKey;
        }

        public void setProductKey(String productKey) {
            this.productKey = productKey;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Payload{" +
                    "lastTime='" + lastTime + '\'' +
                    ", time='" + time + '\'' +
                    ", productKey='" + productKey + '\'' +
                    ", deviceName='" + deviceName + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

}
