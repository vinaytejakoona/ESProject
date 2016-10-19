package embed.smarthome;

/**
 * Created by anbarasan on 9/10/16.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON {
    public static String[] ids;
    public static String[] Current1s;
    public static String[] Current2s;
    public static String[] Current3s;
    public static String[] Voltages;
    public static String[] Frequencys;
    public static String[] Phase1s;
    public static String[] Phase2s;
    public static String[] Phase3s;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_ID = "id";
    public static final String KEY_CURRENT1 = "Current1";
    public static final String KEY_CURRENT2 = "Current2";
    public static final String KEY_CURRENT3 = "Current3";
    public static final String KEY_VOLTAGE = "Voltage";
    public static final String KEY_FREQUENCY = "frequency";
    public static final String KEY_PHASE1 = "Phase1";
    public static final String KEY_PHASE2 = "Phase2";
    public static final String KEY_PHASE3 = "Phase3";

    private JSONArray users = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            ids = new String[users.length()];
            Current1s = new String[users.length()];
            Current2s = new String[users.length()];
            Current3s = new String[users.length()];
            Voltages = new String[users.length()];
            Frequencys = new String[users.length()];
            Phase1s = new String[users.length()];
            Phase2s = new String[users.length()];
            Phase3s = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                ids[i] = jo.getString(KEY_ID);
                Current1s[i] = jo.getString(KEY_CURRENT1);
                Current2s[i] = jo.getString(KEY_CURRENT2);
                Current3s[i] = jo.getString(KEY_CURRENT3);
                Voltages[i] = jo.getString(KEY_VOLTAGE);
                Frequencys[i] = jo.getString(KEY_FREQUENCY);
                Phase1s[i] = jo.getString(KEY_PHASE1);
                Phase2s[i] = jo.getString(KEY_PHASE2);
                Phase3s[i] = jo.getString(KEY_PHASE3);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}