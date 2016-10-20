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

    public static final String JSON_ARRAY = "result";
    public static final String KEY_TEMPERATURE = "temperature";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_MILLISECONDS = "milliseconds";

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
            //jsonObject = new JSONObject("{result:[{\"temperature\":\"25\",\"milliseconds\":\"1\",\"timestamp\":\"2016-10-19 23:18:20\"},{\"temperature\":\"25\",\"milliseconds\":\"2\",\"timestamp\":\"2016-10-19 23:21:02\"}]}");
            result = jsonObject.getJSONArray(JSON_ARRAY);

            temperatures = new String[result.length()];
            timestamps = new String[result.length()];
            milliseconds = new String[result.length()];


            for(int i=0;i<result.length();i++){
                JSONObject jo = result.getJSONObject(i);
                temperatures[i] = jo.getString(KEY_TEMPERATURE);
                timestamps[i] = jo.getString(KEY_TIMESTAMP);
                milliseconds[i] = jo.getString(KEY_MILLISECONDS);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}