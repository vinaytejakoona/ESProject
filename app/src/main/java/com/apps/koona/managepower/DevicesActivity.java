package com.apps.koona.managepower;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
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
    private String REGISTER_URL;

    public static final String KEY_DEVICE_ID = "device_id";
    public static final String KEY_ON_OFF = "on_off";

    TextView deviceIdView;
    TextView deviceLabelView;
    Button deleteButton;
    Switch toggleButton;
    LinearLayout linearLayout;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_devices);

        String ipaddr = getResources().getString(R.string.ipaddr);
        REGISTER_URL = "http://"+ipaddr+"/OnOff.php";

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

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




            toggleButton = new Switch(this);
            if(devicesList.get(i).getOnOff()==1){
                toggleButton.setChecked(true);
            }
            else{
                toggleButton.setChecked(false);
            }

            toggleButton.setId(devicesList.get(i).getDeviceId()+3000);



            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String on_off_val;
                    int onOff;

                    if(isChecked) {
                        on_off_val = "1";
                        onOff=1;
                    }
                    else{
                        on_off_val = "0";
                        onOff=0;
                    }

                    final String device_id_val = Integer.toString(buttonView.getId()-3000);
                    DatabaseHandler db = new DatabaseHandler(DevicesActivity.this);


                    Device device = new Device("",onOff);
                    device.setDeviceId(buttonView.getId()-3000);
                    db.updateDevice(device);


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(DevicesActivity.this,response,Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(DevicesActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
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

                    Log.d("onclick ",device_id_val+" "+on_off_val+" sent to server");
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
