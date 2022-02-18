package com.example.frb.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.frb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.example.frb.activities.NotificationClass.NOTIFICATION_ID;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
//    private static final String NOTIFICATION_ID = "Notification_id";
    NotificationManagerCompat managerCompat;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("My Messaging Service", "The token refreshed:" + s);
        if(mAuth.getCurrentUser() != null) {
            String phoneNo = mAuth.getCurrentUser().getPhoneNumber();
            FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app/").getReference().child("MessageIds").child(phoneNo).setValue(s);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("FirebaseMessageReceived", "In onMessageReceived");
//        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null){
            Log.d("FirebaseMessage Title", remoteMessage.getNotification().getTitle());
            Log.d("FirebaseMessage Body", remoteMessage.getNotification().getBody());


            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            managerCompat = NotificationManagerCompat.from(this);
            Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .build();
            NotificationManager manager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(1002, notification);
        }
//        Notification notification = new Notification.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
//                .setContentTitle()
//                .setContentText()
//                .setSmallIcon()
//                .setContentIntent()
//                .build();
    }

    private void showNotification(String title, String body) {

    }
}