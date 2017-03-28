package com.ueehome.apibean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TangWei on 2017/3/27.
 * UEE API错误实体
 */
public class UEEError {
    @SerializedName("error_code")
    @Expose
    public Integer errorCode;
    @SerializedName("error_discription")
    @Expose
    public String errorDiscription;

    /**
     * UEE API错误实体
     *
     * @param errorCode        错误码
     * @param errorDiscription 错误描述
     */
    public UEEError(Integer errorCode, String errorDiscription) {
        this.errorCode = errorCode;
        this.errorDiscription = errorDiscription;
    }
}
