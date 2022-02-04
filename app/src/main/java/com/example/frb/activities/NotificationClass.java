package com.example.frb.activities;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationClass extends Application {
    public static final String NOTIFICATION_ID = "Notification_id";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();

    }

    private void createNotification() {
        //Check Android Version
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_ID,
                    "demo_notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("DemoNotification");

            NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            //now Run the App
        }
    }
}
