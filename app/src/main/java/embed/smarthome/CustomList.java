package embed.smarthome;

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
    private String[] ids;
    private String[] Current1s;
    private String[] Current2s;
    private String[] Current3s;
    private String[] Voltages;
    private String[] Frequencys;
    private String[] Phase1s;
    private String[] Phase2s;
    private String[] Phase3s;
    private Activity context;

    public CustomList(Activity context, String[] ids, String[] Current1s, String[] Current2s,String[] Current3s,String[] Voltages,String[] Frequencys,String[] Phase1s,String[] Phase2s,String[] Phase3s) {
        super(context, R.layout.list_view_layout, ids);
        this.context = context;
        this.ids = ids;
        this.Current1s = Current1s;
        this.Current2s = Current2s;
        this.Current3s = Current3s;
        this.Voltages = Voltages;
        this.Frequencys = Frequencys;
        this.Phase1s = Phase1s;
        this.Phase2s = Phase2s;
        this.Phase3s = Phase3s;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view_layout, null, true);
        TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
        TextView textViewCurrent1 = (TextView) listViewItem.findViewById(R.id.textViewCurrent1);
        TextView textViewCurrent2 = (TextView) listViewItem.findViewById(R.id.textViewCurrent2);
        TextView textViewCurrent3 = (TextView) listViewItem.findViewById(R.id.textViewCurrent3);
        TextView textViewVoltage = (TextView) listViewItem.findViewById(R.id.textViewVoltage);
        TextView textViewFrequency = (TextView) listViewItem.findViewById(R.id.textViewFrequency);
        TextView textViewPhase1 = (TextView) listViewItem.findViewById(R.id.textViewPhase1);
        TextView textViewPhase2 = (TextView) listViewItem.findViewById(R.id.textViewPhase2);
        TextView textViewPhase3 = (TextView) listViewItem.findViewById(R.id.textViewPhase3);
        textViewId.setText(ids[position]);
        textViewCurrent1.setText(Current1s[position]);
        textViewCurrent2.setText(Current2s[position]);
        textViewCurrent3.setText(Current3s[position]);
        textViewVoltage.setText(Voltages[position]);
        textViewFrequency.setText(Frequencys[position]);
        textViewPhase1.setText(Phase1s[position]);
        textViewPhase2.setText(Phase2s[position]);
        textViewPhase3.setText(Phase3s[position]);
        return listViewItem;
    }
}
