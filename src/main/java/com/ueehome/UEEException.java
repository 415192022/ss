package com.ueehome;

import com.google.gson.Gson;
import com.ueehome.apibean.UEEError;

import java.util.ArrayList;

import static spark.Spark.halt;

/**
 * Created by TangWei on 2017/4/12.
 * 异常
 */
public class UEEException extends Exception {
    private static ArrayList<UEEError> errorList;
    private Integer errorCode;

    static void init() {
        errorList = new ArrayList<>();
        //全局
        errorList.add(new UEEError(1000001, "接口不存在"));
        errorList.add(new UEEError(1000002, "JSON格式错误"));
        errorList.add(new UEEError(1000003, "服务器内部错误"));
        errorList.add(new UEEError(1000004, "POST PUT DELETE请求Content-Type错误"));

        //GET /manufacture/qqiot/:password
        errorList.add(new UEEError(2001001, "缺少password参数"));
        errorList.add(new UEEError(2001002, "password格式校验不通过"));
        errorList.add(new UEEError(2001003, "password错误或不存在"));
        errorList.add(new UEEError(2001004, "password授权数已用完"));
        errorList.add(new UEEError(2001005, "password授权已过期"));
        errorList.add(new UEEError(2001006, "password授权已关闭"));
        errorList.add(new UEEError(2001007, "QQ物联授权已用完"));

        //POST /manufacture/qqiot/
        errorList.add(new UEEError(2002001, "缺少GUID"));
        errorList.add(new UEEError(2002002, "缺少Licence"));
        errorList.add(new UEEError(2002003, "参数格式校验不通过"));
        errorList.add(new UEEError(2002004, "GUID或Licence不唯一"));
        errorList.add(new UEEError(2002005, "一次批量插入不能超过1000条"));
        errorList.add(new UEEError(2002006, "密码错误"));

    }

    public UEEException(Integer errorCode) {
        super(errorCode + "");
        this.errorCode = errorCode;
    }

    public String getDetailJson() {
        for (UEEError ueeError : errorList) {
            if (ueeError.errorCode.equals(errorCode))
                return new Gson().toJson(ueeError);
        }
        throw halt(500, "Undefined ErrorCode");
    }
}
