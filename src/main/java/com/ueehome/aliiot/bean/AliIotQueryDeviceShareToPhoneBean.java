package com.ueehome.aliiot.bean;

/**
 * Created by uee on 2018/3/8.
 */
public class AliIotQueryDeviceShareToPhoneBean extends AliBaseBean<AliIotQueryDeviceShareToPhoneBean> {
    private String sharePhone;
    private String share_phone_number;
    private Long useTime;

    public void setSharePhone(String sharePhone) {
        this.sharePhone = sharePhone;
    }

    public void setShare_phone_number(String share_phone_number) {
        this.share_phone_number = share_phone_number;
    }

    public void setUseTime(Long useTime) {
        this.useTime = useTime;
    }

    public String getSharePhone() {
        return sharePhone;
    }

    public String getShare_phone_number() {
        return share_phone_number;
    }

    public Long getUseTime() {
        return useTime;
    }

    @Override
    public String toString() {
        return "AliIotQueryDeviceShareToPhoneBean{" +
                "sharePhone='" + sharePhone + '\'' +
                ", share_phone_number='" + share_phone_number + '\'' +
                ", useTime=" + useTime +
                '}';
    }
}
