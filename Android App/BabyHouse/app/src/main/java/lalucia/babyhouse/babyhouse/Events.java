/*
Events.java
Displays event details to the user from MYSQL Database
Lecturer : Rajesh Chanderman
WIL Assessment
Date Updated : 10/24/16
 */
package lalucia.babyhouse.babyhouse;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Events extends AppCompatActivity {

    private ListView eventlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() !=null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        eventlist = (ListView) findViewById(R.id.lstEventsList);
        new RetrieveEventsListData().execute();
    }
    //*********************************************************
    public class RetrieveEventsListData extends AsyncTask<String,Void,String>
    {
        ArrayList objListOfEvents = new ArrayList<>();
        @Override
        protected String doInBackground(String... params)
        {
            String line ;
            String entireLine = "";
            JSONObject jsonObject;
            JSONArray jsonArray;
            HttpURLConnection urlConnection ;
            String eventListData = "";

            URL url;
            try {
                url = new URL("http://www.babyhouse.dx.am/eventandroidbabyhouse.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                //Retrieve events list from php Script
                BufferedReader objread = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = objread.readLine()) != null) {
                    entireLine += line;
                }

                //Get json elements
                jsonObject = new JSONObject(entireLine);
                jsonArray = jsonObject.optJSONArray("eventslist");

                //Break down each list item into a group and save it into an array list to be display in a ListView
                for(int count = 0 ; count < jsonArray.length();count++)
                {
                    JSONObject jsonEventData = jsonArray.getJSONObject(count);
                    eventListData = "Title :\t" + jsonEventData.optString("event_title") + "\n" +
                            "Type of Event:\t" + jsonEventData.optString("event_description") + "\n" +
                            "Date:\t" + jsonEventData.optString("event_date");
                    isDateNow("2016-10-25");
                    objListOfEvents.add(eventListData);
                }
                objread.close();
                return eventListData;

            }
            catch (IOException | JSONException e)
            {
                e.printStackTrace();
            }

            return "Nothing Returned!!";
        }
        //**********************************************************************
        @Override
        protected void onPostExecute(String s)
        {
            SharedPreferences mySharedPref = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
            if(s.trim().equalsIgnoreCase("Nothing Returned!!"))
            {
                Toast.makeText(Events.this,R.string.error_message,Toast.LENGTH_LONG).show();
            }
            else
            {
                ArrayAdapter<String> arrAdapter = new ArrayAdapter<>(Events.this, android.R.layout.simple_list_item_1, objListOfEvents);
                eventlist.setAdapter(arrAdapter);
                //Check if notification should be shown
                if(mySharedPref.getBoolean("showNotification",false))
                {
                    setNotification();
                }
            }
        }
        //**********************************************************************
        private void setNotification()
        {
            //Set Notifcation Properties
            NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(Events.this);
            notifyBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notifyBuilder.setTicker("Baby House Event Today !!!");
            notifyBuilder.setContentTitle("Baby House La Lucia");
            notifyBuilder.setContentText("Contact Us or View the Events Screen for More Info...");
            notifyBuilder.setAutoCancel(true);
            notifyBuilder.setVibrate(new long[] { 200, 200, 600, 600});
            notifyBuilder.setLights(Color.RED,50,50);

            //Create back stack for the activity after back button clicked
            //Adds the intent back to the top of the stack
            Intent lauchActivity = new Intent(Events.this, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(Events.this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(lauchActivity);

            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            notifyBuilder.setContentIntent(resultPendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1,notifyBuilder.build());
        }
        //***********************************************************************
        public void isDateNow(String eventDate)
        {
            //Compare the event date with current date
            //Convert the dates into date object for comparison
            SharedPreferences mySharedPref = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mySharedPref.edit();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            Date currDate;
            Date dateOfEvent;
            try {
                currDate = simpleDateFormat.parse(String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day));
                dateOfEvent = simpleDateFormat.parse(eventDate);

                //If the dates are equal , set the notification preference to true
                if(currDate.equals(dateOfEvent))
                {
                    editor.putBoolean("showNotification",true);
                    editor.apply();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //**********************************************************************
    }
}
