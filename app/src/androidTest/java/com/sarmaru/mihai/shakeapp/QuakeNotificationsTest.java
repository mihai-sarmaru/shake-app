package com.sarmaru.mihai.shakeapp;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Mihai Sarmaru on 14.03.2015.
 */
public class QuakeNotificationsTest extends InstrumentationTestCase {

    private List<QuakeObject> quakeList;

    private int noMagnitude = 0;
    private int minMagnitude = 3;
    private int maxMagnitude = 4;

    private int multipleLastQuakeId = 0;
    private int singleLastQuakeId = 0;
    private int noLastQuakeId = 0;

    public void testQuakeNotificationsMultipleEvents () {
        QuakeNotificationsSubclass notificationClass = getQuakeNotificationsSubclass(multipleLastQuakeId, noMagnitude);

        assertNotNull(notificationClass.title);
        assertNotNull(notificationClass.content);
        assertNotNull(notificationClass.notificationIntent);
        assertTrue(notificationClass.title.equals("4 New Earthquakes"));

        notificationClass = getQuakeNotificationsSubclass(multipleLastQuakeId, minMagnitude);

        assertNotNull(notificationClass.title);
        assertNotNull(notificationClass.content);
        assertNotNull(notificationClass.notificationIntent);
        assertTrue(notificationClass.title.equals("2 New Earthquakes"));

        notificationClass = getQuakeNotificationsSubclass(multipleLastQuakeId, maxMagnitude);

        assertNull(notificationClass.title);
        assertNull(notificationClass.content);
        assertNull(notificationClass.notificationIntent);
    }

    public void testQuakeNotificationsSingleEvent () {
        QuakeNotificationsSubclass notificationClass = getQuakeNotificationsSubclass(singleLastQuakeId, noMagnitude);

        assertNotNull(notificationClass.title);
        assertNotNull(notificationClass.content);
        assertNotNull(notificationClass.notificationIntent);
        assertTrue(notificationClass.title.contains("2.4"));
        assertTrue(notificationClass.title.contains("Zona seismica Vrancea"));
        assertTrue(notificationClass.content.contains("02:46"));
    }

    public void testQuakeNotificationsNoEvents () {
        QuakeNotificationsSubclass notificationClass = getQuakeNotificationsSubclass(noLastQuakeId, noMagnitude);

        assertNull(notificationClass.title);
        assertNull(notificationClass.content);
        assertNull(notificationClass.notificationIntent);
    }

    private QuakeNotificationsSubclass getQuakeNotificationsSubclass(int lastQuakeId, int magnitude) {
        QuakeNotificationsSubclass notificationClass = new QuakeNotificationsSubclass(getInstrumentation().getTargetContext());
        List<QuakeObject> notificationQuakeList = notificationClass.getLatestQuakeObjects(lastQuakeId);
        notificationQuakeList = notificationClass.parseMagnitude(notificationQuakeList, magnitude);

        if (notificationQuakeList.size() > 0) {
            notificationClass.setupNotificationContent(notificationQuakeList);
        }
        return notificationClass;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        QuakeJsonParser parser = getQuakeJsonParser();
        quakeList = parser.parseQuakeList();

        DatabaseHandler db = new DatabaseHandler(getInstrumentation().getTargetContext());
        if (db.getQuakeCount() > 0) {
            db.clearAllQuakes();
            insertQuakeList(db);
        } else {
            insertQuakeList(db);
        }

        quakeList = db.getQuakeList();
        singleLastQuakeId= quakeList.get(1).getId();
        multipleLastQuakeId= quakeList.get(4).getId();
        noLastQuakeId= quakeList.get(0).getId();
    }

    private void insertQuakeList(DatabaseHandler db) {
        for (QuakeObject quake : quakeList) {
            db.insertQuakeObject(quake);
        }
    }

    private QuakeJsonParser getQuakeJsonParser() {
        String testJsonString = getTestJsonString();
        return new QuakeJsonParser(testJsonString);
    }

    private String getTestJsonString () {
        try {
            InputStream inputStream = getInstrumentation().getTargetContext().getAssets().open("quakeJsonSample.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            StringBuilder builder = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                builder.append(inputLine);
            }

            reader.close();
            return builder.toString();

        } catch (IOException e) {
            Log.d("TEST", "Getting test JSON String failed");
            e.printStackTrace();
            return null;
        }
    }

    private class QuakeNotificationsSubclass extends QuakeNotifications {
        public QuakeNotificationsSubclass(Context mContext) {
            super(mContext);
        }
    }
}
