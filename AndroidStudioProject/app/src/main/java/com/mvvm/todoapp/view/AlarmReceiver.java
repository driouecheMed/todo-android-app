package com.mvvm.todoapp.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.mvvm.todoapp.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra(AddEditNoteActivity.EXTRA_NOTIFICATION_ID, 0);
        String titleTask = intent.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);

        //When notification is tapped, call MainActivity
        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Prepare notification
        Notification.Builder builder = new Notification.Builder(context, "channelId");
        builder.setSmallIcon(R.drawable.ic_info_24dp)
                .setContentTitle("Reminder").setContentText(titleTask)
                .setWhen(System.currentTimeMillis()).setAutoCancel(true)
                .setContentIntent(contentIntent);

        // Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        //Notify
        notificationManager.notify(notificationId, builder.build());
    }

}
