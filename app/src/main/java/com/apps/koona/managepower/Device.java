package com.apps.koona.managepower;

/**
 * Created by Koona on 13-10-2016.
 */

public class Device {
    int deviceId;
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

    public Device(){
        deviceLabel="device";
        numberOfDevices++;


    }

    public Device(String deviceLabel) {
        numberOfDevices++;
        this.deviceLabel = deviceLabel;
    }
}
