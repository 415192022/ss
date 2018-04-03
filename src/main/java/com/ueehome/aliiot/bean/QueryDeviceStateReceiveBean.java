package com.ueehome.aliiot.bean;

import java.util.List;

/**
 * Created by uee on 2018/2/5.
 */
public class QueryDeviceStateReceiveBean {
    private List<DeviceName> deviceNames;

    public void setDeviceNames(List<DeviceName> deviceNames) {
        this.deviceNames = deviceNames;
    }

    public List<DeviceName> getDeviceNames() {
        return deviceNames;
    }

    @Override
    public String toString() {
        return "QueryDeviceStateParmBean{" +
                "deviceNames=" + deviceNames +
                '}';
    }

    public static class DeviceName{
        private String name ;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "DeviceName{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
