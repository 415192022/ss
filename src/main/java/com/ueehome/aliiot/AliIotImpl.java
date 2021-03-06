package com.ueehome.aliiot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.iot.model.v20170420.*;
import com.google.gson.Gson;
import com.ueehome.util.LogUtil;
import org.apache.commons.codec.binary.Base64;

import java.util.*;


/**
 * Created by uee on 2018/1/29.
 */
public class AliIotImpl extends BaseAliIot {
    private static long shadowVersion = 1;
    /**
     * 创建产品
     *
     * @param productName 产品名称
     * @param productDesc 产品描述 可空
     * @return 产品的PK
     */
    public static String createProductTest(String productName, String productDesc) {
        CreateProductRequest request = new CreateProductRequest();
        request.setName(productName);
        request.setDesc(productDesc);
        CreateProductResponse response = (CreateProductResponse)executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("创建产品成功！productKey:" + response.getProductInfo().getProductKey());
            return response.getProductInfo().getProductKey();
        } else {
            LogUtil.error("创建产品失败！requestId:" + response.getRequestId() + "原因:" + response.getErrorMessage());
        }
        return null;
    }

    /**
     * 修改产品
     *
     * @param productKey 产品PK 必需
     * @param productName 产品名称 非必需
     * @param productDesc 产品描述 非必需
     */
    public static void updateProductTest(String productKey, String productName, String productDesc) {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setProductKey(productKey);
        request.setProductName(productName);
        request.setProductDesc(productDesc);
        UpdateProductResponse response = (UpdateProductResponse)executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("修改产品成功！");
        } else {
            LogUtil.error("修改产品失败！requestId:" + response.getRequestId() + "原因:" + response.getErrorMessage());
        }

    }

    /**
     * 注册设备
     *
     * @param productKey 产品pk
     * @param deviceName 设备名称 非必须
     * @return 设备名称
     */
    public static RegistDeviceResponse registDeviceTest(String productKey, String deviceName) {
        RegistDeviceRequest request = new RegistDeviceRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        RegistDeviceResponse response = (RegistDeviceResponse)executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print(
                    "创建设备成功！deviceName:" + response.getDeviceName() + ",deviceSecret:" + response.getDeviceSecret());
            return response;
        } else {
            LogUtil.error("创建设备失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());
        }
        return response;
    }

    /**
     * 查询设备列表
     *
     * @param productKey 产品PK
     * @param pageSize 每页显示条数 非必须
     * @param currentPage 当前页 非必须
     */
    public static void QueryDeviceTest(String productKey, Integer pageSize, Integer currentPage) {
        QueryDeviceRequest request = new QueryDeviceRequest();
        request.setProductKey(productKey);
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        QueryDeviceResponse response = (QueryDeviceResponse)executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("查询设备成功！ " + JSONObject.toJSONString(response));
        } else {
            LogUtil.error("查询设备失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());
        }

    }

    //查询设备是否存在
    public static String queryDeviceByNameTest(String productKey, String deviceName) {
        QueryDeviceByNameRequest request = new QueryDeviceByNameRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        QueryDeviceByNameResponse response = (QueryDeviceByNameResponse)executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("查询设备成功！ " + JSONObject.toJSONString(response));
        } else {
            LogUtil.error("查询设备失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());
        }
        return new Gson().toJson(response);
    }

    /**
     * 批量查询设备状态
     *
     * @param productKey 产品pk
     * @param deviceNames 设备名称集合
     */
    public static String batchGetDeviceStatusTest(String productKey, List<String> deviceNames) {
        BatchGetDeviceStateRequest request = new BatchGetDeviceStateRequest();
        request.setProductKey(productKey);
        request.setDeviceNames(deviceNames);
        BatchGetDeviceStateResponse response = (BatchGetDeviceStateResponse)executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("查询设备成功！ " + JSONObject.toJSONString(response));
        } else {
            LogUtil.error("查询设备失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());
        }


        return new Gson().toJson(response);
    }


    /**
     * 批量申请设备
     *
     * @param productKey 产品pk
     * @return 申请单id
     */
    public static Long applyDeviceWithNamesTest(String productKey, List<String> deviceNames) {
        ApplyDeviceWithNamesRequest request = new ApplyDeviceWithNamesRequest();
        request.setProductKey(productKey);
        request.setDeviceNames(deviceNames);
        ApplyDeviceWithNamesResponse response = (ApplyDeviceWithNamesResponse)executeTest(request);

        if (response != null && response.getSuccess() != false) {
            LogUtil.print("批量申请设备成功！ applyId: " + response.getApplyId());
            return response.getApplyId();

        } else {
            LogUtil.error("批量申请设备失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());

        }
        return null;

    }

    /**
     * 查询申请单是否执行完毕
     *
     * @param applyId 申请单id
     * @return 是否执行完毕
     */
    public static Boolean queryApplyStatusTest(Long applyId) {
        QueryApplyStatusRequest request = new QueryApplyStatusRequest();
        request.setApplyId(applyId);
        QueryApplyStatusResponse response = (QueryApplyStatusResponse)executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("查询申请单执行状态成功！ 是否执行完成: " + response.getFinish());
            return response.getFinish();
        } else {
            LogUtil.error("查询申请单执行状态失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());

        }

        return null;
    }

    /**
     * 分页查询批量申请的设备
     *
     * @param applyId 申请单id
     * @param pageSize 每页显示条数
     * @param currentPage 当前页
     */
    public static void queryPageByApplyIdTest(Long applyId, Integer pageSize, Integer currentPage) {
        QueryPageByApplyIdRequest request = new QueryPageByApplyIdRequest();
        request.setApplyId(applyId);
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        QueryPageByApplyIdResponse response = (QueryPageByApplyIdResponse)executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("分页查询批量申请的设备成功！ " + JSONObject.toJSONString(response));
        } else {
            LogUtil.error("分页查询批量申请的设备失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());

        }

    }


    /**
     * 发送广播消息
     *
     * @param productKey 产品pk
     * @param topic 广播topic /broadcast/${pk}/+
     * @param msg 消息内容
     */
    public static void pubBroadcastTest(String productKey, String topic, String msg) {
        PubBroadcastRequest request = new PubBroadcastRequest();
        request.setProductKey(productKey);
        request.setTopicFullName(topic);
        request.setMessageContent(Base64.encodeBase64String(msg.getBytes()));
        PubBroadcastResponse response = (PubBroadcastResponse)executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("发送消息成功！");
        } else {
            LogUtil.error("发送消息失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());

        }

    }

    /**
     * 发送rrpc消息
     *
     * @param productKey 产品pk
     * @param deviceName 设备名称
     * @param msg 消息内容
     */
    public static void rrpcTest(String productKey, String deviceName, String msg) {
        RRpcRequest request = new RRpcRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        request.setRequestBase64Byte(Base64.encodeBase64String(msg.getBytes()));
        request.setTimeout(5000);
        RRpcResponse response = (RRpcResponse)executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("rrpc消息发送成功！messageId:" + response.getMessageId() + ",payloadBase64Byte：" + new String(
                    Base64.decodeBase64(response.getPayloadBase64Byte())));
        } else {
            LogUtil.error("rrpc消息发送失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());

        }

    }

    /**
     * 获取设备影子信息
     *
     * @param productKey 产品pk
     * @param deviceName 设备名称
     * @return 影子信息
     */
    public static String getDeviceShadowTest(String productKey, String deviceName) {
        GetDeviceShadowRequest request = new GetDeviceShadowRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        GetDeviceShadowResponse response = (GetDeviceShadowResponse)executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("获取设备影子成功！shadowMessage:" + response.getShadowMessage());
            //如果影子内容不为空，获取版本信息
            if (response.getShadowMessage() != null	) {
                JSONObject shadowObject = JSONObject.parseObject(response.getShadowMessage());
                shadowVersion = shadowObject.getLong("version");
            }
            return response.getShadowMessage();
        } else {
            LogUtil.error("获取设备影子失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());
        }
        return null;
    }

    /**
     * 更新影子信息
     *
     * @param productKey 产品pk
     * @param deviceName 设备名称
     */
    public static void updateDeviceShadowTest(String productKey, String deviceName) {

        UpdateDeviceShadowRequest request = new UpdateDeviceShadowRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);

        Map<String, Object> attributeMap = new LinkedHashMap<String, Object>(16);
        attributeMap.put("window", "open");
        attributeMap.put("temperature", 25);

        String shadow = genUpdateShadowMsg(attributeMap);
        request.setShadowMessage(shadow);
        UpdateDeviceShadowResponse response = (UpdateDeviceShadowResponse)executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("更新设备影子成功！");
        } else {
            LogUtil.error("更新设备影子失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());

        }
    }


    public static void saveDevicePropTest(String productKey, String deviceName){
        SaveDevicePropRequest request = new SaveDevicePropRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("temperature", "38");
        props.put("features", "{\"color\":\"red\"}");
        request.setProps(JSON.toJSONString(props));
        SaveDevicePropResponse response = (SaveDevicePropResponse) executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("保存设备属性成功！");
        } else {
            LogUtil.error("保存设备属性失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());

        }
    }

    public static void deleteDevicePropTest(String productKey, String deviceName){
        DeleteDevicePropRequest request = new DeleteDevicePropRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        request.setPropKey("temperature");
        DeleteDevicePropResponse response = (DeleteDevicePropResponse) executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("删除设备属性成功！");
        } else {
            LogUtil.error("删除设备属性失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());

        }

    }
    private static void queryDevicePropTest(String productKey, String deviceName){
        QueryDevicePropRequest request = new QueryDevicePropRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        QueryDevicePropResponse response = (QueryDevicePropResponse) executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("查询设备属性成功！属性信息：" + response.getProps());

        } else {
            LogUtil.error("查询设备属性失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());

        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static String genUpdateShadowMsg(Map<String, Object> attributeMap) {

        Set<String> attSet = attributeMap.keySet();
        Map attMap = new LinkedHashMap();
        for (String attKey : attSet) {
            attMap.put(attKey, attributeMap.get(attKey));
        }
        Map reportedMap = new LinkedHashMap();
        reportedMap.put("desired", attMap);
        Map shadowJsonMap = new LinkedHashMap();
        shadowJsonMap.put("method", "update");
        shadowJsonMap.put("state", reportedMap);
        shadowVersion++;
        shadowJsonMap.put("version", shadowVersion);
        return JSON.toJSONString(shadowJsonMap);
    }

    /**
     * pub消息
     *
     * @param productKey pk
     * @param topic topic
     * @param msg 消息内容
     */
    public static String pubTest(String productKey, String topic, String msg) {
        PubRequest request = new PubRequest();
        request.setProductKey(productKey);
        request.setTopicFullName(topic);
        request.setMessageContent(Base64.encodeBase64String(msg.getBytes()));
        request.setQos(0);
        PubResponse response = (PubResponse)executeTest(request);
       String resulJson= new Gson().toJson(response);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("发送消息成功！messageId：" +resulJson );
        } else {
            LogUtil.error("发送消息失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());
        }
        return resulJson;
    }

    public static void main(String[] args){
        String productKey = "Ft9glS0MPmE";
        List<String> phoneList=new ArrayList<>();
        phoneList.add("phone002");
        queryDeviceByNameTest(productKey,"phone002");

    }
}
