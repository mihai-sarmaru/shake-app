package com.sarmaru.mihai.shakeapp;

import android.test.InstrumentationTestCase;

import java.util.List;

/**
 * Created by Mihai Sarmaru on 30.12.2014.
 */
public class DatabaseHandlerTest extends InstrumentationTestCase {

    private int id = 24340;
    private double lat = 45.606900;
    private double lng = 26.353400;
    private int depth = 113;
    private double magnitude = 2.4;
    private long time = 1419641199;
    private String region = "Zona seismica Vrancea, judetul Buzau";

    private int latestQuakeId = 24342;

    public void testDatabaseHandlerInsertQuake () {
        DatabaseHandler db = new DatabaseHandler(getInstrumentation().getTargetContext());
        long insertedId = db.insertQuakeObject(createQuakeObject());
        assertTrue(insertedId != -1);
    }

    public void testDatabaseHandlerQuakeCount () {
        DatabaseHandler db = new DatabaseHandler(getInstrumentation().getTargetContext());
        db.insertQuakeObject(createQuakeObject());
        assertTrue(db.getQuakeCount() != 0);
    }

    public void testDatabaseHandlerGetQuake () {
        DatabaseHandler db = new DatabaseHandler(getInstrumentation().getTargetContext());
        db.insertQuakeObject(createQuakeObject());
        QuakeObject quake = db.getQuakeObject(id);

        assertEquals(id, quake.getId());
        assertEquals(lat, quake.getLatitude());
        assertEquals(lng, quake.getLongitude());
        assertEquals(depth, quake.getDepth());
        assertEquals(magnitude, quake.getMagnitude());
        assertEquals(region, quake.getRegion());

        assertEquals(time * 1000, quake.getTime());
    }

    public void testDatabaseHandlerQuakeList () {
        DatabaseHandler db = new DatabaseHandler(getInstrumentation().getTargetContext());
        db.insertQuakeObject(createQuakeObject());
        List<QuakeObject> quakeList = db.getQuakeList();
        assertTrue(quakeList.size() > 0);
    }

    public void testDatabaseHandlerLatestQuake () {
        DatabaseHandler db = new DatabaseHandler(getInstrumentation().getTargetContext());
        db.insertQuakeObject(new QuakeObject(latestQuakeId, lat, lng, depth, magnitude, time, region));
        QuakeObject quake = db.getLatestQuakeObject();
        assertEquals(quake.getId(), latestQuakeId);
    }

    public void testDatabaseHandlerLatestQuakesList () {
        DatabaseHandler db = new DatabaseHandler(getInstrumentation().getTargetContext());
        List<QuakeObject> quakeList = db.getLatestQuakesList(0);
        assertTrue(quakeList.size() > 0);
    }

    public void testDatabaseHandlerClearAllQuakes () {
        DatabaseHandler db = new DatabaseHandler(getInstrumentation().getTargetContext());
        assertEquals(true, db.clearAllQuakes());
    }

    private QuakeObject createQuakeObject () {
        return new QuakeObject(id, lat, lng, depth, magnitude, time, region);
    }
}
