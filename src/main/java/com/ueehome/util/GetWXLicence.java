package com.ueehome.util;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uee on 2017/11/6.
 */
public class GetWXLicence {
    public static final String LOCAL_LICENCE="E:/WxLicence.json";
    private static HttpUtils httpUtils=new HttpUtils();
    public static void main(String[] args) {
        getWXLicenceSavaLocal();
    }
    private static String token="8_yKFDYNzigWeBtxzv3wny8f-nBjjI8JhTZ6ZF7C00joX0iJFneMHcKXgDBWD9yfjfW2cBgYt-eKVYOWJ077lIqwdUlCT8bUwbxDql6ANuD_nideiPDo0tMgzjmnG1ZCL7K1UqbJ4xrTgueRK6KOLhABAYCZ";
    public static void getWXLicenceSavaLocal(){
        httpUtils.startGet("https://api.weixin.qq.com/device/getqrcode?access_token="+token+"&product_id=42355"
                , new HttpUtils.OnGetWXLicenceCallBack() {
                    @Override
                    public void onGetWXLicence(String result, String code) {
                        if("200".equals(code)){
                            System.out.println("请求成功"+result);

                            WechatMessageBean wechatMessageBean=new Gson().fromJson(result,WechatMessageBean.class);
                            System.out.println(wechatMessageBean);

                            WechatLicenceBean wechatLicenceBean=new WechatLicenceBean();
                            List<WechatLicenceBean.WechatLicenceBeanItem> wechatLicenceBeanItems=new ArrayList<>();
                            WechatLicenceBean.WechatLicenceBeanItem wechatLicenceBeanItem=new WechatLicenceBean.WechatLicenceBeanItem();
                            wechatLicenceBeanItem.setDeviceid(wechatMessageBean.getDeviceid());
                            wechatLicenceBeanItem.setQrticket(wechatMessageBean.getQrticket());
                            wechatLicenceBeanItem.setDevice_type("gh_83d00dec8786");
                            wechatLicenceBeanItems.add(wechatLicenceBeanItem);
                            wechatLicenceBean.setWechatLicenceBeanItems(wechatLicenceBeanItems);


                            String readResult=FileUtils.readFile(LOCAL_LICENCE);
                            if("".equals(readResult) || null == readResult){
                                String saveJson=new Gson().toJson(wechatLicenceBean,WechatLicenceBean.class);
                                System.out.println("LLL   "+saveJson);
                                FileUtils.writeFile(LOCAL_LICENCE,saveJson,false);
                            }else{
                                WechatLicenceBean wechatLicenceBean1=new Gson().fromJson(readResult,WechatLicenceBean.class);
                                List<WechatLicenceBean.WechatLicenceBeanItem> wechatLicenceBeans=wechatLicenceBean1.getWechatLicenceBeanItems();
                                wechatLicenceBeans.add(wechatLicenceBeanItem);
                                wechatLicenceBean1.setWechatLicenceBeanItems(wechatLicenceBeans);
                                String newLocalJson=new Gson().toJson(wechatLicenceBean1);
                                FileUtils.writeFile(LOCAL_LICENCE,newLocalJson,false);
                            }

                            try {
                                Thread.sleep(1000);
                                getWXLicenceSavaLocal();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else{
                            System.out.println("请求失败"+code);
                        }
                    }
                });
    }
}
