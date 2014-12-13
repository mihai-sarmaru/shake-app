package com.sarmaru.mihai.shakeapp;

import android.test.InstrumentationTestCase;

/**
 * Created by Mihai Sarmaru on 13.12.2014.
 */
public class QuakeObjectTest extends InstrumentationTestCase {

    private int id = 30316;
    private double lat = 15.6771;
    private double lng = 26.6535;
    private int depth = 80;
    private double magnitude = 2.7;
    private long time = 1418454278;
    private String region = "Zona seismica Vrancea, judetul Vrancea";

    public void testQuakeObjectConstructor() {
        QuakeObject quake = new QuakeObject(id, lat, lng, depth, magnitude, time, region);

        assertEquals(id, quake.getId());
        assertEquals(lat, quake.getLatitude());
        assertEquals(lng, quake.getLongitude());
        assertEquals(depth, quake.getDepth());
        assertEquals(magnitude, quake.getMagnitude());
        assertEquals(region, quake.getRegion());

        assertEquals(time * 1000, quake.getTime());
    }

    public void testQuakeObjectSetters() {
        QuakeObject quake = new QuakeObject();

        quake.setId(id);
        quake.setLatitude(lat);
        quake.setLongitude(lng);
        quake.setDepth(depth);
        quake.setMagnitude(magnitude);
        quake.setTime(time);
        quake.setRegion(region);

        assertEquals(id, quake.getId());
        assertEquals(lat, quake.getLatitude());
        assertEquals(lng, quake.getLongitude());
        assertEquals(depth, quake.getDepth());
        assertEquals(magnitude, quake.getMagnitude());
        assertEquals(region, quake.getRegion());

        assertEquals(time * 1000, quake.getTime());
    }
}
