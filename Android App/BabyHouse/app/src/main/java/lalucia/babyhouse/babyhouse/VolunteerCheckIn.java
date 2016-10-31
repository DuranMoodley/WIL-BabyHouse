/*
VolunteerCheckIn.java
Allows a volunteer to enter in their check in and check out times, Details will be saved to online database
Lecturer : Rajesh Chanderman
WIL Assessment
Date Updated : 10/24/16
 */
package lalucia.babyhouse.babyhouse;

import android.Manifest;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VolunteerCheckIn extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private EditText checkInedt;
    private EditText checkOutedt;
    private String checkInTime;
    private String checkOutTime;
    private String volId;
    private String date;
    private GoogleApiClient googleApiClient;
    private LocationRequest objLocationRequest;
    private Location objLocation;
    private ArrayList<Geofence> geofenceArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_check_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if( getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createLocationRequest();
        setUpGoogleApiClient();
        date = DateFormat.getDateInstance().format(new Date());
        checkInedt = (EditText) findViewById(R.id.edtCheckInTime);
        checkOutedt = (EditText) findViewById(R.id.edtCheckOut);

        //Listen if user has clicked on edit text fields, show time picker
        checkInedt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showTimePicker(checkInedt);
                checkInedt.setShowSoftInputOnFocus(false);
            }
        });

        checkOutedt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                showTimePicker(checkOutedt);
                checkOutedt.setShowSoftInputOnFocus(false);
            }
        });
    }
    //******************************************************
    public int getTimeDifference()
    {
        //Calculate the difference between the check in and check out times
        DateFormat df = new SimpleDateFormat("hh:mm", Locale.US);
        int min = 0;
        try {
            Date dateTimeCheckIn = df.parse(checkInTime);
            Date dateTimeCheckOut = df.parse(checkOutTime);
            long difference = dateTimeCheckOut.getTime() - dateTimeCheckIn.getTime();
            min = (int) (difference/(60*1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return min;
    }
    //***********************************************************************
    private void startGeofenceMonitoring()
    {
        //Sets up the geofence of the volunteer location
        if(objLocation != null)
        {
            geofenceArrayList = new ArrayList<>();
            geofenceArrayList.add(new Geofence.Builder()
                    .setRequestId("myfence")
                    .setCircularRegion(objLocation.getLatitude(), objLocation.getLongitude(), 50)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setNotificationResponsiveness(1000)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());

            GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
                    .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                    .addGeofences(geofenceArrayList).build();

            Intent intent = new Intent(this, GeofenceTransitionIntentService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (!googleApiClient.isConnected()) {
                Toast.makeText(this, "Google API Client not connected", Toast.LENGTH_LONG).show();
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                LocationServices.GeofencingApi.addGeofences(
                        googleApiClient,
                        geofencingRequest,
                        pendingIntent).
                        setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                if (!status.isSuccess()) {
                                    Toast.makeText(VolunteerCheckIn.this,R.string.error_message, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
    }
    //***************************************************************
    private void createLocationRequest()
    {
        objLocationRequest = LocationRequest.create()
                .setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    //**********************************************************************
    private void setUpGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();
    }
    //**********************************************************************
    public void showTimePicker(final EditText showtime)
    {
        //Open the time picker dialog when the date edit text is selected
        showtime.setEnabled(false);
        final Calendar caltime = Calendar.getInstance();
        int hour = caltime.get(Calendar.HOUR_OF_DAY);
        int minute = caltime.get(Calendar.MINUTE);

        final TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay)
            {
                showtime.setText(String.format("%02d:%02d",hourOfDay,minuteOfDay));
            }
        },hour,minute,true);

        timePicker.show();
    }
    //********************************************************************************
    public void saveCheckinTime(View view)
    {
        //Validate input fields
        if(checkOutedt.getText().toString().isEmpty() || checkInedt.getText().toString().isEmpty())
        {
            Toast.makeText(VolunteerCheckIn.this,"Please Enter in a Check in and Check Out" ,Toast.LENGTH_LONG).show();
        }
        else
        {
            checkOutTime = checkOutedt.getText().toString();
            checkInTime =  checkInedt.getText().toString();
            new RetrieveLocation().execute();
        }
    }
    //********************************************************************************
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }
    //********************************************************************************
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, objLocationRequest, this);
    }
    //********************************************************************************
    @Override
    public void onConnectionSuspended(int i) {
            //Blank Method
    }
    //********************************************************************************
    @Override
    public void onLocationChanged(Location location) {
        objLocation = location;
    }
    //********************************************************************************
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }
    //********************************************************************************
    private void stopLocationUpdates()
    {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
    }
    //**************************************************************************************
    @Override
    public void onResume()
    {
        super.onResume();
        if(googleApiClient.isConnected())
        {
            startLocationUpdates();
        }
    }
    //*************************************************************************************
    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }
    //*************************************************************************************
    @Override
    public void onStart()
    {
        super.onStart();
        googleApiClient.connect();
    }
    //********************************************************************************
    @Override
    public void onPause()
    {
        super.onPause();
        stopLocationUpdates();
    }
    //**************************************************************************************
    public class SendData extends AsyncTask<String, Void, String> {
        String personId;

        @Override
        protected void onPreExecute()
        {
            //Get Share preferences data that will be used when search for the volunteer
            SharedPreferences myprefs = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
            Toast.makeText(VolunteerCheckIn.this, "Please Wait...", Toast.LENGTH_LONG).show();
            personId = String.valueOf(myprefs.getInt("personId",0));
        }
        //********************************************************************************
        @Override
        protected String doInBackground(String... params) {

            String entireLine = "Nothing";
            String line;
            String app_data;
            HttpURLConnection urlConnection;
            JSONObject jsonObject;
            JSONArray jsonArray;

            //Create connection to the url
            try {
                URL url = new URL("http://www.babyhouse.dx.am/getVolunteerIdValueAndroid.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                //Write the data/post which contains the person ID field
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter objWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                app_data = URLEncoder.encode("PersonId", "UTF-8")+"="+URLEncoder.encode(personId, "UTF-8");

                objWriter.write(app_data);
                objWriter.flush();
                objWriter.close();
                outputStream.close();

                //Return the volunteer ID of the person who is check in
                BufferedReader objread = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = objread.readLine()) != null)
                {
                    entireLine = line;
                }

                //Read JSON fields
                jsonObject = new JSONObject(entireLine);
                jsonArray = jsonObject.optJSONArray("volunteer");

                for(int count = 0 ; count < jsonArray.length();count++)
                {
                    JSONObject personInformation = jsonArray.getJSONObject(count);
                    volId = personInformation.optString("volunteerID");
                }

                entireLine = volId;
                urlConnection.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return entireLine;
        }
        //********************************************************************************
        @Override
        protected void onPostExecute(String s)
        {
            //Display messages where appropriate
            if(!s.isEmpty())
            {
                if (s.equalsIgnoreCase("Nothing")) {
                    Toast.makeText(VolunteerCheckIn.this, "Please Try Again!!!", Toast.LENGTH_LONG).show();
                } else {
                    //If volunteer ID found, insert the relevant details to database
                    Toast.makeText(VolunteerCheckIn.this, "Inserting...", Toast.LENGTH_LONG).show();
                    new InsertData().execute(s);
                }
            }
            else
            {
                Toast.makeText(VolunteerCheckIn.this, R.string.error_message, Toast.LENGTH_LONG).show();
                Toast.makeText(VolunteerCheckIn.this, s, Toast.LENGTH_LONG).show();
            }
        }
    }
    //********************************************************************************
    public class InsertData extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            String entireLine = "";
            String line;
            String vol_data;
            URL url2;
            HttpURLConnection urlConnection2;
            try {
                url2 = new URL("http://www.babyhouse.dx.am/addVolunteerAttendanceAndroid.php");
                urlConnection2 = (HttpURLConnection) url2.openConnection();
                urlConnection2.setRequestMethod("POST");
                urlConnection2.setDoInput(true);
                urlConnection2.setDoOutput(true);


                //Write the data/post which contains volunteer information that will be saved to database
                OutputStream outputStreamAttendance = urlConnection2.getOutputStream();
                BufferedWriter objWriteAttendance = new BufferedWriter(new OutputStreamWriter(outputStreamAttendance, "UTF-8"));
                vol_data = URLEncoder.encode("volunteerid", "UTF-8")+"="+URLEncoder.encode(params[0], "UTF-8")+"&"+
                        URLEncoder.encode("dateCheckIn","UTF-8")+"="+URLEncoder.encode(date,"UTF-8")+"&"+
                        URLEncoder.encode("timeCheckIn","UTF-8")+"="+URLEncoder.encode(checkInTime,"UTF-8")+"&"+
                        URLEncoder.encode("timeCheckOut","UTF-8")+"="+URLEncoder.encode(checkOutTime,"UTF-8")+"&"+
                        URLEncoder.encode("timeWorked","UTF-8")+"="+URLEncoder.encode(String.valueOf(getTimeDifference()),"UTF-8");

                objWriteAttendance.write(vol_data);
                objWriteAttendance.flush();
                objWriteAttendance.close();
                outputStreamAttendance.close();

                //Read confirmation of whether the data has been saved
                BufferedReader objreadAttendanceAdded = new BufferedReader(new InputStreamReader(urlConnection2.getInputStream()));
                while ((line = objreadAttendanceAdded.readLine()) != null)
                {
                    entireLine += line;
                }

                urlConnection2.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return entireLine;
        }
        //********************************************************************************
        @Override
        protected void onPostExecute(String s)
        {
            if(!s.isEmpty())
            {
                if(s.equalsIgnoreCase("Successfully Updated"))
                {
                    Toast.makeText(VolunteerCheckIn.this, s, Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(VolunteerCheckIn.this, R.string.error_message, Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(VolunteerCheckIn.this, R.string.error_message, Toast.LENGTH_LONG).show();
            }
        }
    }
    //********************************************************************************
    public class RetrieveLocation extends AsyncTask<Location, Void, String>
    {
        public RetrieveLocation()
        {
            super();
        }
        //***************************************************************************
        @Override
        protected void onPreExecute()
        {
            Toast.makeText(VolunteerCheckIn.this, "Finding Your Location....", Toast.LENGTH_SHORT).show();
        }
        //***************************************************************************
        @Override
        protected String doInBackground(Location... params) {
            String myAddress = "";
            String errorMessage = "Location Not Found";
            if (Geocoder.isPresent()) {
                Geocoder objCoder = new Geocoder(VolunteerCheckIn.this);
                try {
                    if (ActivityCompat.checkSelfPermission(VolunteerCheckIn.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(VolunteerCheckIn.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return "";
                    }

                    //Reverse geocode location and get street address
                    if (objLocation != null)
                    {
                        List<Address> objAddressList = objCoder.getFromLocation(objLocation.getLatitude(), objLocation.getLongitude(), 1);
                        if (objAddressList != null) {
                            for (Address addressLoc : objAddressList) {
                                int addressIndex = addressLoc.getMaxAddressLineIndex();
                                for (int count = 0; count <= addressIndex; count++) {
                                    String line = addressLoc.getAddressLine(count);
                                    myAddress += line + "\n";
                                }
                            }
                        }
                    }
                    else
                    {
                        return errorMessage;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return errorMessage;
            }
            return myAddress;
        }
        //*****************************************************
        @Override
        protected void onPostExecute(String address)
        {
            //Retrieve volunteer location and display to the user
            if(!address.isEmpty())
            {
                if (!address.equalsIgnoreCase("Location Not Found"))
                {
                    Toast.makeText(VolunteerCheckIn.this, "You checked into :\n" + address, Toast.LENGTH_SHORT).show();
                    startGeofenceMonitoring();
                    new SendData().execute();
                } else {
                    Toast.makeText(VolunteerCheckIn.this,address + "\nPlease Try Again\nPlease Make Sure your Location and WIFI Settings are on", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                address += "\nPlease Try Again!!\n"+"Please Make Sure your Location and WIFI Settings are on";
                Toast.makeText(VolunteerCheckIn.this,address, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
