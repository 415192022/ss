package com.ueehome.logic.bean;

/**
 * Created by Ryan Wu on 2017/4/11.
 */
public class UEEIOTBean {


        private String uee_licence;

    public String getUee_licence() {
        return uee_licence;
    }

    public void setUee_licence(String uee_licence) {
        this.uee_licence = uee_licence;
    }

    @Override
    public String toString() {
        return "UEEIOTBean{" +
                "uee_licence='" + uee_licence + '\'' +
                '}';
    }
}
