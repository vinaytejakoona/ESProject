package com.apps.koona.managepower;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        DatabaseHandler db = new DatabaseHandler(this);

        initialiseGlobalApp(db);
        Button profile_button = (Button) findViewById(R.id.profiles);

        // Capture button clicks
        profile_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
               // Intent nextIntent = new Intent(MainActivity.this,ProfilesActivity.class);
               // startActivity(nextIntent);
            }
        });

        Button controls_button = (Button) findViewById(R.id.controls);

        // Capture button clicks
        controls_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent nextIntent = new Intent(MainActivity.this,
                        ControlsActivity.class);
                startActivity(nextIntent);
            }
        });

        Button new_timer_button = (Button) findViewById(R.id.newtimer);

        // Capture button clicks
        new_timer_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent nextIntent = new Intent(MainActivity.this,
                        AddNewTimer.class);
                startActivity(nextIntent);
            }
        });

        Button timers_button = (Button) findViewById(R.id.timers);

        // Capture button clicks
        timers_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent nextIntent = new Intent(MainActivity.this,
                        TimersActivity.class);
                startActivity(nextIntent);
            }
        });
    }



    public void initialiseGlobalApp(DatabaseHandler db) {



//        Log.d("Insert: ", "Inserting ..");
//        db.addProfile(new Profile("profile1", 96.2, 12.5));
//        db.dropTables();
        Log.d("Insert Timer: ", "Inserting ..");

        //db.addTimer(new Timer(1, 10, Calendar.getInstance(TimeZone.getTimeZone("UTC"))));
        //db.addTimer(new Timer(0, 14, Calendar.getInstance(TimeZone.getTimeZone("UTC"))));

        List<Profile> profiles = db.getAllProfiles();
        for (Profile p : profiles) {
            String log = "Id: " + p.getProfileId() + " ,Name: " + p.getName() + " ,Temp: " + p.getTemperature() + " ,Humidity: " + p.getHumidity();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }




    }

}

