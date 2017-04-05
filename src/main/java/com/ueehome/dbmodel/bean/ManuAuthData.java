package com.ueehome.dbmodel.bean;

/**
 * Created by TangWei on 2017/4/5.
 * 生产授权数据
 */
public class ManuAuthData {
    public String pid;
    public String pidSub;
    public Long prudDate;
    public Long prudDeadline;
    public Integer count;
    public Integer usedCount;
    public Boolean enable;

    /**
     * 生产授权数据
     *
     * @param pid          产品ID
     * @param pidSub       产品子ID
     * @param prudDate     生产开始日期
     * @param prudDeadline 生产截止日期
     * @param count        总数量
     * @param usedCount    已使用数量
     * @param enable       是否开启
     */
    public ManuAuthData(String pid, String pidSub, Long prudDate, Long prudDeadline, Integer count, Integer usedCount, Boolean enable) {
        this.pid = pid;
        this.pidSub = pidSub;
        this.prudDate = prudDate;
        this.prudDeadline = prudDeadline;
        this.count = count;
        this.usedCount = usedCount;
        this.enable = enable;
    }
}