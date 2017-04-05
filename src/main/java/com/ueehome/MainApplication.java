package com.ueehome;

/**
 * Created by TangWei on 2017/3/27.
 * 主入口
 */
public class MainApplication {
    private static final String dbUrl = "jdbc:mysql://192.168.100.100/uee?" +
            "serverTimezone=UTC&" +
            "characterEncoding=utf-8&" +
            "user=uee&" +
            "password=a12345.";

    public static void main(String[] args) throws Exception {
        ErrorHandler.init();
        DBManager.init(dbUrl);
        API.init();
    }
}
