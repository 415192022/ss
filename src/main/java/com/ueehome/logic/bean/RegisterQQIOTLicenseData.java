package com.ueehome.logic.bean;

/**
 * Created by TangWei on 2017/3/28.
 * 注册QQ物联License返回数据
 */
public class RegisterQQIOTLicenseData {
    public String uid;
    public String qqGuid;
    public String qqLicense;

    /**
     * @param uid       产品唯一识别号
     * @param qqGuid    QQ物联License
     * @param qqLicense 剩余授权数量
     */
    public RegisterQQIOTLicenseData(String uid, String qqGuid, String qqLicense) {
        this.uid = uid;
        this.qqGuid = qqGuid;
        this.qqLicense = qqLicense;
    }
}
