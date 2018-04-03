package com.ueehome.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by uee on 2017/11/3.
 */
public class HttpUtils {
    public void startGet(String path,OnGetWXLicenceCallBack onGetWXLicenceCallBack){
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            //GET请求直接在链接后面拼上请求参数
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            //Get请求不需要DoOutPut
            conn.setDoOutput(false);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //连接服务器
            conn.connect();
            // 取得输入流，并使用Reader读取
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            int responseCode = conn.getResponseCode();
            if(HttpURLConnection.HTTP_OK == responseCode){
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                    System.out.println(line+"==============");
                    onGetWXLicenceCallBack.onGetWXLicence(line,responseCode+"");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭输入流
        finally{
            try{
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    public String startGet(String path){
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            //GET请求直接在链接后面拼上请求参数
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            //Get请求不需要DoOutPut
            conn.setDoOutput(false);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //连接服务器
            conn.connect();
            // 取得输入流，并使用Reader读取
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            int responseCode = conn.getResponseCode();
            if(HttpURLConnection.HTTP_OK == responseCode){
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                    System.out.println(line+"==============");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭输入流
        finally{
            try{
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    public interface OnGetWXLicenceCallBack{
        void onGetWXLicence(String result, String code);
    }


    public static void main(String[] s){
        TestThread testThread1=new TestThread();
        TestThread testThread2=new TestThread();
        TestThread testThread3=new TestThread();
        TestThread testThread4=new TestThread();
        TestThread testThread5=new TestThread();
        TestThread testThread6=new TestThread();
        TestThread testThread7=new TestThread();
        TestThread testThread8=new TestThread();
        TestThread testThread9=new TestThread();
        TestThread testThread0=new TestThread();

        TestThread testThread11=new TestThread();
        TestThread testThread22=new TestThread();
        TestThread testThread33=new TestThread();
        TestThread testThread44=new TestThread();
        TestThread testThread55=new TestThread();
        TestThread testThread66=new TestThread();
        TestThread testThread77=new TestThread();
        TestThread testThread88=new TestThread();
        TestThread testThread99=new TestThread();
        TestThread testThread00=new TestThread();

        TestThread testThread111=new TestThread();
        TestThread testThread222=new TestThread();
        TestThread testThread333=new TestThread();
        TestThread testThread444=new TestThread();
        TestThread testThread555=new TestThread();
        TestThread testThread666=new TestThread();
        TestThread testThread777=new TestThread();
        TestThread testThread888=new TestThread();
        TestThread testThread999=new TestThread();
        TestThread testThread000=new TestThread();


        TestThread testThread1111=new TestThread();
        TestThread testThread2222=new TestThread();
        TestThread testThread3333=new TestThread();
        TestThread testThread4444=new TestThread();
        TestThread testThread5555=new TestThread();
        TestThread testThread6666=new TestThread();
        TestThread testThread7777=new TestThread();
        TestThread testThread8888=new TestThread();
        TestThread testThread9999=new TestThread();
        TestThread testThread0000=new TestThread();

        TestThread testThread11111=new TestThread();
        TestThread testThread22222=new TestThread();
        TestThread testThread33333=new TestThread();
        TestThread testThread44444=new TestThread();
        TestThread testThread55555=new TestThread();
        TestThread testThread66666=new TestThread();
        TestThread testThread77777=new TestThread();
        TestThread testThread88888=new TestThread();
        TestThread testThread99999=new TestThread();
        TestThread testThread00000=new TestThread();

        TestThread testThread111111=new TestThread();
        TestThread testThread222222=new TestThread();
        TestThread testThread333333=new TestThread();
        TestThread testThread444444=new TestThread();
        TestThread testThread555555=new TestThread();
        TestThread testThread666666=new TestThread();
        TestThread testThread777777=new TestThread();
        TestThread testThread888888=new TestThread();
        TestThread testThread999999=new TestThread();
        TestThread testThread000000=new TestThread();

        TestThread testThread1111111=new TestThread();
        TestThread testThread2222222=new TestThread();
        TestThread testThread3333333=new TestThread();
        TestThread testThread4444444=new TestThread();
        TestThread testThread5555555=new TestThread();
        TestThread testThread6666666=new TestThread();
        TestThread testThread7777777=new TestThread();
        TestThread testThread8888888=new TestThread();
        TestThread testThread9999999=new TestThread();
        TestThread testThread0000000=new TestThread();

        TestThread testThread11111111=new TestThread();
        TestThread testThread22222222=new TestThread();
        TestThread testThread33333333=new TestThread();
        TestThread testThread44444444=new TestThread();
        TestThread testThread55555555=new TestThread();
        TestThread testThread66666666=new TestThread();
        TestThread testThread77777777=new TestThread();
        TestThread testThread88888888=new TestThread();
        TestThread testThread99999999=new TestThread();
        TestThread testThread00000000=new TestThread();

        TestThread testThread111111111=new TestThread();
        TestThread testThread222222222=new TestThread();
        TestThread testThread333333333=new TestThread();
        TestThread testThread444444444=new TestThread();
        TestThread testThread555555555=new TestThread();
        TestThread testThread666666666=new TestThread();
        TestThread testThread777777777=new TestThread();
        TestThread testThread888888888=new TestThread();
        TestThread testThread999999999=new TestThread();
        TestThread testThread000000000=new TestThread();

        TestThread testThread1111111111=new TestThread();
        TestThread testThread2222222222=new TestThread();
        TestThread testThread3333333333=new TestThread();
        TestThread testThread4444444444=new TestThread();
        TestThread testThread5555555555=new TestThread();
        TestThread testThread6666666666=new TestThread();
        TestThread testThread7777777777=new TestThread();
        TestThread testThread8888888888=new TestThread();
        TestThread testThread9999999999=new TestThread();
        TestThread testThread0000000000=new TestThread();



        testThread1.start();
        testThread2.start();
        testThread3.start();
        testThread4.start();
        testThread5.start();
        testThread6.start();
        testThread7.start();
        testThread8.start();
        testThread9.start();
        testThread0.start();

        testThread11.start();
        testThread22.start();
        testThread33.start();
        testThread44.start();
        testThread55.start();
        testThread66.start();
        testThread77.start();
        testThread88.start();
        testThread99.start();
        testThread00.start();


        testThread111.start();
        testThread222.start();
        testThread333.start();
        testThread444.start();
        testThread555.start();
        testThread666.start();
        testThread777.start();
        testThread888.start();
        testThread999.start();
        testThread000.start();

        testThread1111.start();
        testThread2222.start();
        testThread3333.start();
        testThread4444.start();
        testThread5555.start();
        testThread6666.start();
        testThread7777.start();
        testThread8888.start();
        testThread9999.start();
        testThread0000.start();


        testThread11111.start();
        testThread22222.start();
        testThread33333.start();
        testThread44444.start();
        testThread55555.start();
        testThread66666.start();
        testThread77777.start();
        testThread88888.start();
        testThread99999.start();
        testThread00000.start();


        testThread111111.start();
        testThread222222.start();
        testThread333333.start();
        testThread444444.start();
        testThread555555.start();
        testThread666666.start();
        testThread777777.start();
        testThread888888.start();
        testThread999999.start();
        testThread000000.start();


        testThread1111111.start();
        testThread2222222.start();
        testThread3333333.start();
        testThread4444444.start();
        testThread5555555.start();
        testThread6666666.start();
        testThread7777777.start();
        testThread8888888.start();
        testThread9999999.start();
        testThread0000000.start();


        testThread11111111.start();
        testThread22222222.start();
        testThread33333333.start();
        testThread44444444.start();
        testThread55555555.start();
        testThread66666666.start();
        testThread77777777.start();
        testThread88888888.start();
        testThread99999999.start();
        testThread00000000.start();


        testThread111111111.start();
        testThread222222222.start();
        testThread333333333.start();
        testThread444444444.start();
        testThread555555555.start();
        testThread666666666.start();
        testThread777777777.start();
        testThread888888888.start();
        testThread999999999.start();
        testThread000000000.start();


        testThread1111111111.start();
        testThread2222222222.start();
        testThread3333333333.start();
        testThread4444444444.start();
        testThread5555555555.start();
        testThread6666666666.start();
        testThread7777777777.start();
        testThread8888888888.start();
        testThread9999999999.start();
        testThread0000000000.start();




    }

    private static final String getlicence="http://192.168.100.100:6789/getWXLicence/666666";
    private static final String gettoken="http://192.168.100.63:6789/getWXToken";
    public static class TestThread extends Thread{
        HttpUtils httpUtils=new HttpUtils();
        @Override
        public void run() {
            super.run();
            httpUtils.startGet(gettoken, new OnGetWXLicenceCallBack() {
                @Override
                public void onGetWXLicence(String result, String code) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    httpUtils.startGet(gettoken,this);
                }
            });
        }
    }

}
