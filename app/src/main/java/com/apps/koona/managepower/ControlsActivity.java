package com.apps.koona.managepower;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ControlsActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment ontime = new TimePickerFragment();
        ontime.show(getSupportFragmentManager(), "timePicker");
    }


    public void showDatePickerDialog(View v) {
        DialogFragment ondate = new DatePickerFragment();
        ondate.show(getSupportFragmentManager(), "datePicker");
    }
}
