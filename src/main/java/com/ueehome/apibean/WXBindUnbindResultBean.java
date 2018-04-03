package com.ueehome.apibean;

/**
 * Created by uee on 2018/1/5.
 */
public class WXBindUnbindResultBean {
    private int code;
    private String data;

    public WXBindUnbindResultBean() {
    }

    public WXBindUnbindResultBean(int code, String data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WXBindUnbindResultBean{" +
                "code='" + code + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
