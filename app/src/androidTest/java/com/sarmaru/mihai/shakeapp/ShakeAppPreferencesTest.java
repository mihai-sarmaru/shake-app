package com.sarmaru.mihai.shakeapp;

import android.test.InstrumentationTestCase;

/**
 * Created by Mihai Sarmaru on 27.01.2015.
 */
public class ShakeAppPreferencesTest extends InstrumentationTestCase {

    private int id = 30316;

    public void testServicePreferences () {
        ShakeAppPreferences prefs = new ShakeAppPreferences(getInstrumentation().getTargetContext());

        prefs.setServiceDone(true);
        assertEquals(true, prefs.isServiceDone());

        // Reset preference to default
        prefs.setServiceDone(false);
        assertEquals(false, prefs.isServiceDone());
    }

    public void testLatestDatabaseIdPreferences () {
        ShakeAppPreferences prefs = new ShakeAppPreferences(getInstrumentation().getTargetContext());

        prefs.setLatestDatabaseId(id);
        assertEquals(id, prefs.getLatestDatabaseId());

        // Reset preference to default
        prefs.setLatestDatabaseId(0);
        assertEquals(0, prefs.getLatestDatabaseId());
    }
}
