package com.apps.koona.managepower;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimersActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {

    List<EditText> timerviews;
    List<EditText>  dateviews;
    List<TextView> textviews;
    int currentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timers);
        List<Timer> timersList = GlobalApp.getInstance().getTimers();
        timerviews = new ArrayList<EditText>();
        dateviews = new ArrayList<EditText>();
        textviews = new ArrayList<TextView>();


        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.activity_timers, null);



        // insert into main view
        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.timersList);

        TextView textview;
        EditText dateview;
        EditText timeview;
        LinearLayout.LayoutParams layoutParams;

        DatePicker datePickerView;

        for (int i = 0; i < timersList.size(); i++){

            // fill in any details dynamically here


            textview = new TextView(this);

            textview.setText(Integer.toString(timersList.get(i).getDeviceId()));
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            // Add the text view to the parent layout
            dateview = new EditText(this);

            dateview.setLayoutParams(layoutParams);
            dateview.setHint("Date");
            dateview.setId(i);
            dateview.setFocusable(false);
            dateview.setClickable(true);

            dateview.setInputType(0x00000014);
            dateviews.add(dateview);

            dateview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerFragment datePickerFragment= new DatePickerFragment();
                    Log.d("clicked id = " , view.getId()+" ");
                    currentID=view.getId();
                    datePickerFragment.show(getSupportFragmentManager(), "DatePicker");
                }
            });

            timeview = new EditText(this);

            timeview.setLayoutParams(layoutParams);
            timeview.setHint("Time");
            timeview.setId(i+5000);
            timeview.setFocusable(false);
            timeview.setClickable(true);

            timeview.setInputType(0x00000024);

            timeview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerFragment timePickerFragment= new TimePickerFragment();
                    Log.d("clicked id = " , view.getId()+" ");
                    currentID=view.getId();
                    timePickerFragment.show(getSupportFragmentManager(), "TimePicker");
                }
            });

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            //linearLayout.setId(i);
            linearLayout.addView(textview);
            linearLayout.addView(dateview);
            linearLayout.addView(timeview);
            insertPoint.addView(linearLayout,layoutParams);

        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //do some stuff for example write on log and update TextField on activity
        Log.d("DatePicker","Date = " + year);

        EditText dateview = (EditText)findViewById(currentID);
        dateview.setText(day+"-"+month+"-"+year);
        //Toast.makeText(getApplicationContext(), day+"-"+month+"-"+year, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        EditText timeview = (EditText)findViewById(currentID);
        timeview.setText(hourOfDay+":"+minute);
        //Toast.makeText(getActivity(), "ON Time is set", Toast.LENGTH_SHORT).show();
    }


}
