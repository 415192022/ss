package com.ueehome.apibean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TangWei on 2017/3/27.
 * QQ物联返回实体
 */
public class QQIOTResponse {
    @SerializedName("uid")
    @Expose
    public String uid;
    @SerializedName("qq_guid")
    @Expose
    public String qqGuid;
    @SerializedName("qq_licence")
    @Expose
    public String qqLicence;
    @SerializedName("remaining")
    @Expose
    public Integer remaining;

    /**
     * @param uid       产品唯一识别号
     * @param qqGuid    QQ物联GUID
     * @param qqLicence QQ物联Licence
     * @param remaining 剩余授权数量
     */
    public QQIOTResponse(String uid, String qqGuid, String qqLicence, Integer remaining) {
        super();
        this.uid = uid;
        this.qqGuid = qqGuid;
        this.qqLicence = qqLicence;
        this.remaining = remaining;
    }
}
