package com.ueehome;

import com.google.gson.Gson;
import com.ueehome.aliiot.AliIotImpl;
import com.ueehome.aliiot.IotClient;
import com.ueehome.aliiot.bean.AliBaseBean;
import com.ueehome.aliiot.bean.DeviceIsExsitBean;
import com.ueehome.aliiot.bean.QueryDeviceStateReceiveBean;
import com.ueehome.aliiot.impl.AliIotDataSourceImpl;
import com.ueehome.util.LogUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

/**
 * Created by uee on 2018/1/29.
 */
public class AliIOTApi {
    public static void init(){
        port(7410);
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



        //send a message by devicename  get
        get("/alisendmessage/:devicename/:content", (request, response) -> {
            String devicename=request.params("devicename");
            String content=request.params("content");
            LogUtil.print(devicename+"===========");
            String productKey = IotClient.getProductKey();
            String topic = "/" + productKey + "/" + devicename + "/regeister";
            System.out.println(topic);
            return AliIotImpl.pubTest(productKey, topic, content);
        });


        //send a message by devicename  post
//        post("/alisendmessage/:jsonParms", (request, response) -> {
//            System.out.println("=======");
//            String json=request.raw().getParameter("jsonParms");
//            SendMessageBean sendMessageBean=new Gson().fromJson(json,SendMessageBean.class);
//            String devicename=sendMessageBean.getDeviceName();
//            String content=sendMessageBean.getContent();
//            LogUtil.print(devicename+"===========");
//            String productKey = IotClient.getProductKey();
//            String topic = "/" + productKey + "/" + devicename + "/regeister";
//            System.out.println(topic);
//            return AliIotImpl.pubTest(productKey, topic, content);
//        });
//        post("/alisendmessage", (request, response) -> {
//            String parms="jsonParms";
//            String re=request.body();
//            byte[] bytes=parms.getBytes();
//            String json=re.substring(bytes.length+1);
//            System.out.println(json);
//
//            SendMessageBean sendMessageBean=new Gson().fromJson(json,SendMessageBean.class);
//            String devicename=sendMessageBean.getDeviceName();
//            String content=sendMessageBean.getContent();
//            LogUtil.print(devicename+"===========");
//            String productKey = IotClient.getProductKey();
//            String topic = "/" + productKey + "/" + devicename + "/regeister";
//            System.out.println(topic);
//            return AliIotImpl.pubTest(productKey, topic, content);
//        });

        //obtain device state
        get("/alidevicestate/:devicename", (request, response) -> {
            System.out.println("========="+request.ip());
            String devicename=request.params("devicename");
            String productKey = IotClient.getProductKey();
            List<String> deviceList=new ArrayList<>();
            deviceList.add(devicename);
            return  AliIotImpl.batchGetDeviceStatusTest(productKey,deviceList);
        });

        /**
         * 根据传入多个设备名使用json格式封装参数查询多个设备
         */
        get("/querydevicenamebyjsons/:devicenamejsons", (request, response) -> {
            System.out.println("========="+request.ip());
            String devicename=request.params("devicenamejsons");
            String productKey = IotClient.getProductKey();
            QueryDeviceStateReceiveBean queryDeviceStateReceiveBean=new Gson().fromJson(devicename,QueryDeviceStateReceiveBean.class);
            List<String> deviceList=new ArrayList<>();
            for(QueryDeviceStateReceiveBean.DeviceName dn:queryDeviceStateReceiveBean.getDeviceNames()){
                deviceList.add(dn.getName());
            }
            return  AliIotImpl.batchGetDeviceStatusTest(productKey,deviceList);
        });


        /**
         * 在Ali后台注册设备
         */
        get("/registerDevice/:newDeviceName", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String deviceName=request.params("newDeviceName");
                String productKey = IotClient.getProductKey();
                AliIotDataSourceImpl aliIotDataSource=new AliIotDataSourceImpl();
                return new Gson().toJson(aliIotDataSource.registerDevice(productKey,deviceName));
            }
        });

        /**
         * 在Ali后台注册手机设备，根据手机号、手机名建立一对一关系
         */
        get("/registerPhone/:phoneNumber/:phoneName", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String phoneNumber=request.params("phoneNumber");
                String phoneName=request.params("phoneName");
                String productKey = IotClient.getProductKey();
                AliIotDataSourceImpl aliIotDataSource=new AliIotDataSourceImpl();
                String json=new Gson().toJson(aliIotDataSource.registerPhone(productKey,phoneNumber,phoneName));
                return json;
            }
        });

        /**
         * 查询手机、设备是否存在，以及deviceSecret
         */
        get("/queryDeviceIsExist/:deviceName", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String productKey = IotClient.getProductKey();
                String devicename=request.params("deviceName");
               String json= AliIotImpl.queryDeviceByNameTest(productKey,devicename);
                DeviceIsExsitBean deviceIsExsitBean=new Gson().fromJson(json,DeviceIsExsitBean.class);
                System.out.println(deviceIsExsitBean.getDeviceInfo().getDeviceName()==null);
                return json;
            }
        });

        /**
         * 根据设备名和手机名建立绑定关系
         */
        get("/bindDevice/:deviceName/:phoneName", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String devicename=request.params("deviceName");
                String phoneName=request.params("phoneName");
                AliIotDataSourceImpl aliIotDataSource=new AliIotDataSourceImpl();
                return new Gson().toJson(aliIotDataSource.bindDevice(phoneName,devicename));
            }
        });
        /**
         * 解绑设备
         */
        get("unbindDevice/:deviceName", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String deviceName=request.params("deviceName");
                AliIotDataSourceImpl aliIotDataSource=new AliIotDataSourceImpl();
                AliBaseBean aliBaseBean= aliIotDataSource.unbindDevice(deviceName);
                String json=new Gson().toJson(aliBaseBean);
                return json;
            }
        });

        /**
         * 根据手机号查询所有绑定的设备
         */
        get("/queryAllDeviceByPhoneNumber/:phoneNumber", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String phoneNumber=request.params("phoneNumber");
                AliIotDataSourceImpl aliIotDataSource=new AliIotDataSourceImpl();
                return new Gson().toJson(aliIotDataSource.queryAllDeviceByPhoneNumber(phoneNumber));
            }
        });

        /**
         * 根据设备名称查询该设备是否被绑定
         */
        get("/deviceIsBound/:deviceName", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String deviceName=request.params("deviceName");
                AliIotDataSourceImpl aliIotDataSource=new AliIotDataSourceImpl();
                AliBaseBean aliBaseBean=aliIotDataSource.deviceIsBound(deviceName);
                String json=new Gson().toJson(aliBaseBean);
                return  json;
            }
        });

        /**
         *通过手机名查询所绑定的手机号
         */
        get("/queryPhoneNumber/:phoneName", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String phoneName=request.params("phoneName");
                AliIotDataSourceImpl aliIotDataSource=new AliIotDataSourceImpl();
                AliBaseBean aliBaseBean=aliIotDataSource.querryPhoneNumber(phoneName);
                String json=new Gson().toJson(aliBaseBean);
                return json;
            }
        });

        /**
         * 分享设备
         */
        get("/sharePhone/:masterPhone/:sharePhone/:deviceName", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String masterPhone=request.params("masterPhone");
                String sharePhone=request.params("sharePhone");
                String deviceName=request.params("deviceName");
                AliIotDataSourceImpl aliIotDataSource=new AliIotDataSourceImpl();
               AliBaseBean aliBaseBean= aliIotDataSource.sharePhone(masterPhone,sharePhone,deviceName);
                String json=new Gson().toJson(aliBaseBean);
                return json;
            }
        });

        /**
         * 取消分享设备
         */
        get("/cancelShareDevice/:masterPhone/:sharePhone/:deviceName", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String masterPhone=request.params("masterPhone");
                String sharePhone=request.params("sharePhone");
                String deviceName=request.params("deviceName");
                AliIotDataSourceImpl aliIotDataSource=new AliIotDataSourceImpl();
                AliBaseBean aliBaseBean= aliIotDataSource.cancelSharePhone(masterPhone,sharePhone,deviceName);
                String json=new Gson().toJson(aliBaseBean);
                return json;
            }
        });

        /**
         * 查询当前登录用户有没有被分享的设备
         */
        get("queryShareDeviceByPhoneNumber/:phoneNumber", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String phoneNumber=request.params("phoneNumber");
                AliIotDataSourceImpl aliIotDataSource=new AliIotDataSourceImpl();
                AliBaseBean aliBaseBean=aliIotDataSource.querryShareDeviceByPhoneNumber(phoneNumber);
                String json=new Gson().toJson(aliBaseBean);
                return json;
            }
        });

        /**
         * 主控手机通过设备名查询该设备分享给哪些手机
         */
        get("queryDeviceShareToPhone/:masterPhoneName/:deviceName", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String masterPhoneName=request.params("masterPhoneName");
                String deviceName=request.params("deviceName");
                AliIotDataSourceImpl aliIotDataSource=new AliIotDataSourceImpl();
                AliBaseBean aliBaseBean=aliIotDataSource.queryDeviceShareToPhone(masterPhoneName,deviceName);
                String json=new Gson().toJson(aliBaseBean);
                return json;
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
