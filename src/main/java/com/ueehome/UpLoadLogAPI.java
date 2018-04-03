package com.ueehome;

import com.google.gson.Gson;
import com.ueehome.apibean.UploadLogBean;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.MultipartConfigElement;
import java.io.FileOutputStream;
import java.io.InputStream;

import static spark.Spark.*;

/**
 * Created by Mingwei Li on 2017/11/6.
 */
public class UpLoadLogAPI {
    private static final String PATH_SAVE_LINUX="/MyLog/";
    private static final String PATH_SAVE_WINDOWS="D:\\";
    public static void init(){
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
        post("/uploadlog/:info", new Route() {
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
                String fileinfo = request.params(":info");
                FileOutputStream fileOutputStream =new FileOutputStream(PATH_SAVE_WINDOWS+fileinfo);
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


        //全局后处理
        after((request, response) -> {
            response.header("Content-Type", "application/json; charset=utf-8");
            response.header("Content-Type", "multipart/form-data");
            response.header("Content-Encoding", "gzip");
        });

//        exception(UEEException.class, (exception, request, response) -> {
//            response.header("Content-Type", "application/json; charset=utf-8");
//            response.header("Content-Encoding", "gzip");
//            response.status(400);
//            response.body(((UEEException) exception).getDetailJson());
//        });
    }
}
