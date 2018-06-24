package com.akin.mapu;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import java.util.List;

/**
 * Created by Mungfali on 14-06-2018.
 */

public class GeofenceTransitionsIntentService extends IntentService {

    private final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";
    private final int NOTIFICATION_ID = 1;
    public GeofenceTransitionsIntentService(){
        super("");
    }
    public GeofenceTransitionsIntentService(String name) {
        super(name);
    }
    private NotificationManager manager;
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Toast.makeText(this, geofencingEvent.getErrorCode(), Toast.LENGTH_SHORT).show();
            return;
        }
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            Toast.makeText(getApplicationContext(), "Geofence Entered", Toast.LENGTH_SHORT).show();
             // vibrate();
            sendNotification();
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            setNotification();
        } else {
            // Log the error.
            Toast.makeText(getApplicationContext(), "Geofence Transition Mismatch : " + geofenceTransition, Toast.LENGTH_SHORT).show();
        }
    }
 /*   public void getCurrentTime(View view) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = "Current Time : " + mdformat.format(calendar.getTime()); */
 private void setNotification() {
     Intent i = new Intent(this, MapGeofence.class);
     i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     PendingIntent pi = PendingIntent.getActivity(this,
             0 /* Request code */,
             i,
             PendingIntent.FLAG_ONE_SHOT);

     Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
     long[] pattern = {500,500,500,500,500,500,500,500,500};
     NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
             getString(R.string.default_notification_channel_id))
             .setSmallIcon(R.mipmap.ic_launcher)
             .setContentTitle("Map")
             .setContentText("About to reach")
             .setAutoCancel(true)
             .setSound(sound)

     .setVibrate(pattern)
             .setContentIntent(pi);

     NotificationManager manager =
             (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

     manager.notify(0, builder.build());
 }

//    private void setNotification() {
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher_notify)
//                        .setContentTitle("GPS")
//                        .setContentText("You are near your destination");
//
//
//        Intent notificationIntent = new Intent(this, MapGeofence.class);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        builder.setSound(uri);
//        builder.setContentIntent(contentIntent);
//        builder.setAutoCancel(true);
//        builder.setLights(Color.BLUE, 500, 500);
//        long[] pattern = {500,500,500,500,500,500,500,500,500};
//        builder.setVibrate(pattern);
//        builder.setStyle(new NotificationCompat.InboxStyle());
//// Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(1, builder.build());
//    }
  /*  private void setNotification() {
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder notification1 = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Map")
                .setContentText("You are near your destination")
                .setLights(6,2000,1000)
               .setSound(uri)
                .setSmallIcon(R.mipmap.ic_launcher_notify)
                .setChannelId(NOTIFICATION_CHANNEL_ID);
        manager.notify(NOTIFICATION_ID, notification1.build());

    } */

    private void  sendNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "Notification")
                .setSmallIcon(R.mipmap.ic_launcher_notify)
                .setContentTitle("REMiNDER")
                .setContentText("You are about to reach your destination")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationId is a unique int for each notification that you must define
        mNotificationManager.notify(1111, mBuilder.build());
    }

}

