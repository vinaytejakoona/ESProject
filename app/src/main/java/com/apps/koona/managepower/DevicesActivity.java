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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevicesActivity extends AppCompatActivity {
    private static final String REGISTER_URL = "http://192.168.1.101/OnOff.php";

    public static final String KEY_DEVICE_ID = "device_id";
    public static final String KEY_ON_OFF = "on_off";

    TextView deviceIdView;
    TextView deviceLabelView;
    Button deleteButton;
    ToggleButton toggleButton;
    LinearLayout linearLayout;

    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_devices);
        Log.d("devices activity","");
        DatabaseHandler db= new DatabaseHandler(getApplicationContext());
        List<Device> devicesList = db.getAllDevices();


        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.devicesList);


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

            toggleButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    final String on_off_val;
                    // your click actions go here
                    if(toggleButton.isChecked()){
                       on_off_val = "1";
                    }
                    else{
                       on_off_val = "0";
                    }

                    final String device_id_val = deviceIdView.getText().toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(DevicesActivity.this,response,Toast.LENGTH_LONG).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(DevicesActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                                }
                            }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put(KEY_DEVICE_ID,device_id_val);
                            params.put(KEY_ON_OFF,on_off_val);
                            return params;
                        }

                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(DevicesActivity.this);
                    requestQueue.add(stringRequest);

                    Log.d("onclick ",on_off_val+" "+device_id_val+" sent to server");
                }
            });

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
