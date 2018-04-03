package com.ueehome.util;

/**
 * Created by uee on 2017/11/6.
 */
public class GetWXToken {
    private final String GRANT_TYPE="client_credential";
    private final String APPID="wx67a99b2ffe675561";
    private final String SECRET="a6b7e10cd023a004363136cfcc14297d";
    private HttpUtils httpUtils=new HttpUtils();
    private final String URL_TOKEN="https://api.weixin.qq.com/cgi-bin/token?grant_type="+GRANT_TYPE+"&appid="+APPID+"&secret="+SECRET;
    public String getToken(){
       return httpUtils.startGet(URL_TOKEN);
    }
}
