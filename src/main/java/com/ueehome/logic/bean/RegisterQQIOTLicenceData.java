package com.ueehome.logic.bean;

/**
 * Created by TangWei on 2017/3/28.
 * 注册QQ物联Licence返回数据
 */
public class RegisterQQIOTLicenceData {
    public String uid;
    public String qqGuid;
    public String qqLicence;
    public Integer remaining;

    /**
     * @param uid       产品唯一识别号
     * @param qqGuid    QQ物联Licence
     * @param qqLicence 剩余授权数量
     * @param remaining 剩余授权数量
     */
    public RegisterQQIOTLicenceData(String uid, String qqGuid, String qqLicence, Integer remaining) {
        this.uid = uid;
        this.qqGuid = qqGuid;
        this.qqLicence = qqLicence;
        this.remaining = remaining;
    }
}
