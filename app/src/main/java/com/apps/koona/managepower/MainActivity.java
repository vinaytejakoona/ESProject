package com.apps.koona.managepower;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    public String JSON_URL;

    private Button refresh_button;

    //private ListView listView;

    Handler h;

    int delay; //milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

      //  db.dropTables();

        String ipaddr = getResources().getString(R.string.ipaddr);
        JSON_URL = "http://"+ipaddr+"/LiveData.php";



        refresh_button = (Button) findViewById(R.id.buttonGet);

        refresh_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(isNetworkAvailable()){
                    sendRequest();
                }
                else{
                    Toast.makeText(MainActivity.this,"network not available",Toast.LENGTH_LONG).show();
                }

               // showJSON("{\"result\":[{\"temperature\":\"12\",\"milliseconds\":\"11\",\"timestamp\":\"2016-10-20 11:10:59\",\"humidity\":\"17\"}]}");
                // Start NewActivity.class
                // Intent nextIntent = new Intent(MainActivity.this,ProfilesActivity.class);
                // startActivity(nextIntent);
            }
        });

        //listView = (ListView) findViewById(R.id.listView);

        h= new Handler();
        delay = 5000; //milliseconds

        h.postDelayed(new Runnable(){
            public void run(){
                sendRequest();
                h.postDelayed(this, delay);
            }
        }, delay);

        Button profile_button = (Button) findViewById(R.id.profiles);

        // Capture button clicks
        profile_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                 //Start NewActivity.class
                Intent nextIntent = new Intent(MainActivity.this,ProfilesActivity.class);
                startActivity(nextIntent);
            }
        });

        Button devices_button = (Button) findViewById(R.id.devices);




        // Capture button clicks
        devices_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent nextIntent = new Intent(MainActivity.this,
                        DevicesActivity.class);
                startActivity(nextIntent);
            }
        });

        Button add_new_device_button = (Button) findViewById(R.id.newdevice);

        // Capture button clicks
        add_new_device_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent nextIntent = new Intent(MainActivity.this,
                        AddNewDevice.class);
                startActivity(nextIntent);
            }
        });

        Button new_timer_button = (Button) findViewById(R.id.newtimer);

        // Capture button clicks
        new_timer_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent nextIntent = new Intent(MainActivity.this,
                        AddNewTimer.class);
                startActivity(nextIntent);
            }
        });

        Button timers_button = (Button) findViewById(R.id.timers);

        // Capture button clicks
        timers_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent nextIntent = new Intent(MainActivity.this,
                        TimersActivity.class);
                startActivity(nextIntent);
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

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("onError Response ","msg");
                        String body;
//                        String statusCode = String.valueOf(error.networkResponse.statusCode);
                        //get response body and parse with appropriate encoding
//                        if(error.networkResponse.data!=null) {
//                            try {
//                                body = new String(error.networkResponse.data,"UTF-8");
//                            } catch (UnsupportedEncodingException e) {
//                                e.printStackTrace();
//                            }
//                        }

                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        //CustomList cl = new CustomList(this, ParseJSON.milliseconds, ParseJSON.timestamps, ParseJSON.temperatures);
        //listView.setAdapter(cl);
        TextView temperatureView = (TextView) findViewById(R.id.temperature);
        TextView humidityView = (TextView) findViewById(R.id.humidity);
        temperatureView.setText(ParseJSON.temperatures[0]);
        humidityView.setText((ParseJSON.humidity[0]));

    }

}

