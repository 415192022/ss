package com.ueehome.apibean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UEEIOTResponse {
    @SerializedName("uid")
    @Expose
    public String uid;


    @SerializedName("uee_licence")
    @Expose
    public String ueeLicence;

    @SerializedName("remaining")
    @Expose
    public Integer remaining;

    /**
     * @param uid       产品唯一识别号
     * @param ueeLicence Licence
     * @param remaining 剩余授权数量
     */
    public UEEIOTResponse(String uid,  String ueeLicence, Integer remaining) {
        super();
        this.uid = uid;
        this.ueeLicence = ueeLicence;
        this.remaining = remaining;
    }
}
