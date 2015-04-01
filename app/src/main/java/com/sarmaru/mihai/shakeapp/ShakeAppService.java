package com.sarmaru.mihai.shakeapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.List;

/**
 * Created by Mihai Sarmaru on 19.01.2015.
 */
public class ShakeAppService extends IntentService {

    private static String URL_KEY = "url";
    ShakeAppPreferences prefs;

    // super(name) - Used to name the worker thread, important only for debugging
    public ShakeAppService() {
        super("ShakeAppThread");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        prefs = new ShakeAppPreferences(getApplicationContext());
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        HttpHandler handler = new HttpHandler(intent.getExtras().getString(URL_KEY));
        QuakeJsonParser parser = new QuakeJsonParser(handler.getJsonString());
        List<QuakeObject> quakeList = parser.parseQuakeList();

        int latestDatabaseQuakeId = db.getQuakeCount() > 0 ? db.getLatestQuakeObject().getId() : 0;

        if (db.getQuakeCount() > 0) {
            // Check if there are new quake events
            if (parser.getLatestQuake().getId() != db.getLatestQuakeObject().getId()) {
                insertQuakesIntoDatabase(db, quakeList);
                retainLatestQuakeId(latestDatabaseQuakeId);

                // Notify user of new events
                if (prefs.getNotifications()) {
                    throwNotification(latestDatabaseQuakeId);
                }
            }
        } else {
            insertQuakesIntoDatabase(db, quakeList);
            retainLatestQuakeId(latestDatabaseQuakeId);
        }
    }

    private void insertQuakesIntoDatabase(DatabaseHandler db, List<QuakeObject> quakeList) {
        if (db.clearAllQuakes()) {
            for (QuakeObject quake : quakeList) {
                db.insertQuakeObject(quake);
            }
            serviceDone();
        } else {
            Log.d("SERVICE", "Database was not cleared.");
        }
    }

    private void serviceDone () {
        prefs.setServiceDone(true);
    }

    private void retainLatestQuakeId (int latestId) {
        prefs.setLatestDatabaseId(latestId);
    }

    private void throwNotification (int lastQuakeId) {
        QuakeNotifications notifications = new QuakeNotifications(getApplicationContext());
        notifications.startNotification(lastQuakeId, prefs.getMagnitude());
    }
}
