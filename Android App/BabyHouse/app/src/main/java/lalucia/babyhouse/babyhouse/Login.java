package lalucia.babyhouse.babyhouse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {

    private EditText emailedt;
    private EditText passwordedt;
    private SharedPreferences myprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();

        myprefs = getSharedPreferences("myPreference",MODE_PRIVATE);
        boolean isLoggedIn = myprefs.getBoolean("isLoggedIn",false);

        if(isLoggedIn)
        {
            Intent newActivity;
            newActivity = new Intent(Login.this, MainActivity.class);
            startActivity(newActivity);
            finish();
        }
    }
    //***************************************************
    public void initialize()
    {
        emailedt = (EditText) findViewById(R.id.edtEmailAddress);
        passwordedt = (EditText) findViewById(R.id.edtPersonPassword);
    }
    //***************************************************
    public void loginButtonClick(View v)
    {
        Person objPerson = new Person(emailedt.getText().toString().trim(),passwordedt.getText().toString().trim());

        if(Validation(objPerson.getPersonEmail(),objPerson.getPersonPassword()))
        {
            new SendData().execute(objPerson);
        }
    }
    //***************************************************
    public boolean Validation(String email, String password)
    {
        boolean isValid = true;
        if(email.isEmpty())
        {
            isValid = false;
            Toast.makeText(this,R.string.error_message_empty_email,Toast.LENGTH_SHORT).show();
        }
        else if(password.isEmpty())
        {
            isValid = false;
            Toast.makeText(this,R.string.error_message_empty_password,Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }
    //***************************************************
    public void RegisterLoginButtonClick(View v)
    {
        Intent newActivity;
        newActivity = new Intent(Login.this, RegisterScreen.class);
        startActivity(newActivity);
    }
    //***************************************************
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    //********************************************************************************
    public class SendData extends AsyncTask<Person, Void, String>
    {
        int personid;
        @Override
        protected void onPreExecute()
        {
            Toast.makeText(Login.this,"Please Wait...",Toast.LENGTH_LONG).show();
        }
        //********************************************************************************
        @Override
        protected String doInBackground(Person... params) {

            String entireLine = "";
            String line ;
            String app_data ;
            HttpURLConnection urlConnection;
            Person objPerson = params[0];
            JSONObject jsonObject;
            JSONArray jsonArray;
            String persondata = "";

            //Create connection to the url
            try {
                URL url = new URL("http://www.babyhouse.dx.am/loginbabyhouseAndroid.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                //Write the data/post which is the student number to the url
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter objWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                app_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(objPerson.getPersonEmail(),"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(objPerson.getPersonPassword(),"UTF-8");

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
                jsonArray = jsonObject.optJSONArray("person");

                for(int count = 0 ; count < jsonArray.length();count++)
                {
                    JSONObject jsonStudentData = jsonArray.getJSONObject(count);
                    persondata = jsonStudentData.optString("isVolunteer");
                    personid =  jsonStudentData.getInt("Person_ID");

                }
                urlConnection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return persondata;
        }
        //********************************************************************************
        @Override
        protected void onPostExecute(String s)
        {
            SharedPreferences myprefs = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = myprefs.edit();
            editor.putString("isVolunteer", s);
            editor.putInt("personId",personid);
            if(!s.isEmpty())
            {
                editor.putBoolean("isLoggedIn",true);
                Toast.makeText(Login.this,"Welcome",Toast.LENGTH_SHORT).show();
                Intent newActivity;
                newActivity = new Intent(Login.this, MainActivity.class);
                startActivity(newActivity);
                finish();
            }
            else
            {
                Toast.makeText(Login.this,s + "Login Unsuccessful. Please Try Again",Toast.LENGTH_SHORT).show();
            }
            editor.apply();
        }
    }
}
