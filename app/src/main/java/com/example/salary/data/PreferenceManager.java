package com.example.salary.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.health.ServiceHealthStats;

public class PreferenceManager {
    public static PreferenceManager preferenceManager = null;

    public final String PREFERENCE_NAME = "user_preference";

    private final String DEFAULT_VALUE_STRING = "";
    private final boolean DEFAULT_VALUE_BOOLEAN = false;
    private final int DEFAULT_VALUE_INT = -1;
    private final long DEFAULT_VALUE_LONG = -1L;
    private final float DEFAULT_VALUE_FLOAT = -1F;

    private Context mContext = null;

    public PreferenceManager() {
    }

    public static PreferenceManager getInstance() {
        if (preferenceManager == null) {
            preferenceManager = new PreferenceManager();
        }
        return preferenceManager;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    private SharedPreferences getPreferences() {
        return mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void setString(String key, String value) {
        SharedPreferences prefs = getPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setBoolean(String key, Boolean value) {
        SharedPreferences prefs = getPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setInt(String key, int value) {
        SharedPreferences prefs = getPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void setFloat(String key, float value) {
        SharedPreferences prefs = getPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public String getString(String key) {
        SharedPreferences prefs = getPreferences();
        String value = prefs.getString(key, DEFAULT_VALUE_STRING);
        return value;
    }

    public Boolean getBoolean(String key) {
        SharedPreferences prefs = getPreferences();
        Boolean value = prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN);
        return value;
    }

    public int getInt(String key) {
        SharedPreferences prefs = getPreferences();
        int value = prefs.getInt(key, DEFAULT_VALUE_INT);
        return value;
    }

    public float getFloat(String key) {
        SharedPreferences prefs = getPreferences();
        float value = prefs.getFloat(key, DEFAULT_VALUE_FLOAT);
        return value;
    }

    public void removeKey(String key) {
        SharedPreferences prefs = getPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        SharedPreferences prefs = getPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
