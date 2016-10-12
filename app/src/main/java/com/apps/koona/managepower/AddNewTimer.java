package com.apps.koona.managepower;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddNewTimer extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_timer);

        timer= new Timer();
        DatabaseHandler db = new DatabaseHandler(this);
        Spinner on_off_spinner = (Spinner) findViewById(R.id.on_off_options);

        Spinner device_spinner = (Spinner) findViewById(R.id.device_names);
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
                if(parent.getItemAtPosition(pos).toString()=="ON"){
                    timer.setOn_off(1);
                }
                else{
                    timer.setOn_off(0);
                }
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
                db.addTimer(timer);
                Toast.makeText(getApplicationContext(),
                        "New Timer Added",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //do some stuff for example write on log and update TextField on activity
        Calendar calendar = timer.getCalendar();
        calendar.add(Calendar.YEAR,year);
        calendar.add(Calendar.MONTH,month);
        calendar.add(Calendar.DATE,day);
        timer.setCalendar(calendar);
        //Toast.makeText(getApplicationContext(), day+"-"+month+"-"+year, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        Calendar calendar = timer.getCalendar();
        calendar.add(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.add(Calendar.MINUTE,minute);
        timer.setCalendar(calendar);
        //Toast.makeText(getActivity(), "ON Time is set", Toast.LENGTH_SHORT).show();
    }
}
