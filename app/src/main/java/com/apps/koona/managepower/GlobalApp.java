package com.apps.koona.managepower;

import android.app.Application;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Vector;


import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class GlobalApp extends Application {


        DatabaseHandler db;
        List<Profile> profiles;
        List<Timer> timers;

    public DatabaseHandler getDb() {
        return db;
    }

    private static GlobalApp globalApp= null;

    protected GlobalApp(){

        db = new DatabaseHandler(this);

        Log.d("Insert: ", "Inserting ..");
        db.addProfile(new Profile("profile1",96.2,12.5));

        Log.d("Insert Timer: ", "Inserting ..");
        db.addTimer(new Timer(1,12, Calendar.getInstance(TimeZone.getTimeZone("UTC"))));
        db.addTimer(new Timer(2,14, Calendar.getInstance(TimeZone.getTimeZone("UTC"))));

        profiles = db.getAllProfiles();
        for (Profile p : profiles) {
            String log = "Id: "+p.getProfileId()+" ,Name: " + p.getName() + " ,Temp: " + p.getTemperature()+ " ,Humidity: " +p.getHumidity();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        Log.d("Insert: ", "Inserting ..");
        db.addProfile(new Profile("profile1",96.2,12.5));

        timers = db.getAllTimers();
        for (Timer t : timers) {
            String log = "Id: "+t.getId()+" ,on_off: " + t.getOn_off() + " ,Device ID: " + t.getDeviceId()+ " ,time : "+t.getCalendar().toString();
            Log.d("Name: ", log);
        }
    }

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
