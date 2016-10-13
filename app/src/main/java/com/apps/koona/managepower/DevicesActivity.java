package com.apps.koona.managepower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

public class DevicesActivity extends AppCompatActivity {


    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_devices);
        Log.d("devices activity","");
        DatabaseHandler db= new DatabaseHandler(getApplicationContext());
        List<Device> devicesList = db.getAllDevices();


        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.devicesList);

        TextView deviceIdView;
        TextView deviceLabelView;
        Button deleteButton;
        ToggleButton toggleButton;
        LinearLayout linearLayout;
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        for(int i=0;i<devicesList.size();i++){


            deleteButton = new Button(this);
            deleteButton.setText("del");
            deleteButton.setId(devicesList.get(i).getDeviceId()+9000);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    int deviceId=v.getId()-9000;
                    db.deleteDevice(deviceId);
                    ViewGroup layout = (ViewGroup) v.getParent();
                    ViewGroup parent = (ViewGroup) layout.getParent();
                    if(null!=layout) //for safety only  as you are doing onClick
                        parent.removeView(layout);
                }
            });

            deviceIdView = new TextView(this);
            deviceIdView.setMinEms(3);
            deviceIdView.setText(Integer.toString(devicesList.get(i).getDeviceId()));

            deviceLabelView = new TextView(this);
            deviceLabelView.setMinEms(6);
            //deviceLabelView.setInputType(0x00000001);
            deviceLabelView.setText(devicesList.get(i).getDeviceLabel());

            toggleButton = new ToggleButton(this);
            toggleButton.setSelected(false);

            linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            //linearLayout.setId(i);
            linearLayout.addView(deviceIdView);
            linearLayout.addView(deviceLabelView);
            linearLayout.addView(toggleButton);
            linearLayout.addView(deleteButton);
            insertPoint.addView(linearLayout,layoutParams);

        }

    }
}
