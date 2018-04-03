package com.ueehome.logic.bean;

public class RegisterUEEIOTLicenceData {
    public String uid;
    public String ueeLicence;
    public Integer remaining;

    /**
     * @param uid       产品唯一识别号
     * @param ueeLicence 剩余授权数量
     * @param remaining 剩余授权数量
     */
    public RegisterUEEIOTLicenceData(String uid, String ueeLicence, Integer remaining) {
        this.uid = uid;
        this.ueeLicence = ueeLicence;
        this.remaining = remaining;
    }
}
