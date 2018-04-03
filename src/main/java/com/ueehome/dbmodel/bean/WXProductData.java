package com.ueehome.dbmodel.bean;

/**
 * Created by Mingwei on 2017/7/28.
 * 产品数据
 */
public class WXProductData {
    public String ueeID;
    public String pid;
    public String pidSub;
    public Long prodDate;
    public String deviceid;
    public String qrticket;
    public String device_type;

    /**
     * 产品数据
     *
     * @param ueeID        ID
     * @param pid          产品ID
     * @param pidSub       产品子ID
     * @param prodDate     生产日期
     */
    public WXProductData(String ueeID, String pid, String pidSub, Long prodDate, String deviceid, String qrticket, String device_type) {
        this.ueeID = ueeID;
        this.pid = pid;
        this.pidSub = pidSub;
        this.prodDate = prodDate;
        this.deviceid = deviceid;
        this.qrticket = qrticket;
        this.device_type = device_type;
    }
}
