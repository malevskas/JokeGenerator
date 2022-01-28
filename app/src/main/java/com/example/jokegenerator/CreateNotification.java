package com.example.jokegenerator;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

public class CreateNotification {

    private static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotifyManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String ACTION_UPDATE_NOTIFICATION = "com.example.jokegenerator.ACTION_UPDATE_NOTIFICATION";
    Context context;

    CreateNotification(Context context) { this.context = context; }

    public void createNotificationChannel(String joke) {
        mNotifyManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.MAGENTA);
        mNotifyManager.createNotificationChannel(notificationChannel);
        createNotification(joke);
    }

    public void createNotification(String joke) {
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setContentTitle("Want to hear something funny?")
                .setSmallIcon(R.drawable.ic_baseline_insert_emoticon_24)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(joke))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .addAction(R.drawable.ic_baseline_autorenew_24, "Give me another", updatePendingIntent);
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

}
