package com.apps.koona.managepower;

import android.widget.SeekBar;
import android.widget.TextView;

public class Profile {
    double temperature;
    double humidity;
    String name;
    int profileId;

    static int numberOfProfiles=0;
    Profile(){
        numberOfProfiles++;
        temperature=50;
        humidity=50;
        name="profile_name";
    }

    Profile(String name,double temp,double humid){
        numberOfProfiles++;
        this.temperature=temp;
        this.humidity=humid;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public static int getNumberOfProfiles() {
        return numberOfProfiles;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getHumidity() {
        return humidity;
    }
}
