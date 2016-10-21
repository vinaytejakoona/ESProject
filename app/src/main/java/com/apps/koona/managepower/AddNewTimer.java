package com.apps.koona.managepower;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



import java.util.HashMap;
import java.util.Map;

public class AddNewTimer extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Spinner on_off_spinner;
    Spinner device_spinner;
    EditText settimeview;
    EditText setdateview;
    Calendar calendar;
    List<Device> deviceList;

    private String REGISTER_URL;

    public static final String KEY_DEVICE_ID = "device_id_val";
    public static final String KEY_DEVICE_NAME = "device_name";
    public static final String KEY_TIMER_ID = "timer_id";
    public static final String KEY_MILLISECONDS = "milliseconds";
    public static final String KEY_DATE = "date";
    public static final String KEY_ON_OFF = "on_off_db";
    public static final String KEY_TIME = "time_db";


    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_add_new_timer);

        String ipaddr = getResources().getString(R.string.ipaddr);
        REGISTER_URL = "http://"+ipaddr+"/dbConnect.php";



        calendar = Calendar.getInstance(TimeZone.getDefault());

        settimeview=(EditText) findViewById(R.id.settime);
        setdateview=(EditText) findViewById(R.id.setdate);

        setdateview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment= new DatePickerFragment();
                Log.d("clicked id = " , view.getId()+" ");
                datePickerFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        settimeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment= new TimePickerFragment();
                Log.d("clicked id = " , view.getId()+" ");
                timePickerFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });

        DatabaseHandler db = new DatabaseHandler(this);
        on_off_spinner = (Spinner) findViewById(R.id.on_off_options);

        device_spinner = (Spinner) findViewById(R.id.device_names);
        List<String> device_names = new ArrayList<String>();
        deviceList = new ArrayList<Device>();
        deviceList=db.getAllDevices();
        for(Device d:deviceList){
            device_names.add(d.getDeviceLabel());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, device_names);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        device_spinner.setAdapter(dataAdapter);

        device_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                Toast.makeText(parent.getContext(),
//                        "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        on_off_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                Toast.makeText(parent.getContext(),
//                        "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
//                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        Button add_timer_button = (Button) findViewById(R.id.add_timer);

        // Capture button clicks
        add_timer_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if( setdateview.getText().toString().trim().equals("")){

                    setdateview.setError( "date is required!" );
                    return;
                }
                if( settimeview.getText().toString().trim().equals("")){

                    settimeview.setError( "time is required!" );
                    return;
                }
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                int on_off;
                if(on_off_spinner.getSelectedItem().toString().equals("ON"))
                    on_off=1;
                else
                    on_off=0;

                int device_id=-1;
                String selectedDeviceLabel=device_spinner.getSelectedItem().toString();
                for(Device d:deviceList){
                    if(selectedDeviceLabel.equals(d.getDeviceLabel()))
                        device_id=d.getDeviceId();
                }
                if(device_id==-1)
                    return;

                Timer timer = new Timer(on_off,device_id,calendar);
                Log.d("Add new Timer: "+on_off," "+timer.getDeviceId()+" ");
                db.addTimer(timer);
//                Toast.makeText(getApplicationContext(),
//                        "New Timer Added",
//                        Toast.LENGTH_SHORT).show();
                Log.d("AddnewTimer: "+on_off," "+timer.getCalendar().getTimeInMillis()+" ");


                final String on_off_db = Integer.toString(timer.getOn_off());
                final String device_id_val = Integer.toString(timer.getDeviceId());
                final String milliseconds= Long.toString(timer.getCalendar().getTimeInMillis());
                final String date = setdateview.getText().toString().trim();
                final String time_db = settimeview.getText().toString().trim();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(AddNewTimer.this,response,Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AddNewTimer.this,error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put(KEY_DEVICE_ID,device_id_val);
                        params.put(KEY_ON_OFF,on_off_db);
                        params.put(KEY_DATE,date);
                        params.put(KEY_TIME,time_db);
                        params.put(KEY_MILLISECONDS,milliseconds);
                        return params;
                    }

                };
                RequestQueue requestQueue = Volley.newRequestQueue(AddNewTimer.this);
                requestQueue.add(stringRequest);
                Log.d("on add ","request sent to server : dbConnect.php");

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //do some stuff for example write on log and update TextField on activity

        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DATE,day);
        SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
        setdateview.setText(formatter.format(calendar.getTime()));
        //Toast.makeText(getApplicationContext(), day+"-"+month+"-"+year, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user

        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        SimpleDateFormat formatter=new SimpleDateFormat("HH:mm");
        settimeview.setText(formatter.format(calendar.getTime()));
        //Toast.makeText(getApplicationContext(), "ON Time is set", Toast.LENGTH_SHORT).show();
    }
}
