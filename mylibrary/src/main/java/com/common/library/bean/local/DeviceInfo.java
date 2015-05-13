package com.common.library.bean.local;

/**
 * Created by xing on 2015/4/19.
 */
public class DeviceInfo {
    /**
     * 设备型号
     */
    private String device_model;
    /**
     * 设备SDK版本
     */
    private String version_sdk;
    /**
     * 设备的系统版本
     */
    private String version_release;

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getVersion_sdk() {
        return version_sdk;
    }

    public void setVersion_sdk(String version_sdk) {
        this.version_sdk = version_sdk;
    }

    public String getVersion_release() {
        return version_release;
    }

    public void setVersion_release(String version_release) {
        this.version_release = version_release;
    }
}
