package com.example.romanchuk.appisode;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

//import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by romanchuk on 23.02.2017.
 */
//
//public class MyGcmListenerService extends GcmListenerService {
//    private static final String TAG = "GCM";
//
//    @Override
//    public void onMessageReceived(String from, Bundle data) {
//        Bundle notification = data.getBundle("notification");
//
//        String message = data.getString("message","defValue");
//        String title = data.getString("title","defValue");
//        String icon = "";
//        if (notification != null) {
//            title = notification.getString("title");
//            message = notification.getString("body");
//            icon = notification.getString("icon");
//        }
//        else {
//
//        }
//        Log.d(TAG, "From: " + from);
//        Log.d(TAG, "Message: " + message);
//        sendNotification(message, icon, title);
//    }
//
//    private void sendNotification(String message, String icon, String title){
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//        bigText.bigText(message);
//        bigText.setBigContentTitle(getString(R.string.app_name));
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        Notification notification = null;
//        notification = builder.setSmallIcon(R.drawable.small_icon)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
//                .setColor(getResources().getColor(R.color.color_accent))
//                .setContentTitle(title)
//                .setContentIntent(pendingIntent)
//                .setContentText(message)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setAutoCancel(true)
//                .setStyle(bigText)
//                .setSound(defaultSoundUri)
//                .setPriority(Notification.PRIORITY_HIGH).build();
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, notification);
//    }
//}