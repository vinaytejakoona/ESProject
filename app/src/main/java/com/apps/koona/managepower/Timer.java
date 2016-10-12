package com.apps.koona.managepower;


import android.widget.EditText;

import java.util.Calendar;
import java.util.TimeZone;

public class Timer {
    int on_off;
    int id;
    int deviceId;
    Calendar calendar;


    public int getDeviceId() {

        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    static int numberOfTimers=0;

    public int getOn_off() {
        return on_off;
    }

    public void setOn_off(int on_off) {
        this.on_off = on_off;
    }

    Timer(){
        on_off=1;
        numberOfTimers++;
        this.calendar= Calendar.getInstance(TimeZone.getDefault());
        deviceId=1;
    }

    Timer(int on_off,int deviceId,Calendar calendar){
        numberOfTimers++;
        this.calendar=calendar;
        this.on_off=on_off;
        this.deviceId=deviceId;
    }




    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public static int getNumberOfTimers() {
        return numberOfTimers;
    }


}
