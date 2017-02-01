package com.attendanceTracker.auxiliary;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by tom on 01.02.17.
 */
public class Database {

    private SharedPreferences preferences;

    public Database(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void store(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String get(String key) {
        return preferences.getString(key, Const.NO_ENTRY);
    }

}
