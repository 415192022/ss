package com.ueehome.dbmodel.bean;

/**
 * Created by TangWei on 2017/4/5.
 * 产品数据
 */
public class ProductData {
    public String ueeID;
    public String pid;
    public String pidSub;
    public Long prodDate;
    public String qqiotGUID;
    public String qqiotLicence;

    /**
     * 产品数据
     *
     * @param ueeID        ID
     * @param pid          产品ID
     * @param pidSub       产品子ID
     * @param prodDate     生产日期
     * @param qqiotGUID    QQ物联GUID
     * @param qqiotLicence QQ物联Licence
     */
    public ProductData(String ueeID, String pid, String pidSub, Long prodDate, String qqiotGUID, String qqiotLicence) {
        this.ueeID = ueeID;
        this.pid = pid;
        this.pidSub = pidSub;
        this.prodDate = prodDate;
        this.qqiotGUID = qqiotGUID;
        this.qqiotLicence = qqiotLicence;
    }
}
