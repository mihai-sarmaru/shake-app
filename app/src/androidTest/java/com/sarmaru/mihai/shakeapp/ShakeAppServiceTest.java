package com.sarmaru.mihai.shakeapp;

import android.content.Intent;
import android.test.InstrumentationTestCase;

import java.io.IOException;

/**
 * Created by Mihai Sarmaru on 20.01.2015.
 */
public class ShakeAppServiceTest extends InstrumentationTestCase {

    public void testShakeAppService () throws IOException {
        ShakeAppPreferences prefs = new ShakeAppPreferences(getInstrumentation().getTargetContext());
        assertEquals(false, prefs.isServiceDone());

        // Disable notifications
        prefs.setNotifications(false);

        DatabaseHandler db = new DatabaseHandler(getInstrumentation().getTargetContext());
        int latestId = db.getQuakeCount() > 0 ? db.getLatestQuakeObject().getId() : 0;

        Intent serviceIntent = new Intent(getInstrumentation().getTargetContext(), ShakeAppService.class);
        serviceIntent.putExtra("url", Utils.getServerUrl(getInstrumentation().getTargetContext()));
        getInstrumentation().getTargetContext().startService(serviceIntent);

        assertEquals(true, Utils.isServiceRunning(getInstrumentation().getTargetContext(), ShakeAppService.class));

        while (Utils.isServiceRunning(getInstrumentation().getTargetContext(), ShakeAppService.class)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertEquals(false, Utils.isServiceRunning(getInstrumentation().getTargetContext(), ShakeAppService.class));
        assertEquals(true, prefs.isServiceDone());

        // Enable back notifications
        prefs.setNotifications(true);

        assertTrue(db.getQuakeCount() > 0);
        assertTrue(db.getLatestQuakeObject().getId() > latestId);
    }
}
