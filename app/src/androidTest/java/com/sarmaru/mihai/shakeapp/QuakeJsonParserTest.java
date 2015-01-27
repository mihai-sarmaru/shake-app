package com.sarmaru.mihai.shakeapp;

import android.test.InstrumentationTestCase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Mihai Sarmaru on 27.12.2014.
 */
public class QuakeJsonParserTest extends InstrumentationTestCase {

    private static final String TEST_STRING = "test";
    private static final int QUAKE_ID = 24340;
    private static final int QUAKE_LIST_SIZE = 5;

    private int id = 24340;
    private double lat = 45.606900;
    private double lng = 26.353400;
    private int depth = 113;
    private double magnitude = 2.4;
    private long time = 1419641199;
    private String region = "Zona seismica Vrancea, judetul Buzau";

    public void testQuakeJsonParserConstructor () {
        String testJsonString = getTestJsonString();

        QuakeJsonParser jsonParser = new QuakeJsonParser(TEST_STRING);
        assertEquals(jsonParser.getJsonString(), TEST_STRING);

        jsonParser.setJsonString(testJsonString);
        assertEquals(jsonParser.getJsonString(), testJsonString);
    }

    public void testSingleQuakeParser () {
        QuakeJsonParser jsonParser = getQuakeJsonParser();
        QuakeObject quake = jsonParser.parseSingleQuake(QUAKE_ID);

        assertEquals(quake.getId(), id);
        assertEquals(quake.getLatitude(), lat);
        assertEquals(quake.getLongitude(), lng);
        assertEquals(quake.getDepth(), depth);
        assertEquals(quake.getMagnitude(), magnitude);
        assertEquals(quake.getTime(), time * 1000);
        assertEquals(quake.getRegion(), region);
    }

    public void testLatestQuakeParser() {
        QuakeJsonParser jsonParser = getQuakeJsonParser();
        QuakeObject quake = jsonParser.getLatestQuake();

        assertEquals(quake.getId(), id);
    }

    public void testQuakeParserList () {
        QuakeJsonParser jsonParser = getQuakeJsonParser();
        List<QuakeObject> quakeList = jsonParser.parseQuakeList();

        assertEquals(quakeList.size(), QUAKE_LIST_SIZE);
    }

    private QuakeJsonParser getQuakeJsonParser() {
        String testJsonString = getTestJsonString();
        return new QuakeJsonParser(testJsonString);
    }

    private String getTestJsonString () {
        try {
            InputStream inputStream = getInstrumentation().getTargetContext().getAssets().open("quakeJsonSample.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            StringBuilder builder = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                builder.append(inputLine);
            }

            reader.close();
            return builder.toString();

        } catch (IOException e) {
            Log.d("TEST", "Getting test JSON String failed");
            e.printStackTrace();
            return null;
        }
    }
}
