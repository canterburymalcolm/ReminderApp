package com.example.myfirstapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static com.example.myfirstapp.MainActivity.REMINDER_INDEX;
import static com.example.myfirstapp.MainActivity.REMINDER_NOTIFICATION;
import static com.example.myfirstapp.MainActivity.REMINDER_UPDATE;

/**
 * Created by malcolm on 5/10/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Alarm Went Off");
        Intent myIntent = intent;
        Gson gson = new GsonBuilder().create();
        Reminder reminder = gson.fromJson(intent.getStringExtra(REMINDER_UPDATE), Reminder.class);
        //builder to create notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        //characteristics of notification
        mBuilder.setSmallIcon(R.drawable.reminder_logo_192)
                .setContentTitle(reminder.getTitle())
                .setContentText(reminder.getDescription())
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);
        //intent to take user to buildReminderActivity
        Intent resultIntent = new Intent(context, BuildReminderActivity.class);
        //pasing reminder in json form to intent
        resultIntent.putExtra(REMINDER_UPDATE, gson.toJson(reminder));
        resultIntent.putExtra(REMINDER_NOTIFICATION, myIntent.getIntExtra(REMINDER_NOTIFICATION, -3));
        //resultIntent.putExtra(REMINDER_NOTIFICATION, index);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        //add intent to top of stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
    }


}
