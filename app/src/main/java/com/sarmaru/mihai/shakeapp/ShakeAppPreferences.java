package com.sarmaru.mihai.shakeapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Mihai Sarmaru on 27.01.2015.
 */
public class ShakeAppPreferences {

    private static SharedPreferences prefs;

    private static final String PREF_SERVICE = "service";
    private static final boolean PREF_DEFAULT_SERVICE = false;

    public ShakeAppPreferences (Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isServiceDone () {
        return prefs.getBoolean(PREF_SERVICE, PREF_DEFAULT_SERVICE);
    }

    public void setServiceDone (boolean done) {
        prefs.edit().putBoolean(PREF_SERVICE, done).apply();
    }
}
