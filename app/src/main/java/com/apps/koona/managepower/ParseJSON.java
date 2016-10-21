package com.apps.koona.managepower;

/**
 * Created by anbarasan on 20/10/16.
 */

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON {
    public static String[] temperatures;
    public static String[] timestamps;
    public static String[] milliseconds;
    public static String[] humidity;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_TEMPERATURE = "temperature";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_MILLISECONDS = "milliseconds";
    public static final String KEY_HUMIDITY = "humidity";

    private JSONArray result = null;

    private String json;

    public ParseJSON(String json){
        Log.d("ParseJson ",json);
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            //jsonObject = new JSONObject("{\"result\":[{\"temperature\":\"12\",\"milliseconds\":\"11\",\"timestamp\":\"2016-10-20 11:10:59\",\"humidity\":\"17\"}]}");
            result = jsonObject.getJSONArray(JSON_ARRAY);

            temperatures = new String[result.length()];
            timestamps = new String[result.length()];
            milliseconds = new String[result.length()];
            humidity = new String[result.length()];


            for(int i=0;i<result.length();i++){
                JSONObject jo = result.getJSONObject(i);
                temperatures[i] = jo.getString(KEY_TEMPERATURE);
                timestamps[i] = jo.getString(KEY_TIMESTAMP);
                milliseconds[i] = jo.getString(KEY_MILLISECONDS);
                humidity[i]=jo.getString(KEY_HUMIDITY);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}