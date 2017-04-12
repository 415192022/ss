package com.ueehome.apibean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ryan Wu on 2017/4/11.
 */
public class QQIOTImportResponse {
    @SerializedName("result")
    @Expose
    public String result;

    /**
     * @param result 结果
     */
    public QQIOTImportResponse(String result) {
        super();
        this.result = result;
    }
}
