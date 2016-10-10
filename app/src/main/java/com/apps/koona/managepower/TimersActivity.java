package com.apps.koona.managepower;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TimersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timers);

        DatabaseHandler db  = ((GlobalApp) this.getApplication()).getDb();
        List<Timer> timersList = db.getAllTimers();


        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.activity_timers, null);



        // insert into main view
        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.timersList);

        TextView textview;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < timersList.size(); i++){

            // fill in any details dynamically here
            textview = new TextView(this);
            textview.setText(timersList.get(i).getDeviceId());
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            // Add the text view to the parent layout

            insertPoint.addView(textview,layoutParams);
        }
    }





}
