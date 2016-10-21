package com.apps.koona.managepower;

/**
 * Created by Koona on 13-10-2016.
 */

public class Device {
    int deviceId;
    int onOff;
    String deviceLabel;
    static int numberOfDevices=0;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceLabel() {
        return deviceLabel;
    }

    public void setDeviceLabel(String deviceLabel) {
        this.deviceLabel = deviceLabel;
    }

    public int getOnOff() {
        return onOff;
    }

    public void setOnOff(int onOff) {
        this.onOff = onOff;
    }

    public Device(){
        deviceLabel="device";

        numberOfDevices++;


    }

    public Device(String deviceLabel,int onOff) {
        numberOfDevices++;
        this.onOff=onOff;
        this.deviceLabel = deviceLabel;
    }
}
