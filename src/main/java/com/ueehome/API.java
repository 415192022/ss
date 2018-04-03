package com.ueehome;

import com.google.gson.Gson;
import com.ueehome.apibean.QQIOTResponse;
import com.ueehome.apibean.UEEIOTResponse;
import com.ueehome.apibean.UploadLogBean;
import com.ueehome.logic.Manufacture;
import com.ueehome.logic.bean.RegisterQQIOTLicenceData;
import com.ueehome.logic.bean.RegisterUEEIOTLicenceData;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.MultipartConfigElement;
import java.io.FileOutputStream;
import java.io.InputStream;

import static spark.Spark.*;

/**
 * Created by TangWei on 2017/3/29.
 * API
 */
class API {
    static void init() {
        port(5678);
        //全局前处理
        before((request, response) -> {
            //Content-Type验证
            if (request.requestMethod().equals("POST") || request.requestMethod().equals("PUT") || request.requestMethod().equals("DELETE")) {
                String contentType = request.headers("Content-Type");
                if (contentType == null || !contentType.equals("application/json")){
//                    throw new UEEException(1000004);
                }
            }
        });

        //API开始-----------------------------------------------------------------------
        get("/v1/manufacture/qqiot/:password", (request, response) -> {
            String password = request.params(":password");
            System.out.println(password+"GET QQ");
            if (password.equals("123456"))
                throw new UEEException(2001002);

            RegisterQQIOTLicenceData registerQQIOTLicenceData = new Manufacture().registerQQIOTLicence(password);

            QQIOTResponse qqiotResponse =
                    new QQIOTResponse(registerQQIOTLicenceData.uid, registerQQIOTLicenceData.qqGuid,
                            registerQQIOTLicenceData.qqLicence, registerQQIOTLicenceData.remaining);
            return new Gson().toJson(qqiotResponse);
        });

        post("/v1/manufacture/qqiot/:password", (request, response) -> {
            System.out.println("=-=============== POST QQ");
            String password = request.params(":password");
            System.out.println(password);
            String data = request.body();
            new Manufacture().importQQIOTLicence(password, data);
            return halt(200);
        });






        //Test  Post  eg:http://127.0.0.1:4567/a/1232314234 使用Post请求需要添加Header  Content-Type:application/json
        get("/v1/manufacture/ueeiot/:password", (request, response) -> {
            String password = request.params(":password");
            if (password.equals("123456")) {
                throw new UEEException(2001002);
            }
            System.out.println(password +" UEE GET");

            RegisterUEEIOTLicenceData registerQQIOTLicenceData = new Manufacture().registerUEEIOTLicence(password);

            UEEIOTResponse qqiotResponse =
                    new UEEIOTResponse(registerQQIOTLicenceData.uid, registerQQIOTLicenceData.ueeLicence, registerQQIOTLicenceData.remaining);
            return new Gson().toJson(qqiotResponse);
        });



        post("/v1/manufacture/ueeiot/:password", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                System.out.println("=-===============");
                String password = request.params(":password");
                System.out.println(password+" UEE POST");
                String data = request.body();
                new Manufacture().importUEEIOTLicence(password, data);
                return halt(200);
            }
        });

        post("/uploadlog/", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                System.out.println("post: ");
                if (request.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
                    MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
                    request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
                }
                String info=request.raw().getParameter("info");
                System.out.println("post: "+info);
                UploadLogBean uploadLogBean=new Gson().fromJson(info,UploadLogBean.class);
                FileOutputStream fileOutputStream =new FileOutputStream("/MyLog/"+uploadLogBean.getFileName());
                InputStream inputStream=request.raw().getPart("file").getInputStream();
                System.out.println("InputStream: "+inputStream.available());
                int len=inputStream.available();
                int totle=0;
                byte[] buffer=new byte[len];
                System.out.println("len: "+len);
                inputStream.read(buffer);
                fileOutputStream.write(buffer,0,len);
                fileOutputStream.flush();
                System.out.println("总长度："+totle);
                fileOutputStream.close();
                inputStream.close();
                System.out.println("post: "+inputStream);
                return new Gson().toJson(uploadLogBean);
            }
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
