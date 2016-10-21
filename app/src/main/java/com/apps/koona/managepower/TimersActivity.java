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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_timers);

        DatabaseHandler db= new DatabaseHandler(getApplicationContext());
        List<Timer> timersList = db.getAllTimers();
        timerviews = new ArrayList<EditText>();
        dateviews = new ArrayList<EditText>();
        textviews = new ArrayList<TextView>();

        for (Timer t : timersList) {
            String log = "Id: " + t.getId() + " ,on_off: " + t.getOn_off() + " ,Device ID: " + t.getDeviceId() + " ,time : " + t.getCalendar().getTimeInMillis();
            Log.d("start timers activity: ", log);
        }


        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.timersList);

        TextView devicenameview;
        TextView onoffview;
        EditText dateview;
        EditText timeview;
        Button button;
        Spinner on_off_spinner;
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < timersList.size(); i++){

            // fill in any details dynamically here

            if(db.getDevice(timersList.get(i).getDeviceId())==null)
                continue;

            on_off_spinner = new Spinner(this);
            List<String> on_off_options = new ArrayList<String>();
            on_off_options.add("ON");
            on_off_options.add("OFF");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, on_off_options);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            on_off_spinner.setAdapter(dataAdapter);
            if(timersList.get(i).getOn_off()==1){
                on_off_spinner.setSelection(0);
            }
            else
                on_off_spinner.setSelection(1);

            on_off_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                    Toast.makeText(parent.getContext(),
//                            "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
//                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
            devicenameview = new TextView(this);
            devicenameview.setMinEms(2);
            button = new Button(this);
            button.setText("del");
            button.setId(timersList.get(i).getId()+9000);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    int timerid=v.getId()-9000;
                    db.deleteTimer(timerid);
                    ViewGroup layout = (ViewGroup) v.getParent();
                    ViewGroup parent = (ViewGroup) layout.getParent();
                    if(null!=layout) //for safety only  as you are doing onClick
                        parent.removeView(layout);
                }
            });

            //button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.,null,null,null);




            devicenameview.setText(db.getDevice(timersList.get(i).getDeviceId()).getDeviceLabel());

            // Add the text view to the parent layout
            dateview = new EditText(this);

            dateview.setLayoutParams(layoutParams);
            dateview.setHint("Date");
            dateview.setId(i);
            dateview.setFocusable(false);
            dateview.setClickable(true);
            Calendar calendar=timersList.get(i).getCalendar();
            dateview.setText(calendar.get(Calendar.DATE)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR));

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
            timeview.setText((calendar.get(Calendar.HOUR_OF_DAY))+":"+calendar.get(Calendar.MINUTE));

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
            linearLayout.addView(devicenameview);
            linearLayout.addView(on_off_spinner);
            linearLayout.addView(dateview);
            linearLayout.addView(timeview);
            linearLayout.addView(button);
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
