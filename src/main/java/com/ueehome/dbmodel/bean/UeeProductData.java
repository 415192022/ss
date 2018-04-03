package com.ueehome.dbmodel.bean;

/**
 * Created by Mingwei on 2017/7/28.
 * 产品数据
 */
public class UeeProductData {
    public String ueeID;
    public String pid;
    public String pidSub;
    public Long prodDate;
    public String ueeiotLicence;

    /**
     * 产品数据
     *
     * @param ueeID        ID
     * @param pid          产品ID
     * @param pidSub       产品子ID
     * @param prodDate     生产日期
     * @param ueeiotLicence UEELicence
     */
    public UeeProductData(String ueeID, String pid, String pidSub, Long prodDate, String ueeiotLicence) {
        this.ueeID = ueeID;
        this.pid = pid;
        this.pidSub = pidSub;
        this.prodDate = prodDate;
        this.ueeiotLicence = ueeiotLicence;
    }
}
