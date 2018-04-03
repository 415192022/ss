package com.ueehome.apibean;

/**
 * Created by MingweiLi on 2017/8/11.
 */

public class UploadLogBean {
    private String fileName;

    public String getFileName() {
        return fileName;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "UploadLogBean{" +
                "fileName='" + fileName + '\'' +
                '}';
    }
}
