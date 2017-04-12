package com.ueehome;

import com.google.gson.Gson;
import com.ueehome.apibean.QQIOTImportResponse;
import com.ueehome.apibean.QQIOTResponse;
import com.ueehome.logic.Manufacture;
import com.ueehome.logic.bean.RegisterQQIOTLicenceData;

import static spark.Spark.*;

/**
 * Created by TangWei on 2017/3/29.
 * API
 */
class API {
    static void init() {
        port(4567);

        //全局前处理
        before((request, response) -> {
            //Content-Type验证
            if (request.requestMethod().equals("POST") || request.requestMethod().equals("PUT") || request.requestMethod().equals("DELETE")) {
                String contentType = request.headers("Content-Type");
                if (contentType == null || !contentType.equals("application/json"))
                    throw new UEEException(1000004);
            }
        });

        //API开始-----------------------------------------------------------------------
        get("/v1/manufacture/qqiot/:password", (request, response) -> {
            String password = request.params(":password");
            if (password.equals("123456"))
                throw new UEEException(2001002);

            RegisterQQIOTLicenceData registerQQIOTLicenceData = new Manufacture().registerQQIOTLicence(password);

            QQIOTResponse qqiotResponse =
                    new QQIOTResponse(registerQQIOTLicenceData.uid, registerQQIOTLicenceData.qqGuid,
                            registerQQIOTLicenceData.qqLicence, registerQQIOTLicenceData.remaining);
            return new Gson().toJson(qqiotResponse);
        });

        post("/v1/manufacture/qqiot/:password", (request, response) -> {
            String password = request.params(":password");
            String data = request.body();
            new Manufacture().importQQIOTLicence(password,data);
            return halt(200);
        });

        //API结束-----------------------------------------------------------------------

        //全局后处理
        after((request, response) -> {
            response.header("Content-Type", "application/json; charset=utf-8");
            response.header("Content-Encoding", "gzip");
        });

        exception(UEEException.class, (exception, request, response) -> {
            response.header("Content-Type", "application/json; charset=utf-8");
            response.header("Content-Encoding", "gzip");
            response.status(400);
            response.body(((UEEException) exception).getDetailJson());
        });
    }
}
