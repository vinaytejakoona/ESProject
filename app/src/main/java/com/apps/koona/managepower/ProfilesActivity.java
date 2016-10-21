package com.apps.koona.managepower;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ProfilesActivity extends AppCompatActivity {

    String ipaddr;
    public String REGISTER_URL;
    Button setProfileButton;
    EditText temperature;
    EditText humidity;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public static final String KEY_TEMPERATURE = "temperature";
    public static final String KEY_HUMIDITY = "humidity";

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_profiles);
        ipaddr = getResources().getString(R.string.ipaddr);
        REGISTER_URL = "http://"+ipaddr+"/SetProfile.php";

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        setProfileButton = (Button) findViewById(R.id.new_profile);

        temperature=(EditText)findViewById(R.id.temperature);
        humidity=(EditText)findViewById(R.id.humidity);

        temperature.setText(preferences.getString(KEY_TEMPERATURE,"temperature"));
        humidity.setText(preferences.getString(KEY_HUMIDITY,"humidity"));

//                Toast.makeText(ProfilesActivity.this,temperature.getText().toString()+" "+humidity.getText().toString(),Toast.LENGTH_LONG).show();


        setProfileButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if(isNetworkAvailable()){
                    editor.putString(KEY_TEMPERATURE, temperature.getText().toString());
                    editor.putString(KEY_HUMIDITY, humidity.getText().toString());
                    editor.commit();
                    sendRequest();
                }
                else{
                    Toast.makeText(ProfilesActivity.this,"network not available",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void sendRequest(){

        final String temperature_val = temperature.getText().toString();
        final String humidity_val = humidity.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ProfilesActivity.this,response,Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfilesActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_TEMPERATURE,temperature_val);
                params.put(KEY_HUMIDITY,humidity_val);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(ProfilesActivity.this);
        requestQueue.add(stringRequest);

        Log.d("onclick ",temperature_val+" "+humidity_val+" sent to server");
    }



}
