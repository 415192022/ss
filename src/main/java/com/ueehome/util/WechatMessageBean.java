package com.ueehome.util;


/**
 * Created by Zan on 2017/11/2 0002.
 * WechatMessageBean.
 */

public class WechatMessageBean {
    private BaseResponse baseResponse;
    private String deviceid;
    private String qrticket;

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public void setQrticket(String qrticket) {
        this.qrticket = qrticket;
    }

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public String getQrticket() {
        return qrticket;
    }

    @Override
    public String toString() {
        return "WechatMessageBean{" +
                "baseResponse=" + baseResponse +
                ", deviceid='" + deviceid + '\'' +
                ", qrticket='" + qrticket + '\'' +
                '}';
    }

    public class BaseResponse{
        private String errcode;
        private String errmsg;

        public void setErrcode(String errcode) {
            this.errcode = errcode;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        public String getErrcode() {
            return errcode;
        }

        public String getErrmsg() {
            return errmsg;
        }

        @Override
        public String toString() {
            return "BaseResponse{" +
                    "errcode='" + errcode + '\'' +
                    ", errmsg='" + errmsg + '\'' +
                    '}';
        }
    }
}
