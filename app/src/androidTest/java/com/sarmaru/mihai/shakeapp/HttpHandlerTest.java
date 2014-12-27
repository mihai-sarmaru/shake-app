package com.sarmaru.mihai.shakeapp;

import android.test.InstrumentationTestCase;

/**
 * Created by Mihai Sarmaru on 12.12.2014.
 */
public class HttpHandlerTest extends InstrumentationTestCase {

    public void testHttpHandler() {
        String url = "http://ip.jsontest.com/?callback=showMyIP";
        String result = "showMyIP";

        HttpHandler handler = new HttpHandler();
        String json = handler.getJsonString(url).substring(0, 8);
        assertEquals(json, result);
    }

}
