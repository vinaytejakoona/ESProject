package com.apps.koona.managepower;

import android.app.DatePickerDialog;
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

import java.util.ArrayList;
import java.util.List;

public class TimersActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timers);
        List<Timer> timersList = GlobalApp.getInstance().getTimers();


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
            dateview.setFocusable(false);
            dateview.setClickable(true);

            dateview.setInputType(0x00000014);

            dateview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerFragment datePickerFragment= new DatePickerFragment();
                    datePickerFragment.show(getSupportFragmentManager(), "DatePicker");
                }
            });

            timeview = new EditText(this);

            timeview.setLayoutParams(layoutParams);
            timeview.setHint("Time");
            timeview.setFocusable(false);
            timeview.setClickable(true);

            timeview.setInputType(0x00000024);

            timeview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerFragment timePickerFragment= new TimePickerFragment();
                    timePickerFragment.show(getSupportFragmentManager(), "TimePicker");
                }
            });

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setId(i);
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
        //((EditText) findViewById(R.id.tf_date)).setText( day+"-"+month+"-"+year);
    }

}
