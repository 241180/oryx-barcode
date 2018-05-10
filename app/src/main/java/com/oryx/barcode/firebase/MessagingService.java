package com.oryx.barcode.firebase;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.oryx.barcode.R;
import com.oryx.barcode.activity.main.MainActivity;

import java.util.Map;

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationContext.initNotificationManager(this);
        NotificationContext.initChannels();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        RemoteMessage.Notification notification = remoteMessage.getNotification();

        Map<String, String> data = remoteMessage.getData();
        ShowNotification(remoteMessage.hashCode(), notification, data);

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
    }

    private void ShowNotification(int id, RemoteMessage.Notification notification, Map<String, String> data) {

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/raw/notification");

        NotificationCompat.Builder notificationBuilder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder = new NotificationCompat.Builder(this, "default");
        } else {
            notificationBuilder = new NotificationCompat.Builder(this);
        }

        if (notificationBuilder != null) {
            notificationBuilder.setContentTitle(notification.getTitle())
                    .setContentText(notification.getBody())
                    .setAutoCancel(true)
                    .setSound(sound)
                    .setContentIntent(pendingIntent)
                    .setContentInfo("ANY")
                    .setLargeIcon(icon)
                    .setColor(Color.BLUE)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setLights(Color.YELLOW, 1000, 300);
        }

        if (NotificationContext.notificationManager != null) {
            NotificationContext.notificationManager.notify(id, notificationBuilder.build());
        }
    }
}