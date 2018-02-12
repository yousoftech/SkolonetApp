package com.example.admin.skolonetapp.Adapter;

import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.widget.Toast;
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
import com.example.admin.skolonetapp.Activity.SalesMan;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by pc01 on 21/12/17.
>>>>>>> origin/Branch
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
        String partyName = intent.getStringExtra( "partyName" );
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder( context );
        mBuilder.setSmallIcon( R.drawable.logo );
        mBuilder.setContentTitle( "Reminder Alert" );
        mBuilder.setVibrate( new long[]{1000, 1000, 1000, 1000, 1000} );
        mBuilder.setLights( Color.RED, 3000, 3000 );
        Uri alarmSound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
        mBuilder.setSound( alarmSound );
        mBuilder.setContentText(partyName);


        // Moves events into the big view

        Intent resultIntent = new Intent( context, SalesMan.class );
        TaskStackBuilder stackBuilder = TaskStackBuilder.create( context );
        stackBuilder.addParentStack( SalesMan.class );
        stackBuilder.addNextIntent( resultIntent );
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent( 0, PendingIntent.FLAG_UPDATE_CURRENT );
        mBuilder.setContentIntent( resultPendingIntent );
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify( 1, mBuilder.build() );
        //PendingIntent.getBroadcast(getApplicationContext(),1,resultIntent,0);

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
