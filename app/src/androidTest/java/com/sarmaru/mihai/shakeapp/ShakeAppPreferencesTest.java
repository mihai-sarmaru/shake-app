package com.sarmaru.mihai.shakeapp;

import android.test.InstrumentationTestCase;

/**
 * Created by Mihai Sarmaru on 27.01.2015.
 */
public class ShakeAppPreferencesTest extends InstrumentationTestCase {

    private int id = 30316;
    private int magnitude = 3;

    public void testServicePreferences () {
        ShakeAppPreferences prefs = new ShakeAppPreferences(getInstrumentation().getTargetContext());

        prefs.setServiceDone(true);
        assertTrue(prefs.isServiceDone());

        // Reset preference to default
        prefs.setServiceDone(false);
        assertFalse(prefs.isServiceDone());
    }

    public void testNotificationsPreferences () {
        ShakeAppPreferences prefs = new ShakeAppPreferences(getInstrumentation().getTargetContext());

        prefs.setNotifications(false);
        assertFalse(prefs.getNotifications());

        // Reset preference to default
        prefs.setNotifications(true);
        assertTrue(prefs.getNotifications());
    }

    public void testLatestDatabaseIdPreferences () {
        ShakeAppPreferences prefs = new ShakeAppPreferences(getInstrumentation().getTargetContext());

        prefs.setLatestDatabaseId(id);
        assertEquals(id, prefs.getLatestDatabaseId());

        // Reset preference to default
        prefs.setLatestDatabaseId(0);
        assertEquals(0, prefs.getLatestDatabaseId());
    }

    public void testNotificationMagnitudePreferences () {
        ShakeAppPreferences prefs = new ShakeAppPreferences(getInstrumentation().getTargetContext());

        prefs.setMagnitude(magnitude);
        assertEquals(magnitude, prefs.getMagnitude());

        // Reset preference to default
        prefs.setMagnitude(0);
        assertEquals(0, prefs.getMagnitude());
    }
}
