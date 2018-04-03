package com.ueehome.util;

import java.io.*;

/**
 * Created by uee on 2017/11/3.
 */
public class FileUtils {
    /**
     * 读文件
     * @return 读取文件内容结果
     */
    public static String readFile(String f){
        try {
            File file=new File(f);
            if(!file.exists()){
                return "";
            }
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");// windows系统换行为\r\n，Linux为\n
            }
            return sb.delete(sb.length() - 2, sb.length()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 写文件
     * @param file  文件路径
     * @param content 写入内容
     * @param isAppend 是否追加
     */
    public static void writeFile(String file ,String content,boolean isAppend){
        BufferedWriter bw = null;
        FileWriter fileWriter=null;
        try {
            fileWriter=new FileWriter(file,isAppend);
            bw = new BufferedWriter(fileWriter);
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(bw);
            closeIO(fileWriter);
        }
    }
    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
