package com.ueehome;

import com.google.gson.Gson;
import com.ueehome.apibean.UEEError;

import java.util.ArrayList;

import static spark.Spark.halt;

/**
 * Created by TangWei on 2017/3/27.
 * 错误处理器
 */
class ErrorHandler {
    private static ArrayList<UEEError> errorList;

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
        errorList.add(new UEEError(2002002, "缺少License"));
        errorList.add(new UEEError(2002003, "参数格式校验不通过"));
        errorList.add(new UEEError(2002004, "GUID不唯一"));
        errorList.add(new UEEError(2002005, "License不唯一"));
    }

    static String handlerErrorCode(Integer errorCode) {
        for (UEEError ueeError : errorList) {
            if (ueeError.errorCode.equals(errorCode))
                return new Gson().toJson(ueeError);
        }
        throw halt(500, "Undefined ErrorCode");
    }
}
