package com.apps.koona.managepower;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

public class DatePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance(TimeZone.getDefault());
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        Log.d("callfrom: ",getActivity().getClass().getSimpleName()+" ");
        if(getActivity().getClass().getSimpleName().equals("TimersActivity"))
            return new DatePickerDialog(getActivity(),(TimersActivity)getActivity(), year, month, day);
        else
            return new DatePickerDialog(getActivity(),(AddNewTimer)getActivity(), year, month, day);
    }
}
