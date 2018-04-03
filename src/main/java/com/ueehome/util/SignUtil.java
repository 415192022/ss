package com.ueehome.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by uee on 2018/1/5.
 */
public class SignUtil {
    public static final String TOKEN="UeeWechat";
    /**
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     * @Author:lulei
     * @Description: 微信权限验证
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] { TOKEN, timestamp, nonce };
        //按字典排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        //加密并返回验证结果
        return signature == null ? false : signature.equals(encrypt(content.toString(), "SHA-1"));
    }

    /**
     * @param str 需要加密的字符串
     * @param encName 加密种类  MD5 SHA-1 SHA-256
     * @return
     * @Author:lulei
     * @Description: 实现对字符串的加密
     */
    public static String encrypt(String str, String encName){
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance(encName);
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes){
                int bt = b&0xff;
                if (bt < 16){
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }
    public static String check(String signature,String timestamp,String nonce,String echostr ) {
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return "";
    }
}
