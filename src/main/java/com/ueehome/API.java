package com.ueehome;

import com.google.gson.Gson;
import com.ueehome.apibean.QQIOTResponse;
import com.ueehome.logic.Manufacture;
import com.ueehome.logic.bean.RegisterQQIOTLicenceData;

import static spark.Spark.*;

/**
 * Created by TangWei on 2017/3/29.
 * API
 */
class API {
    static void init(){
        port(8080);

        //全局前处理
        before((request, response) -> {
            //Content-Type验证
            if (request.requestMethod().equals("POST") || request.requestMethod().equals("PUT") || request.requestMethod().equals("DELETE")) {
                String contentType = request.headers("Content-Type");
                if (contentType == null || !contentType.equals("application/json"))
                    throw halt(400, ErrorHandler.handlerErrorCode(1000004));
            }
        });

        //API开始-----------------------------------------------------------------------
        get("/v1/manufacture/qqiot/:password", (request, response) -> {
            String password = request.params(":password");
            if (password.equals("123456"))
                throw halt(400, ErrorHandler.handlerErrorCode(2001002));

            RegisterQQIOTLicenceData registerQQIOTLicenceData = new Manufacture().registerQQIOTLicence(password);

            QQIOTResponse qqiotResponse =
                    new QQIOTResponse(registerQQIOTLicenceData.uid, registerQQIOTLicenceData.qqGuid, registerQQIOTLicenceData.qqLicence, 20);
            return new Gson().toJson(qqiotResponse);
        });

        post("/v1/manufacture/qqiot", (request, response) -> "");

        //API结束-----------------------------------------------------------------------

        //全局后处理
        after((request, response) -> {
            response.header("Content-Type", "application/json");
            response.header("Content-Encoding", "gzip");
        });
    }
}
