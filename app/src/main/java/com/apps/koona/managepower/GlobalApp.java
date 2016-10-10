package com.apps.koona.managepower;

import android.app.Application;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Vector;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;


public class GlobalApp extends Application {

        List<Profile> profiles;
        List<Timer> timers;

    private static GlobalApp globalApp= null;

    protected GlobalApp(){}

    public static synchronized GlobalApp getInstance(){
        if(null == globalApp){
            globalApp = new GlobalApp();
        }
        return globalApp;
    }

    public List<Timer> getTimers() {
        return timers;
    }

    public void setTimers(List<Timer> timers) {
        this.timers = timers;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
