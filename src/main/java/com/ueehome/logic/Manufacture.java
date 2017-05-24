package com.ueehome.logic;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;
import com.aliyuncs.utils.ParameterHelper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ueehome.UEEException;
import com.ueehome.apibean.QQIOTImportResponse;
import com.ueehome.dbmodel.bean.ManuAuthData;
import com.ueehome.dbmodel.bean.ProductData;
import com.ueehome.dbmodel.modelimpl.ManufactureDataSourceImpl;
import com.ueehome.logic.bean.QQIOTBean;
import com.ueehome.logic.bean.RegisterQQIOTLicenceData;

import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * Created by TangWei on 2017/3/27.
 * 生产
 */
public class Manufacture {

    private ManufactureDataSourceImpl manufactureDataSource;

    public Manufacture() throws SQLException {
        manufactureDataSource = new ManufactureDataSourceImpl();
    }

    public RegisterQQIOTLicenceData registerQQIOTLicence(String password) throws SQLException, UEEException {
        if (validPassword(password)) {
            ProductData productData = manufactureDataSource.registQQIOT(password);
            ManuAuthData manuAuthData = manufactureDataSource.getManuAuthData(password);

            if (productData.ueeID == null && productData.pid == null && productData.pidSub == null && productData.prodDate == null && productData.qqiotGUID == null && productData.qqiotLicence == null)
                throw new UEEException(2001007);//QQ物联授权已用完
            return new RegisterQQIOTLicenceData(productData.ueeID, productData.qqiotGUID, productData.qqiotLicence, manuAuthData.count - manuAuthData.usedCount);
        } else
            throw new UEEException(2001003); //密码错误
    }

    private boolean validPassword(String password) throws SQLException, UEEException {

        ManuAuthData manuAuthData = manufactureDataSource.getManuAuthData(password);

        if (manuAuthData.pid == null && manuAuthData.pidSub == null &&
                manuAuthData.prodDate == 0L && manuAuthData.prodDeadline == 0L
                && manuAuthData.count == null && manuAuthData.usedCount == null
                && manuAuthData.enable.equals(false)) {
            throw new UEEException(2001003);//password错误或不存在
        } else {
            if (!manuAuthData.enable) throw new UEEException(2001006); //password授权已关闭
            if (manuAuthData.prodDeadline - manuAuthData.prodDate < 0)
                throw new UEEException(2001005); //password授权已过期
            if (manuAuthData.count.equals(manuAuthData.usedCount))
                throw new UEEException(2001004);//password授权数已用完
        }

        return true;
    }

