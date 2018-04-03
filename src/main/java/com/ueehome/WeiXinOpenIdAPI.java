package com.ueehome;

import com.google.gson.Gson;
import com.ueehome.apibean.WXBindUnbindResultBean;
import com.ueehome.apibean.WXDeviceBean;
import com.ueehome.apibean.WXIOTResponse;
import com.ueehome.dbmodel.bean.WXSaveTokenData;
import com.ueehome.logic.Manufacture;
import com.ueehome.logic.bean.RegisterWXIOTLicenceData;
import com.ueehome.util.SignUtil;

import static spark.Spark.*;

/**
 * Created by uee on 2017/11/2.
 */
public class WeiXinOpenIdAPI {
    public static void init(){
        port(80);
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
        get("/getWXLicence/:password", (request, response) -> {
            String password = request.params(":password");
            if (password.equals("123456")) {
                throw new UEEException(2001002);
            }
            System.out.println(password +" getWXLicence GET");
            RegisterWXIOTLicenceData registerUEEIOTLicenceData = new Manufacture().registerWXIOTLicence(password);
            WXIOTResponse qqiotResponse =
                    new WXIOTResponse(registerUEEIOTLicenceData.deviceid, registerUEEIOTLicenceData.qrticket, registerUEEIOTLicenceData.device_type,registerUEEIOTLicenceData.remaining);
            return new Gson().toJson(qqiotResponse);
        });


        get("/getWXToken", (request, response) -> {
            WXSaveTokenData wxSaveTokenData=new Manufacture().getWXToken();
            return new Gson().toJson(wxSaveTokenData);
        });


        get("/updateURL", (request, response) -> {
            String last="";
            String nonce="";
            String timestamp="";
            String echostr="";
            String signature="";
            try {
                String re=request.raw().getQueryString();
                if(re.substring(0,1).equals("s")){
                    nonce=re.substring(re.lastIndexOf("&")+7,re.length());
                    last=re.substring(0,re.lastIndexOf("&"));

                    timestamp=last.substring(last.lastIndexOf("&")+11,last.length());
                    last=last.substring(0,last.lastIndexOf("&"));

                    echostr=last.substring(last.lastIndexOf("&")+9,last.length());
                    last=last.substring(0,last.lastIndexOf("&"));

                    signature=last.substring(last.lastIndexOf("&")+10,last.length());
                    String check=SignUtil.check(signature,timestamp,nonce,echostr);
                }else if(re.substring(0,1).equals("e")){
                    timestamp=re.substring(re.lastIndexOf("&")+11,re.length());
                    last=re.substring(0,re.lastIndexOf("&"));

                    signature=last.substring(last.lastIndexOf("&")+10,last.length());
                    last=last.substring(0,last.lastIndexOf("&"));

                    nonce=last.substring(last.lastIndexOf("&")+7,last.length());
                    last=last.substring(0,last.lastIndexOf("&"));

                    echostr=last.substring(last.lastIndexOf("&")+9,last.length());

                }
            }catch (Exception e ){
                return echostr;
            }
            return echostr;
        });
        post("/updateURL", (request, response) -> {
            String json="";
            String re=request.body();
            System.out.println(re+"====");
            if(!re.substring(0,1).equals("{")){
                return "";
            }
            WXDeviceBean wxDeviceBean=new Gson().fromJson(re,WXDeviceBean.class);
            System.out.println(wxDeviceBean);
            Boolean isS=new Manufacture().wxDeviceBindUnbindDevice(wxDeviceBean);

            if(isS){
                json=new Gson().toJson(new WXBindUnbindResultBean(0,"Success"));
            }else {
                json=new Gson().toJson(new WXBindUnbindResultBean(-1,"Failure"));
            }
            System.out.println(json);
            return json;
        });



        get("/getOpenId/:deviceId", (request, response) -> {
            String deviceId=request.params("deviceId");
            String json=new Gson().toJson(new Manufacture().wxGetOpenId(deviceId));
            System.out.println(json);
            return json;
        });


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
