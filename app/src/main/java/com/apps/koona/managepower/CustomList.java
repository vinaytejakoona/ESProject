package com.apps.koona.managepower;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/**
 * Created by anbarasan on 9/10/16.
 */
public class CustomList extends ArrayAdapter<String> {
    private String[] temperatures;
    private String[] timestamps;
    private String[] milliseconds;
    private Activity context;

    public CustomList(Activity context, String[] milliseconds, String[] timestamps, String[] temperatures) {
        super(context, R.layout.list_view_layout,milliseconds);
        this.context = context;
        this.temperatures = temperatures;
        this.timestamps = timestamps;
        this.milliseconds = milliseconds;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view_layout, null, true);
        TextView textViewMilliSeconds = (TextView) listViewItem.findViewById(R.id.millisecond);
        TextView textViewTemperature = (TextView) listViewItem.findViewById(R.id.temperature);
        TextView textViewTimestamp = (TextView) listViewItem.findViewById(R.id.timestamp);

        textViewMilliSeconds.setText(milliseconds[position]);
        textViewTemperature.setText(temperatures[position]);
        textViewTimestamp.setText(timestamps[position]);

        return listViewItem;
    }
}