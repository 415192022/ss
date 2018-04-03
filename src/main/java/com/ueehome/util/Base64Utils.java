package com.ueehome.util;



import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by MingweiLi on 2017/8/11.
 */

public class Base64Utils {

    /**
     * 将base64字符解码保存文件
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void decoderBase64File(String base64Code, String targetPath)
            throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();

    }

    public static void savaFile(InputStream inputStream, String dec){
        int len;
        byte[] bytes=new byte[4 * 1024];
    }
}
