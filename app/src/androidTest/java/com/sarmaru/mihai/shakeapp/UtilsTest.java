package com.sarmaru.mihai.shakeapp;

import android.test.InstrumentationTestCase;

/**
 * Created by Mihai Sarmaru on 13.12.2014.
 */
public class UtilsTest extends InstrumentationTestCase {

    private long millis = 1418454278;

    public void testFormatDate () {
        String formattedDate = Utils.formatDate(millis * 1000);
        String expectedDate = "13.12.2014";
        assertEquals(formattedDate, expectedDate);
    }

    public void testFormatTime () {
        String formattedTime = Utils.formatTime(millis * 1000);
        String expectedTime = "09:04:38";
        assertEquals(formattedTime, expectedTime);
    }

    public void testServiceNotRunning () {
        assertEquals(false, Utils.isServiceRunning(getInstrumentation().getTargetContext(), ShakeAppService.class));
    }
}
