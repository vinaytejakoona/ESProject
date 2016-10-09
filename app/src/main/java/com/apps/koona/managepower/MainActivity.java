package com.apps.koona.managepower;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button profile_button = (Button) findViewById(R.id.profiles);

        // Capture button clicks
        profile_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent nextIntent = new Intent(MainActivity.this,
                        Profiles.class);
                startActivity(nextIntent);
            }
        });

        Button controls_button = (Button) findViewById(R.id.controls);

        // Capture button clicks
        controls_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent nextIntent = new Intent(MainActivity.this,
                        Controls.class);
                startActivity(nextIntent);
            }
        });
    }

}

