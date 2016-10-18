package lalucia.babyhouse.babyhouse;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Date;

public class VolunteerCheckIn extends AppCompatActivity {

    EditText checkInedt;
    EditText checkOutedt;
    String checkInTime;
    String checkOutTime;
    String volId;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_check_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        date = DateFormat.getDateInstance().format(new Date());
        checkInedt = (EditText) findViewById(R.id.edtCheckInTime);
        checkOutedt = (EditText) findViewById(R.id.edtCheckOut);

        assert checkInedt != null;
        checkInedt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showTimePicker(checkInedt);
                checkInedt.setShowSoftInputOnFocus(false);
            }
        });

        assert checkOutedt != null;
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
    public void showTimePicker(final EditText showtime)
    {
        //Open the time picker dialog when the date edit text is selected
        showtime.setEnabled(false);
        final Calendar caltime = Calendar.getInstance();
        int hour = caltime.get(Calendar.HOUR_OF_DAY);
        int minute = caltime.get(Calendar.MINUTE);

        final TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                showtime.setText(hourOfDay + ":" + minute);
            }
        },hour,minute,false);

        timePicker.show();
    }
    //********************************************************************************
    public void saveCheckinTime(View view)
    {
        if(checkOutedt.getText().toString().isEmpty() || checkInedt.getText().toString().isEmpty())
        {
            Toast.makeText(VolunteerCheckIn.this,"Please Enter in a Check in and Check Out" ,Toast.LENGTH_LONG).show();
        }
        else
        {
            checkOutTime = checkOutedt.getText().toString();
            checkInTime =  checkInedt.getText().toString();
            new SendData().execute();
        }
    }
    //********************************************************************************
    public class SendData extends AsyncTask<String, Void, String> {
        String personId;

        @Override
        protected void onPreExecute()
        {
            SharedPreferences myprefs = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
            Toast.makeText(VolunteerCheckIn.this, "Please Wait...", Toast.LENGTH_LONG).show();
            personId = String.valueOf(myprefs.getInt("personId",0));
        }
        //********************************************************************************
        @Override
        protected String doInBackground(String... params) {

            String entireLine = "";
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

                //Write the data/post which is the student number to the url
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter objWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                app_data = URLEncoder.encode("PersonId", "UTF-8")+"="+URLEncoder.encode(personId, "UTF-8");

                objWriter.write(app_data);
                objWriter.flush();
                objWriter.close();
                outputStream.close();

                BufferedReader objread = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = objread.readLine()) != null)
                {
                    entireLine += line;
                }
                jsonObject = new JSONObject(entireLine);
                jsonArray = jsonObject.optJSONArray("volunteer");

                for(int count = 0 ; count < jsonArray.length();count++)
                {
                    JSONObject personInformation = jsonArray.getJSONObject(count);
                    volId = personInformation.optString("volunteerID");
                }

                entireLine = volId;
                urlConnection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return entireLine;
        }
        //********************************************************************************
        @Override
        protected void onPostExecute(String s)
        {
            if(s.isEmpty())
            {
                Toast.makeText(VolunteerCheckIn.this,"Please Try Again!!!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(VolunteerCheckIn.this,"Inserting...", Toast.LENGTH_LONG).show();
                new InsertData().execute(s);
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


                //Write the data/post which is the student number to the url

                OutputStream outputStreamAttendance = urlConnection2.getOutputStream();
                BufferedWriter objWriteAttendance = new BufferedWriter(new OutputStreamWriter(outputStreamAttendance, "UTF-8"));
                vol_data = URLEncoder.encode("volunteerid", "UTF-8")+"="+URLEncoder.encode(params[0], "UTF-8")+"&"+
                        URLEncoder.encode("dateCheckIn","UTF-8")+"="+URLEncoder.encode(date,"UTF-8")+"&"+
                        URLEncoder.encode("timeCheckIn","UTF-8")+"="+URLEncoder.encode(checkInTime,"UTF-8")+"&"+
                        URLEncoder.encode("timeCheckOut","UTF-8")+"="+URLEncoder.encode(checkOutTime,"UTF-8");

                objWriteAttendance.write(vol_data);
                objWriteAttendance.flush();
                objWriteAttendance.close();
                outputStreamAttendance.close();

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
            Toast.makeText(VolunteerCheckIn.this,s,Toast.LENGTH_LONG).show();
        }
    }
    //********************************************************************************
}
