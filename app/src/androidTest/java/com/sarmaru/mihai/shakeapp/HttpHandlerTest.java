package com.sarmaru.mihai.shakeapp;

import android.test.InstrumentationTestCase;

/**
 * Created by Mihai Sarmaru on 12.12.2014.
 */
public class HttpHandlerTest extends InstrumentationTestCase {

    private static final String URL = "http://ip.jsontest.com/?callback=showMyIP";
    private static final String RESULT = "showMyIP";
    private static final String TEST_URL = "test";

    public void testHttpHandlerConstructor () {
        HttpHandler handler = new HttpHandler(TEST_URL);
        assertEquals(TEST_URL, handler.getUrl());

        handler.setUrl(URL);
        assertEquals(URL, handler.getUrl());
    }

    public void testHttpHandlerGetJson() {
        HttpHandler handler = new HttpHandler(URL);
        String json = handler.getJsonString().substring(0, 8);
        assertEquals(json, RESULT);
    }

}
