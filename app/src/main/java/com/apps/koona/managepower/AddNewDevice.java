package com.apps.koona.managepower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewDevice extends AppCompatActivity {

    EditText deviceLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_device);


        deviceLabel=(EditText) findViewById(R.id.deviceLabel);

        Button add_device_button = (Button) findViewById(R.id.addTimer);

        // Capture button clicks
        add_device_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                Device device = new Device(deviceLabel.getText().toString(),0);
                db.addDevice(device);
                Toast.makeText(getApplicationContext(),
                        "New Device Added",
                        Toast.LENGTH_SHORT).show();
                Log.d("AddnewDevice: ",device.getDeviceLabel());

            }
        });
    }

}
