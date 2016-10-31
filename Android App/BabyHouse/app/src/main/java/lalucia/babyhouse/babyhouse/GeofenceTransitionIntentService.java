/*
GeofenceTransitionIntentService.java
Sets up a geofence around a location
Lecturer : Rajesh Chanderman
WIL Assessment
Date Updated : 10/24/16
 */
package lalucia.babyhouse.babyhouse;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceTransitionIntentService extends IntentService
{
    public GeofenceTransitionIntentService()
    {
        super("GeofenceTransitionIS");
    }
    //*********************************************************
    @Override
    protected void onHandleIntent(Intent intent)
    {
        //Detects if the user is inside or outsides the geofence
        String transitionType;
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if(geofencingEvent.hasError()){
            Log.e("OnHandleIntent","" + geofencingEvent.getErrorCode());
        }
        else
        {
            int transition = geofencingEvent.getGeofenceTransition();

            if(transition == Geofence.GEOFENCE_TRANSITION_ENTER)
            {
                transitionType = "Welcome to Baby House La Lucia";
                //sendNotification(transitionType);
            }
            else if(transition == Geofence.GEOFENCE_TRANSITION_EXIT)
            {
                transitionType = "You Have Exited";
                //sendNotification(transitionType);
            }
        }
    }
    //******************************************************************************************
    private void sendNotification(String notificationDescription)
    {
        //Creates notification containing whether the user as left or inside the geofence
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this);
        builder.setColor(Color.BLUE)
                .setContentTitle("BabyHouse")
                .setContentText(notificationDescription)
                .setAutoCancel(true)
                .setVibrate(new long[] { 200, 200, 600, 600, 600, 200, 200,})
                .setSmallIcon(R.drawable.babyhouseicon);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }
}
