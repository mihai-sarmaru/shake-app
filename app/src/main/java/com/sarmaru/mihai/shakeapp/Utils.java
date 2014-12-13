package com.sarmaru.mihai.shakeapp;

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
}
