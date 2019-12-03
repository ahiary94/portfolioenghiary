package com.example.abeer.mysecretportfolio;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class TestingApp extends ContextWrapper {

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_1_NAME = "channel1";
    private NotificationManager manager;

    public TestingApp(Context base) {
        super(base);
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    CHANNEL_1_NAME ,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");
            channel1.enableLights(true);
            channel1.enableVibration(true);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

            getManeger().createNotificationChannel(channel1);
        }
    }

    public NotificationManager getManeger() {
        if (manager == null)
            manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification (String title, String body){
        return new Notification.Builder(getApplicationContext(), CHANNEL_1_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setAutoCancel(false);


    }
}
