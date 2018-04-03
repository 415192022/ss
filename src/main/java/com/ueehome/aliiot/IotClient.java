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

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ueehome.util.LogUtil;

import java.util.Properties;

public class IotClient {
	private static String accessKeyID;
	private static String accessKeySecret;
	private static String regionId;
	private static String productKey;
	private static String product;
	private static String domain;

	private static DefaultAcsClient client = null;
	public static String getAccessKeyID() {
		return accessKeyID;
	}

	public static String getAccessKeySecret() {
		return accessKeySecret;
	}

	public static DefaultAcsClient getClient() {
		if(null == client){
			synchronized (DefaultAcsClient.class){
				if(null == client){
					Properties prop = new Properties();
					try {
						prop.load(Object.class.getResourceAsStream("/config.properties"));
						accessKeyID = prop.getProperty("user.accessKeyID");
						accessKeySecret = prop.getProperty("user.accessKeySecret");
						regionId = prop.getProperty("iot.regionId");
						productKey=prop.getProperty("iot.productKey");
						product=prop.getProperty("iot.product");
						domain=prop.getProperty("iot.domain");


						DefaultProfile.addEndpoint(regionId, regionId, product, domain);
						IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyID, accessKeySecret);
						// 初始化client
						client = new DefaultAcsClient(profile);
					} catch (Exception e) {
						LogUtil.print("初始化client失败！exception:" + e.getMessage());
					}
				}
			}
		}
		return client;
	}

	public static String getRegionId() {
		return regionId;
	}

	public static String getProductKey() {
		return productKey;
	}

	public static String getProduct() {
		return product;
	}

	public static String getDomain() {
		return domain;
	}
}
