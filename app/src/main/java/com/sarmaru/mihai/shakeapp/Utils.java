package com.sarmaru.mihai.shakeapp;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mihai Sarmaru on 13.12.2014.
 */
public class Utils {

    public static String formatDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public static String formatTime(long time) {
        Date dateTime = new Date(time);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(dateTime);
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String getServerUrl (Context context) {
        byte[] data = Base64.decode(getUrlFromAsset(context), Base64.DEFAULT);
        try {
            return new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getUrlFromAsset (Context context) {
        try {
            InputStream inputStream = context.getAssets().open("url.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            StringBuilder builder = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                builder.append(inputLine);
            }

            reader.close();
            return builder.toString();

        } catch (IOException e) {
            Log.d("UTILS", "Getting URL from assets failed.");
            e.printStackTrace();
            return null;
        }
    }
}