    public void importQQIOTLicence(String password, String data) throws SQLException, UEEException {
        if (!Objects.equals(password, "&m9OBKlE2N5Z")) throw new UEEException(2002006);
        manufactureDataSource = new ManufactureDataSourceImpl();
        try {
            QQIOTBean[] datalist = new Gson().fromJson(data, QQIOTBean[].class);
            if (datalist.length > 1000) {
                throw new UEEException(2002005); //一次批量插入不能超过1000条
            }
            for (QQIOTBean item : datalist) {
                if (item.getQq_guid() == null || item.getQq_guid().isEmpty())
                    throw new UEEException(2002001); //缺少GUID
                if (item.getQq_licence() == null || item.getQq_licence().isEmpty())
                    throw new UEEException(2002002); //缺少Licence
            }
            manufactureDataSource.importQQIOTLicence(datalist);
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 1062:
                    throw new UEEException(2002004); //GUID或Licence不唯一
                default:
                    throw halt(500);
            }
        } catch (JsonSyntaxException e) {
            throw new UEEException(2002003); //参数格式校验不通过
        }

    }

    public String requestAliPush() {
//        Ryans233
//        long appKey = 23828067;
//        String accessKeyId = "LTAIKD2wm6ptB7K5";
//        String accessKeySecret = "4iOAxj25iP4DCIbB0Kw4Pb7i6odvTo";

//        Mingwei
        long appKey = 23828218;
        String accessKeyId = "LTAIMAwxxnUW8BrK";
        String accessKeySecret = "lfyUeoWw8P7rt4C3O2GoO6OY4v96Sf";

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        PushRequest pushRequest = new PushRequest();
        // 推送目标
        pushRequest.setAppKey(appKey);
//        pushRequest.setTarget("DEVICE"); //推送目标: DEVICE:按设备推送 ALIAS : 按别名推送 ACCOUNT:按帐号推送  TAG:按标签推送; ALL: 广播推送
//        pushRequest.setTargetValue(deviceIds); //根据Target来设定，如Target=DEVICE, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
        pushRequest.setTarget("ALL"); //推送目标: device:推送给设备; account:推送给指定帐号,tag:推送给自定义标签; all: 推送给全部
        pushRequest.setTargetValue("ALL"); //根据Target来设定，如Target=device, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
        pushRequest.setPushType("NOTICE"); // 消息类型 MESSAGE NOTICE
        pushRequest.setDeviceType("ANDROID"); // 设备类型 ANDROID iOS ALL.
        // 推送配置
        pushRequest.setTitle("ALi Push Title"); // 消息的标题
        pushRequest.setBody("Ali Push Body"); // 消息的内容
        // 推送配置: iOS
        pushRequest.setiOSBadge(5); // iOS应用图标右上角角标
        pushRequest.setiOSMusic("default"); // iOS通知声音
        pushRequest.setiOSSubtitle("iOS10 subtitle");//iOS10通知副标题的内容
        pushRequest.setiOSNotificationCategory("iOS10 Notification Category");//指定iOS10通知Category
        pushRequest.setiOSMutableContent(true);//是否允许扩展iOS通知内容
        pushRequest.setiOSApnsEnv("DEV");//iOS的通知是通过APNs中心来发送的，需要填写对应的环境信息。"DEV" : 表示开发环境 "PRODUCT" : 表示生产环境
        pushRequest.setiOSRemind(true); // 消息推送时设备不在线（既与移动推送的服务端的长连接通道不通），则这条推送会做为通知，通过苹果的APNs通道送达一次。注意：离线消息转通知仅适用于生产环境
        pushRequest.setiOSRemindBody("iOSRemindBody");//iOS消息转通知时使用的iOS通知内容，仅当iOSApnsEnv=PRODUCT && iOSRemind为true时有效
        pushRequest.setiOSExtParameters("{\"_ENV_\":\"DEV\",\"k2\":\"v2\"}"); //通知的扩展属性(注意 : 该参数要以json map的格式传入,否则会解析出错)
        // 推送配置: Android
        pushRequest.setAndroidNotifyType("NONE");//通知的提醒方式 "VIBRATE" : 震动 "SOUND" : 声音 "BOTH" : 声音和震动 NONE : 静音
        pushRequest.setAndroidNotificationBarType(1);//通知栏自定义样式0-100
        pushRequest.setAndroidNotificationBarPriority(1);//通知栏自定义样式0-100
        pushRequest.setAndroidOpenType("URL"); //点击通知后动作 "APPLICATION" : 打开应用 "ACTIVITY" : 打开AndroidActivity "URL" : 打开URL "NONE" : 无跳转
        pushRequest.setAndroidOpenUrl("http://www.aliyun.com"); //Android收到推送后打开对应的url,仅当AndroidOpenType="URL"有效
        pushRequest.setAndroidActivity("com.alibaba.push2.demo.XiaoMiPushActivity"); // 设定通知打开的activity，仅当AndroidOpenType="Activity"有效
        pushRequest.setAndroidMusic("default"); // Android通知音乐
        pushRequest.setAndroidXiaoMiActivity("com.ali.demo.MiActivity");//设置该参数后启动小米托管弹窗功能, 此处指定通知点击后跳转的Activity（托管弹窗的前提条件：1. 集成小米辅助通道；2. StoreOffline参数设为true）
        pushRequest.setAndroidXiaoMiNotifyTitle("Mi title");
        pushRequest.setAndroidXiaoMiNotifyBody("MiActivity Body");
        pushRequest.setAndroidExtParameters("{\"k1\":\"android\",\"k2\":\"v2\"}"); //设定通知的扩展属性。(注意 : 该参数要以 json map 的格式传入,否则会解析出错)
        // 推送控制
        Date pushDate = new Date(System.currentTimeMillis()); // 30秒之间的时间点, 也可以设置成你指定固定时间
        String pushTime = ParameterHelper.getISO8601Time(pushDate);
        pushRequest.setPushTime(pushTime); // 延后推送。可选，如果不设置表示立即推送
        String expireTime = ParameterHelper.getISO8601Time(new Date(System.currentTimeMillis() + 12 * 3600 * 1000)); // 12小时后消息失效, 不会再发送
        pushRequest.setExpireTime(expireTime);
        pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到
        PushResponse pushResponse = null;
        try {
            pushResponse = client.getAcsResponse(pushRequest);
        } catch (ClientException e) {
            e.printStackTrace();
            return e.toString();
        }
        return "RequestId: " + pushResponse.getRequestId() + ", MessageID: " + pushResponse.getMessageId();
    }
}
