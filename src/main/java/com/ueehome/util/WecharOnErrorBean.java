package com.ueehome.util;

/**
 * Created by uee on 2017/11/3.
 */
public class WecharOnErrorBean {
    private String errir_code;

    public String getErrir_code() {
        return errir_code;
    }

    public void setErrir_code(String errir_code) {
        this.errir_code = errir_code;
    }

    @Override
    public String toString() {
        return "WecharOnErrorBean{" +
                "errir_code='" + errir_code + '\'' +
                '}';
    }
}
