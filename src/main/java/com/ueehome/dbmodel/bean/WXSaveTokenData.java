package com.ueehome.dbmodel.bean;

/**
 * Created by Mingwei Li on 2017/11/7.
 *保存token到数据库
 */
public class WXSaveTokenData {
    private String access_token;
    private Long expires_in;
    private Long remaining_expiration;
    private Long errcode;
    private String errmsg;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public void setRemaining_expiration(Long remaining_expiration) {
        this.remaining_expiration = remaining_expiration;
    }

    public void setErrcode(Long errcode) {
        this.errcode = errcode;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getAccess_token() {
        return access_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public Long getRemaining_expiration() {
        return remaining_expiration;
    }

    public Long getErrcode() {
        return errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    @Override
    public String toString() {
        return "WXSaveTokenData{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", remaining_expiration=" + remaining_expiration +
                ", errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
