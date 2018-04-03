package com.ueehome.aliiot.bean;

/**
 * Created by uee on 2018/3/2.
 */
public class AliiotQueryPhoneNumberBean extends AliBaseBean<AliiotQueryPhoneNumberBean> {
    private String phoneNumber;
    private String phoneName;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    @Override
    public String toString() {
        return "AliiotQueryPhoneNumberBean{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", phoneName='" + phoneName + '\'' +
                '}';
    }
}
