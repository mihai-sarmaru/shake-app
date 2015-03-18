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

    private static final String PREF_DATABASE_LATEST_ID = "databaseLatestId";
    private static final int PREF_DEFAULT_DATABASE_LATEST_ID = 0;

    private static final String PREF_UTC = "utcTime";
    private static final boolean PREF_DEFAULT_UTC = false;

    public ShakeAppPreferences (Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isServiceDone () {
        return prefs.getBoolean(PREF_SERVICE, PREF_DEFAULT_SERVICE);
    }

    public void setServiceDone (boolean done) {
        prefs.edit().putBoolean(PREF_SERVICE, done).apply();
    }

    public int getLatestDatabaseId () {
        return prefs.getInt(PREF_DATABASE_LATEST_ID, PREF_DEFAULT_DATABASE_LATEST_ID);
    }

    public void setLatestDatabaseId (int latestId) {
        prefs.edit().putInt(PREF_DATABASE_LATEST_ID, latestId).apply();
    }

    public boolean getUtcTime () {
        return prefs.getBoolean(PREF_UTC, PREF_DEFAULT_UTC);
    }

    public void setUtcTime (boolean useUtcTime) {
        prefs.edit().putBoolean(PREF_UTC, useUtcTime).apply();
    }
}
