package com.ueehome.apibean;

import java.util.List;

/**
 * Created by uee on 2018/1/5.
 */
public class WXOpenidListBean {
    private List<WXDeviceBean> wxDeviceBeans;

    public List<WXDeviceBean> getWxDeviceBeans() {
        return wxDeviceBeans;
    }

    public void setWxDeviceBeans(List<WXDeviceBean> wxDeviceBeans) {
        this.wxDeviceBeans = wxDeviceBeans;
    }

    @Override
    public String toString() {
        return "WXOpenidListBean{" +
                "wxDeviceBeans=" + wxDeviceBeans +
                '}';
    }
}
