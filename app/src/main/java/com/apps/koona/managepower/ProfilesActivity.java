package com.apps.koona.managepower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class ProfilesActivity extends AppCompatActivity {

    private List<SeekBar> TemperatureSeekBars;
    private List<TextView> TemperatureValues;

    private List<SeekBar> HumiditySeekBars;
    private List<TextView> HumidityValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        initializeVariables();




//        temperature1seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//            int progress = 0;
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
//                progress = progresValue;
//                //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                temperature1value.setText(progress + "/" + seekBar.getMax());
//                //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    private void initializeVariables() {

       // ((MyApplication) this.getApplication()).setSomeVariable("foo");



    }

}
