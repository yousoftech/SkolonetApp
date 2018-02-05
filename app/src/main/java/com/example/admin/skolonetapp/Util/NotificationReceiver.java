package com.example.admin.skolonetapp.Util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.skolonetapp.Activity.SalesMan;
import com.example.admin.skolonetapp.R;

/**
 * Created by DELL on 2/4/2018.
 */

public class NotificationReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Context mcontext = null;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder( context );
        mBuilder.setSmallIcon( R.drawable.logo );
        mBuilder.setContentTitle( "Reminder Alert For"  + " " );
        mBuilder.setVibrate( new long[]{1000, 1000, 1000, 1000, 1000} );
        mBuilder.setLights( Color.RED, 3000, 3000 );
        Uri alarmSound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
        mBuilder.setSound( alarmSound );
     //   mBuilder.setContentText( iPartyTypeName + " " + reminderDate1 );


        // Moves events into the big view

        Intent resultIntent = new Intent( context, SalesMan.class );
        TaskStackBuilder stackBuilder = TaskStackBuilder.create( context );
        stackBuilder.addParentStack( SalesMan.class );
        stackBuilder.addNextIntent( resultIntent );
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent( 0, PendingIntent.FLAG_UPDATE_CURRENT );
        mBuilder.setContentIntent( resultPendingIntent );
        NotificationManager mNotificationManager = (NotificationManager)mcontext.getSystemService(context.NOTIFICATION_SERVICE );
        mNotificationManager.notify( 1, mBuilder.build() );
    }
}
