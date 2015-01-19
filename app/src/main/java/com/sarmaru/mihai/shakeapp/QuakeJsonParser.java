package com.sarmaru.mihai.shakeapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mihai Sarmaru on 27.12.2014.
 */
public class QuakeJsonParser {

    // Private members
    private String _jsonString;

    private static final String TAG_LOCAL = "local";
    private static final String TAG_ID = "id";

    private static final String TAG_LATITUDE = "lat";
    private static final String TAG_LONGITUDE = "lng";

    private static final String TAG_DEPTH = "depth";
    private static final String TAG_MAGNITUDE = "magnitude";

    private static final String TAG_TIME = "time";
    private static final String TAG_REGION = "region";

    // Constructor
    public QuakeJsonParser (String jsonString) {
        this._jsonString = jsonString;
    }

    // Getter and setter
    public String getJsonString() {
        return _jsonString;
    }
    public void setJsonString(String jsonString) {
        this._jsonString = jsonString;
    }

    public List<QuakeObject> parseQuakeList () {
        List<QuakeObject> quakeList = new ArrayList<>();
        JSONArray jsonArray = getJsonArray();

        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    quakeList.add(getQuakeObjectFromJson(jsonArray.getJSONObject(i)));
                }
                return quakeList;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d("JSON", "Failed parsing Quake JSON List");
            e.printStackTrace();
            return null;
        }
    }

    public QuakeObject parseSingleQuake (int quakeId) {
        JSONArray jsonArray = getJsonArray();

        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (jsonArray.getJSONObject(i).getInt(TAG_ID) == quakeId) {
                        return getQuakeObjectFromJson(jsonArray.getJSONObject(i));
                    }
                }
                return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d("JSON", "Failed parsing Single Quake JSON");
            e.printStackTrace();
            return null;
        }
    }

    public QuakeObject getLatestQuake () {
        JSONArray jsonArray = getJsonArray();
        try {
            if (jsonArray != null) {
                return getQuakeObjectFromJson(jsonArray.getJSONObject(0));
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d("JSON", "Failed getting latest quake from JSON");
            e.printStackTrace();
            return null;
        }
    }

    private JSONArray getJsonArray() {
        try {
            JSONObject json = new JSONObject(_jsonString);
            return json.getJSONArray(TAG_LOCAL);
        } catch (Exception e) {
            Log.d("JSON", "Failed parsing Local JSON Array");
            e.printStackTrace();
            return null;
        }
    }

    private QuakeObject getQuakeObjectFromJson (JSONObject json) {
        QuakeObject quake = new QuakeObject();

        try {
            quake.setId(json.getInt(TAG_ID));
            quake.setLatitude(Double.parseDouble(json.getString(TAG_LATITUDE)));
            quake.setLongitude(Double.parseDouble(json.getString(TAG_LONGITUDE)));
            quake.setDepth(json.getInt(TAG_DEPTH));
            quake.setMagnitude(json.getDouble(TAG_MAGNITUDE));
            quake.setTime(json.getLong(TAG_TIME));
            quake.setRegion(json.getString(TAG_REGION));

            return quake;
        } catch (Exception e) {
            Log.d("JSON", "Failed parsing JSON");
            e.printStackTrace();
            return null;
        }
    }
}
