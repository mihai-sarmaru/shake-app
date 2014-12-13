package com.sarmaru.mihai.shakeapp;

import android.test.InstrumentationTestCase;

/**
 * Created by Mihai Sarmaru on 12.12.2014.
 */
public class HttpHandlerTest extends InstrumentationTestCase {

    public void testHttpHandler() {
        String url = "http://ip.jsontest.com/?callback=showMyIP";
        String result = "showMyIP({\"ip\": \"5.13.182.219\"});";

        HttpHandler handler = new HttpHandler();
        String json = handler.getJsonString(url);
        assertEquals(json, result);
    }

}
