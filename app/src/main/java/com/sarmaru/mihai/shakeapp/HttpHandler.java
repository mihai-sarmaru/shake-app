package com.sarmaru.mihai.shakeapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Mihai Sarmaru on 12.12.2014.
 */
public class HttpHandler {

    public HttpHandler() {}

    public String getJsonString(String url) {
        try {
            URL uri = new URL(url);
            URLConnection urlConnection = uri.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String inputLine;
            StringBuilder builder = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                builder.append(inputLine);
            }

            reader.close();
            return builder.toString();

        } catch (Exception e) {
            Log.d("HTTP", "HTTP Handler problems");
            e.printStackTrace();
            return null;
        }
    }
}
