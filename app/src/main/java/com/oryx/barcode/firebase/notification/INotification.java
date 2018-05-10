package com.oryx.barcode.firebase.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.google.firebase.messaging.FirebaseMessagingService;

public class INotification {
    public static NotificationManager notificationManager;

    public static void initChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "firebase", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("firebase msgs");

            if (INotification.notificationManager != null) {
                INotification.notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public static void initNotificationManager(FirebaseMessagingService firebaseMessagingService) {
        if (INotification.notificationManager == null) {
            INotification.notificationManager = (NotificationManager) firebaseMessagingService.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }
}
