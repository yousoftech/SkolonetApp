package com.example.admin.skolonetapp.Adapter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.skolonetapp.*;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by pc01 on 21/12/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    int id;
    int month;
    int date;
    int hour;
    int minute;
    String number = "9998038839";

    //public static final String ALARM_WENT_OFF_ACTION = RingTonePlayingService.class.getName()
      //      + ".ALARM_WENT_OFF";

    public static final String KEY_ALARM_ID = "alarm_id";

    public static final String KEY_ALARM_MONTH = "alarm_month";

    public static final String KEY_ALARM_DATE = "alarm_date";

    public static final String KEY_ALARM_HOUR = "alarm_hour";

    public static final String KEY_ALARM_MINUTE = "alarm_minute";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
        Log.d("Alarm", "Done!!!");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("Notifications Title");
        builder.setContentText("Your notification content here.");
        builder.setSubText("Tap to view the website.");
        builder.setCategory(Notification.CATEGORY_CALL);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("Kush Shah"));
        //builder.addAction(R.drawable.ic_phone_black_24dp, "Call", pendingIntent);
        //builder.addAction(R.drawable.ic_close_white, "Cancel", null);
        builder.setSound(Settings.System.DEFAULT_ALARM_ALERT_URI);
        builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
        builder.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    public void readAlarm(Bundle extras) {
        id = extras.getInt(KEY_ALARM_ID);
        month = extras.getInt(KEY_ALARM_MONTH);
        date = extras.getInt(KEY_ALARM_DATE);
        hour = extras.getInt(KEY_ALARM_HOUR);
        minute = extras.getInt(KEY_ALARM_MINUTE);
        return;
    }
}
