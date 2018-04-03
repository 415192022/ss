package com.ueehome.apibean;

/**
 * Created by uee on 2018/1/5.
 */
public class WXDeviceBean {
    private String device_id;
    private String device_type;
    private String msg_id;
    private String msg_type;
    private Long create_time;
    private String open_id;
    private String session_id;
    private String content;
    private String qrcode_suffix_data;

    public String getDevice_id() {
        return device_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public String getOpen_id() {
        return open_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getContent() {
        return content;
    }

    public String getQrcode_suffix_data() {
        return qrcode_suffix_data;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setQrcode_suffix_data(String qrcode_suffix_data) {
        this.qrcode_suffix_data = qrcode_suffix_data;
    }

    @Override
    public String toString() {
        return "WXDeviceBean{" +
                "device_id='" + device_id + '\'' +
                ", device_type='" + device_type + '\'' +
                ", msg_id='" + msg_id + '\'' +
                ", msg_type='" + msg_type + '\'' +
                ", create_time='" + create_time + '\'' +
                ", open_id='" + open_id + '\'' +
                ", session_id='" + session_id + '\'' +
                ", content='" + content + '\'' +
                ", qrcode_suffix_data='" + qrcode_suffix_data + '\'' +
                '}';
    }
}
