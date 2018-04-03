/*
 * Copyright (c) 2014-2016 Alibaba Group. All rights reserved.
 * License-Identifier: Apache-2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ueehome.aliiot;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.RpcAcsRequest;
import com.google.gson.Gson;
import com.ueehome.aliiot.bean.AliIotDeviceStateBean;
import com.ueehome.aliiot.impl.AliIotDataSourceImpl;
import com.ueehome.util.LogUtil;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseAliIot {

	private static DefaultAcsClient client;

	static {
		client = IotClient.getClient();
		client.setAutoRetry(true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static AcsResponse executeTest(RpcAcsRequest request) {
		AcsResponse response = null;
		try {
			response = client.getAcsResponse(request);

		} catch (Exception e) {
			LogUtil.print("执行失败：e:" + e.getMessage());
		}
		return response;
	}


	private static Subscription subscribe=null;
    private static CloudAccount account = null;
    private static MNSClient msnClient = null;
    private static CloudQueue queue = null;
	public static void loop(String accessId, String accessKey, String accountEndpoint) {
        subscribe = Observable.just(null)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(o -> {
                     account = new CloudAccount(accessId,accessKey,accountEndpoint);
                    msnClient = account.getMNSClient();
                     queue = msnClient.getQueueRef("aliyun-iot-Ft9glS0MPmE"); //参数请输入IoT自动创建的队列名称，例如上面截图中的aliyun-iot-3AbL0062osF
//                    while (true) {
                        try {
                            // 获取消息
                            Message popMsg = queue.popMessage(30); //长轮询等待时间为10秒
                            if (popMsg != null) {
                                String json=popMsg.getMessageBodyAsString();
                                System.out.println("PopMessage Body: "
                                        + json); //获取原始消息
                                AliIotDeviceStateBean aliIotDeviceStateBean=new Gson().fromJson(json,AliIotDeviceStateBean.class);
//                                aliIotDeviceStateBeansQueue.add(aliIotDeviceStateBean);
                                parseQueue(aliIotDeviceStateBean);
                                queue.deleteMessage(popMsg.getReceiptHandle()); //从队列中删除消息
                            } else {
//						System.out.println("Continuing");
                            }
                        }catch (Exception e){
                            aliLoopDestroy(account,msnClient,queue);
                            loop(accessId,accessKey,accountEndpoint);
                        }
                        finally {
                            aliLoopDestroy(account,msnClient,queue);
                            loop(accessId,accessKey,accountEndpoint);
                        }
//                    }
                }, throwable -> {
                    aliLoopDestroy(account,msnClient,queue);
                    loop(accessId,accessKey,accountEndpoint);
                });
	}

    private static void aliLoopDestroy(CloudAccount account , MNSClient client,CloudQueue queue){
	    if(null != subscribe){
            subscribe.unsubscribe();
            subscribe=null;
        }
        account=null;
        queue=null;
        if(null != client){
            client.close();
            client=null;
        }
	}


	private static void parseQueue(AliIotDeviceStateBean aliIotDeviceStateBean){
        System.out.println("json -> "+aliIotDeviceStateBean.getPayload());
        String baseString=new String(Base64.getDecoder().decode(aliIotDeviceStateBean.getPayload()));
        AliIotDeviceStateBean.PayloadBean payloadBean=new Gson().fromJson(baseString,AliIotDeviceStateBean.PayloadBean.class);
        System.out.println("payloadBean -> "+payloadBean);

        if (payloadBean.getDeviceName().contains("phone")){
            //如果是手机  根据 getDeviceName 查询出该手机下绑定的所有设备 DeviceName 向每台手机发送 设备状态(在线/离线)状态消息
        }else {
            //如果是设备   查询出该设备的绑定的所有手机设备  DeviceName(以phone开头的设备名称)  再向该名称的所有手机 发送设备状态(在线/离线)消息
        }
//        String devicename=payloadBean.getDeviceName();

        try {
            AliIotDataSourceImpl aliIotDataSource=new AliIotDataSourceImpl();
            List<String> phoneNames=aliIotDataSource.queryMasterPhoneOfDevice(payloadBean.getDeviceName());
            for (String phoneName:phoneNames){
                String productKey = "Ft9glS0MPmE";
                String msg = new Gson().toJson(payloadBean);
                String topic = "/" + productKey + "/" + phoneName + "/regeister";
                AliIotImpl.pubTest(productKey, topic, msg);
                System.out.println("======  "+phoneName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String productKey = "Ft9glS0MPmE";
        String msg = new Gson().toJson(payloadBean);
        String devicename=payloadBean.getDeviceName();
        String topic = "/" + productKey + "/" + devicename + "/regeister";
        AliIotImpl.pubTest(productKey, topic, msg);
    }


    public static void queueHandle(List<AliIotDeviceStateBean> aliIotDeviceStateBeansQueue) throws IOException {
	    Observable.interval(3000, TimeUnit.MILLISECONDS)
                .flatMap(new Func1<Long, Observable<AliIotDeviceStateBean>>() {
                    @Override
                    public Observable<AliIotDeviceStateBean> call(Long aLong) {
                        System.out.println("============="+aliIotDeviceStateBeansQueue.size());
                        return Observable.from(aliIotDeviceStateBeansQueue);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(aliIotDeviceStateBean -> {
                    if(null== aliIotDeviceStateBean){
                        return;
                    }
                    parseQueue(aliIotDeviceStateBean);
                    aliIotDeviceStateBeansQueue.remove(aliIotDeviceStateBean);
                }, throwable -> {

                });
    }
    public static void queueHandle2(List<AliIotDeviceStateBean> aliIotDeviceStateBeansQueue) throws IOException {
	    Observable.interval(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(aLong -> {
                    System.out.println("$$$$$$$$$$$$$$$$ "+aliIotDeviceStateBeansQueue.size());
                    if(null != aliIotDeviceStateBeansQueue && aliIotDeviceStateBeansQueue.size()<=0){
                        return;
                    }
                    parseQueue(aliIotDeviceStateBeansQueue.get(aliIotDeviceStateBeansQueue.size()-1));
                    aliIotDeviceStateBeansQueue.clear();
                }, throwable -> {

                });
    }
}
