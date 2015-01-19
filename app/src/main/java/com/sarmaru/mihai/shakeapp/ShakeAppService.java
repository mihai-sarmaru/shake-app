package com.sarmaru.mihai.shakeapp;

import android.app.IntentService;
import android.content.Intent;

import java.util.List;

/**
 * Created by Mihai Sarmaru on 19.01.2015.
 */
public class ShakeAppService extends IntentService {

    // super(name) - Used to name the worker thread, important only for debugging
    public ShakeAppService() {
        super("ShakeAppThread");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        HttpHandler handler = new HttpHandler("LINK GOES HERE");
        QuakeJsonParser parser = new QuakeJsonParser(handler.getJsonString());
        List<QuakeObject> quakeList = parser.parseQuakeList();

        // TODO Check if there are new events

        insertQuakesIntoDatabase(quakeList);
    }

    private void insertQuakesIntoDatabase(List<QuakeObject> quakeList) {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        db.clearAllQuakes();
        for (QuakeObject quake : quakeList) {
            db.insertQuakeObject(quake);
        }
    }
}
