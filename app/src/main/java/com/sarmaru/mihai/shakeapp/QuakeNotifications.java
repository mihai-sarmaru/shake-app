package com.sarmaru.mihai.shakeapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mihai Sarmaru on 13.03.2015.
 */
public class QuakeNotifications {

    private Context context;
    protected String title, content;
    protected Intent notificationIntent;

    private static final String QUAKEID = "QUAKEID";

    public QuakeNotifications (Context mContext) {
        this.context = mContext;
    }

    public void startNotification(int lastQuakeId, int minMagnitude) {
        List<QuakeObject> quakeList = getLatestQuakeObjects(lastQuakeId);
        quakeList = parseMagnitude(quakeList, minMagnitude);

        if (quakeList.size() > 0) {
            setupNotificationContent(quakeList);
            launchNotification(notificationIntent, title, content);
        }
    }

    protected List<QuakeObject> parseMagnitude(List<QuakeObject> quakeList, int minMagnitude) {
        List<QuakeObject> parsedQuakeList = new ArrayList<>();

        if (quakeList.size() > 0) {
            for (QuakeObject quake : quakeList) {
                if (quake.getMagnitude() >= minMagnitude) {
                    parsedQuakeList.add(quake);
                }
            }
        }
        return parsedQuakeList;
    }

    protected void setupNotificationContent (List<QuakeObject> quakeList) {
        if (quakeList.size() == 1) {
            notificationIntent = new Intent(context, DetailActivity.class);
            notificationIntent.putExtra(QUAKEID, quakeList.get(0).getId());

            title = String.valueOf(quakeList.get(0).getMagnitude()) + " - " +
                    quakeList.get(0).getRegion();
            content = Utils.formatDate(quakeList.get(0).getTime()) + " - " +
                    Utils.formatTimeShort(quakeList.get(0).getTime());
        } else {
            notificationIntent = new Intent(context, MainActivity.class);

            title = String.valueOf(quakeList.size()) + context.getString(R.string.notification_new_quakes);
            content = "";
            for (QuakeObject quake : quakeList) {
                content = content + String.valueOf(quake.getMagnitude()) + "   ";
            }
        }
    }

    protected List<QuakeObject> getLatestQuakeObjects(int lastQuakeId) {
        DatabaseHandler db = new DatabaseHandler(context);
        return db.getLatestQuakesList(lastQuakeId);
    }

    private void launchNotification(Intent intent, String title, String content) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(content);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(100, notification.build());
    }
}
