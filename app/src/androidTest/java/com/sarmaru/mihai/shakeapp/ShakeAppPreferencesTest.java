package com.sarmaru.mihai.shakeapp;

import android.test.InstrumentationTestCase;

/**
 * Created by Mihai Sarmaru on 27.01.2015.
 */
public class ShakeAppPreferencesTest extends InstrumentationTestCase {

    public void testServicePreferences () {
        ShakeAppPreferences prefs = new ShakeAppPreferences(getInstrumentation().getTargetContext());
        assertEquals(false, prefs.isServiceDone());

        prefs.setServiceDone(true);
        assertEquals(true, prefs.isServiceDone());

        // Reset preference to default
        prefs.setServiceDone(false);
        assertEquals(false, prefs.isServiceDone());
    }
}
