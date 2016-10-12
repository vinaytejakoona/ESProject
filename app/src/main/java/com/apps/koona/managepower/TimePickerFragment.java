package com.apps.koona.managepower;

import android.support.v7.app.AppCompatActivity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        if(getActivity().getClass().getSimpleName().equals("TimersActivity"))
            return new TimePickerDialog(getActivity(), (TimersActivity)getActivity(), hour, minute,DateFormat.is24HourFormat(getActivity()));
        else
            return new TimePickerDialog(getActivity(), (AddNewTimer)getActivity(), hour, minute,DateFormat.is24HourFormat(getActivity()));

    }

}
