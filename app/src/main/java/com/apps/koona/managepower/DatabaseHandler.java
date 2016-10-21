package com.apps.koona.managepower;

import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "PowerManager";

    // Contacts table name
    private static final String TABLE_PROFILES = "Profiles";
    private static final String TABLE_TIMERS = "Timers";
    private static final String TABLE_DEVICES = "Devices";

    // Contacts Table Columns names
    private static final String KEY_PROFILE_ID = "profile_id";
    private static final String KEY_NAME = "profile_name";
    private static final String KEY_TEMPERATURE = "temperature";
    private static final String KEY_HUMIDITY = "humidity";

    private static final String KEY_TIMER_ID = "timer_id";
    private static final String KEY_ON_OFF = "on_off";
    private static final String KEY_DEVICE_ID = "device_id";
    private static final String KEY_MILLISECONDS = "milliseconds";


    private static final String KEY_DEVICE_LABEL= "device_label";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onOpen(SQLiteDatabase db){
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    public void dropTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICES);

        String CREATE_PROFILES_TABLE = "CREATE TABLE " + TABLE_PROFILES + "("
                + KEY_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_TEMPERATURE + " DOUBLE" + KEY_HUMIDITY + " DOUBLE)";
        db.execSQL(CREATE_PROFILES_TABLE);

        Log.d("create timers table","befor....");
        String CREATE_DEVICES_TABLE = "CREATE TABLE " + TABLE_DEVICES + "("
                + KEY_DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DEVICE_LABEL + " TEXT,"+ KEY_ON_OFF +" INTEGER DEFAULT 0)";
        db.execSQL(CREATE_DEVICES_TABLE);

        String CREATE_TIMERS_TABLE = "CREATE TABLE " + TABLE_TIMERS + "("
                + KEY_TIMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ON_OFF + " INTEGER," + KEY_DEVICE_ID + " INTEGER REFERENCES "+TABLE_DEVICES +"("+KEY_DEVICE_ID+") ON DELETE CASCADE,"+ KEY_MILLISECONDS + " INTEGER )";

        db.execSQL(CREATE_TIMERS_TABLE);
        db.close(); // Closing database connection
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PROFILES_TABLE = "CREATE TABLE " + TABLE_PROFILES + "("
                + KEY_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_TEMPERATURE + " DOUBLE" + KEY_HUMIDITY + " DOUBLE)";
        db.execSQL(CREATE_PROFILES_TABLE);
        Log.d("create timers table","befor....");


        String CREATE_DEVICES_TABLE = "CREATE TABLE " + TABLE_DEVICES + "("
                + KEY_DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DEVICE_LABEL + " TEXT,"+ KEY_ON_OFF +" INTEGER DEFAULT 0)";
        db.execSQL(CREATE_DEVICES_TABLE);

        String CREATE_TIMERS_TABLE = "CREATE TABLE " + TABLE_TIMERS + "("
                + KEY_TIMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ON_OFF + " INTEGER," + KEY_DEVICE_ID + " INTEGER REFERENCES "+TABLE_DEVICES +"("+KEY_DEVICE_ID+") ON DELETE CASCADE,"+ KEY_MILLISECONDS + " INTEGER )";

        db.execSQL(CREATE_TIMERS_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICES);
        // Create tables again
        onCreate(db);
        db.close();
    }

    void addProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, profile.getName());
        values.put(KEY_TEMPERATURE, profile.getTemperature());
        values.put(KEY_HUMIDITY,profile.getHumidity());

        // Inserting Row
        db.insert(TABLE_PROFILES, null, values);
        db.close(); // Closing database connection
    }

    void addDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DEVICE_LABEL, device.getDeviceLabel());

        // Inserting Row
        db.insert(TABLE_DEVICES, null, values);
        db.close(); // Closing database connection
    }

    void addTimer(Timer timer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ON_OFF, timer.getOn_off());
        values.put(KEY_DEVICE_ID, timer.getDeviceId());
        values.put(KEY_MILLISECONDS,timer.getCalendar().getTimeInMillis());

        // Inserting Row
        db.insert(TABLE_TIMERS, null, values);

        Log.d("addtimer:date",timer.getCalendar().get(Calendar.YEAR)+"-"+timer.getCalendar().get(Calendar.MONTH)+"-"+timer.getCalendar().get(Calendar.DATE)+"-"+timer.getCalendar().get(Calendar.HOUR_OF_DAY)+"-"+timer.getCalendar().get(Calendar.MINUTE));
        Log.d("addTimer: ",values.get(KEY_MILLISECONDS).toString());
        db.close(); // Closing database connection
    }


    Profile getProfile(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROFILES, new String[] { KEY_NAME, KEY_TEMPERATURE, KEY_HUMIDITY }, KEY_PROFILE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Profile profile = new Profile(cursor.getString(0), cursor.getDouble(1), cursor.getDouble(2));

        db.close();
        return profile;
    }

    Device getDevice(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DEVICES, new String[] { KEY_DEVICE_LABEL,KEY_ON_OFF }, KEY_DEVICE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        Device device = new Device(cursor.getString(0),cursor.getInt(1));

        db.close();
        return device;
    }

    public List<Profile> getAllProfiles() {
        List<Profile> profileList = new ArrayList<Profile>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Profile profile = new Profile();
                profile.setProfileId(Integer.parseInt(cursor.getString(0)));
                profile.setName(cursor.getString(1));
                profile.setTemperature(Double.parseDouble(cursor.getString(2)));
                profile.setHumidity(Double.parseDouble(cursor.getString(3)));

                // Adding contact to list
                profileList.add(profile);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return profileList;
    }

    public List<Timer> getAllTimers() {
        List<Timer> timerList = new ArrayList<Timer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TIMERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Timer timer = new Timer();
                timer.setId(cursor.getInt(0));
                timer.setOn_off(cursor.getInt(1));
                timer.setDeviceId(cursor.getInt(2));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTimeInMillis(cursor.getLong(3));
                timer.setCalendar(calendar);
                timerList.add(timer);
            } while (cursor.moveToNext());
        }

        db.close();
        return timerList;
    }


    public List<Device> getAllDevices() {
        List<Device> deviceList = new ArrayList<Device>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DEVICES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Device device = new Device();
                device.setDeviceId(cursor.getInt(0));
                device.setDeviceLabel(cursor.getString(1));
                device.setOnOff(cursor.getInt(2));
                Log.d("db..get all devices: ",device.getDeviceLabel());
                deviceList.add(device);
            } while (cursor.moveToNext());
        }

        db.close();
        return deviceList;
    }


    public int updateProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, profile.getName());
        values.put(KEY_TEMPERATURE, profile.getTemperature());
        values.put(KEY_HUMIDITY, profile.getHumidity());

        // updating row
         int r=db.update(TABLE_PROFILES, values, KEY_PROFILE_ID + " = ?",
                new String[] { String.valueOf(profile.getProfileId()) });
        db.close();
        return r;
    }

    public void deleteProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROFILES, KEY_PROFILE_ID + " = ?",
                new String[] { String.valueOf(profile.getProfileId()) });
        db.close();
    }



    public int updateTimer(Timer timer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ON_OFF, timer.getOn_off());
        values.put(KEY_DEVICE_ID, timer.getDeviceId());


        // updating row
        int r= db.update(TABLE_TIMERS, values, KEY_TIMER_ID + " = ?",
                new String[] { String.valueOf(timer.getId()) });
        db.close();
        return r;
    }

    public int updateDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ON_OFF, device.getOnOff());

        // updating row
        int r= db.update(TABLE_DEVICES, values, KEY_DEVICE_ID + " = ?",
                new String[] { String.valueOf(device.getDeviceId()) });

        Log.d("update Device",device.getDeviceId()+" "+device.getOnOff());
        db.close();
        return r;
    }


    public void deleteTimer(int timerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TIMERS, KEY_TIMER_ID + " = ?",
                new String[] { String.valueOf(timerId) });
        db.close();
    }

    public void deleteDevice(int deviceId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DEVICES, KEY_DEVICE_ID + " = ?",
                new String[] { String.valueOf(deviceId) });
        db.close();
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PROFILES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }

    public int getTimersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TIMERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }
}
