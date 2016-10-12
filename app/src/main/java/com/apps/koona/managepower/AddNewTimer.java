package com.apps.koona.managepower;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
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

public class AddNewTimer extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Spinner on_off_spinner;
    Spinner device_spinner;
    EditText settimeview;
    EditText setdateview;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_timer);

        calendar = Calendar.getInstance(TimeZone.getDefault());

        settimeview=(EditText) findViewById(R.id.settime);
        setdateview=(EditText) findViewById(R.id.setdate);

        settimeview.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = settimeview.getInputType(); // backup the input type
                settimeview.setInputType(InputType.TYPE_NULL); // disable soft input
                settimeview.onTouchEvent(event); // call native handler
                settimeview.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

        setdateview.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = setdateview.getInputType(); // backup the input type
                setdateview.setInputType(InputType.TYPE_NULL); // disable soft input
                setdateview.onTouchEvent(event); // call native handler
                setdateview.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

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
        device_names.add("Light");
        device_names.add("fan");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, device_names);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        device_spinner.setAdapter(dataAdapter);

        device_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        on_off_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show();

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
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                int on_off;
                if(on_off_spinner.getSelectedItem().toString()=="ON")
                    on_off=1;
                else
                    on_off=0;

                int device_id=1;

                Timer timer = new Timer(on_off,device_id,calendar);
                db.addTimer(timer);
                Toast.makeText(getApplicationContext(),
                        "New Timer Added",
                        Toast.LENGTH_SHORT).show();
                Log.d("AddnewTimer: ",timer.getCalendar().getTimeInMillis()+" ");

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
        Toast.makeText(getApplicationContext(), day+"-"+month+"-"+year, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user

        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        SimpleDateFormat formatter=new SimpleDateFormat("HH:mm");
        settimeview.setText(formatter.format(calendar.getTime()));
        Toast.makeText(getApplicationContext(), "ON Time is set", Toast.LENGTH_SHORT).show();
    }
}
