package lalucia.babyhouse.babyhouse;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterScreen extends AppCompatActivity {

    private EditText nameedt;
    private EditText surnameedt;
    private EditText emailedt;
    private EditText contactNumberedt;
    private EditText passwordedt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();
    }

    //*************************************************************
    public void RegisterButtonClick(View v)
    {
        Person objPerson = new Person(nameedt.getText().toString().trim(),
                                     surnameedt.getText().toString().trim(),
                                     emailedt.getText().toString().trim(),
                                     contactNumberedt.getText().toString().trim(),
                                     passwordedt.getText().toString().trim());

        if(Validation(objPerson.getPersonName(),
                     objPerson.getPersonSurname(),
                     objPerson.getPersonEmail(),
                     objPerson.getPersonContactNumber(),objPerson.getPersonPassword()))
        {
            new SendData().execute(objPerson);
        }
    }
    //*************************************************************
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
    //*************************************************************
    public void initialize()
    {
        nameedt = (EditText) findViewById(R.id.edtName);
        surnameedt = (EditText) findViewById(R.id.edtSurname);
        emailedt = (EditText) findViewById(R.id.edtEmail);
        contactNumberedt = (EditText) findViewById(R.id.edtPhone);
        passwordedt = (EditText) findViewById(R.id.edtPersonPassword);
    }
    //*************************************************************
    public boolean Validation(String name, String surname, String email , String contact , String password)
    {
        boolean isValid = true;

        //Check for Nulls
        if(name.isEmpty())
        {
            isValid = false;
            Toast.makeText(this,R.string.error_message_empty_name,Toast.LENGTH_SHORT).show();
        }
        else if(surname.isEmpty())
        {
            isValid = false;
            Toast.makeText(this,R.string.error_message_empty_surname,Toast.LENGTH_SHORT).show();
        }
        else if(email.isEmpty())
        {
            isValid = false;
            Toast.makeText(this,R.string.error_message_empty_email,Toast.LENGTH_SHORT).show();
        }
        else if(contact.isEmpty())
        {
            isValid = false;
            Toast.makeText(this,R.string.error_message_empty_contactnumber,Toast.LENGTH_SHORT).show();
        }
        else if(password.isEmpty())
        {
            isValid = false;
            Toast.makeText(this,R.string.error_message_empty_password,Toast.LENGTH_SHORT).show();
        }

        //if no nulls are found validate contact Number
        if(isValid)
        {
            if(contact.length() < 10)
            {
                isValid = false;
                Toast.makeText(this,R.string.error_message_empty_contactnumber_small,Toast.LENGTH_SHORT).show();
            }
        }
        return isValid;
    }
    //********************************************************************************
    public class SendData extends AsyncTask<Person, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            Toast.makeText(RegisterScreen.this,"Your Details Are Being Sent",Toast.LENGTH_LONG).show();
        }
        //********************************************************************************
        @Override
        protected String doInBackground(Person... params) {

            String line;
            String entireLine = "";
            String app_data ;
            HttpURLConnection urlConnection;
            Person objPerson = params[0];
            //Create connection to the url
            try {
                URL url = new URL("http://www.babyhouse.dx.am/registerPersonAndroid.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                //Write the data/post which is the student number to the url
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter objWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                app_data = URLEncoder.encode("firstname","UTF-8")+"="+URLEncoder.encode(objPerson.getPersonName(),"UTF-8")+"&"+
                        URLEncoder.encode("lastname","UTF-8")+"="+URLEncoder.encode(objPerson.getPersonSurname(),"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(objPerson.getPersonEmail(),"UTF-8")+"&"+
                        URLEncoder.encode("contact","UTF-8")+"="+URLEncoder.encode(objPerson.getPersonContactNumber(),"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(objPerson.getPersonPassword(),"UTF-8");

                objWriter.write(app_data);
                objWriter.flush();
                objWriter.close();
                outputStream.close();

                //Retrieve the input from the url
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader objReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                while ((line = objReader.readLine()) != null) {
                    entireLine += line;
                }

                objReader.close();
                inputStream.close();
                urlConnection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return entireLine;
        }
        //********************************************************************************
        @Override
        protected void onPostExecute(String s)
        {
            if (s.trim().equalsIgnoreCase("Successfully added"))
            {
                Toast.makeText(RegisterScreen.this,"You Are Registered", Toast.LENGTH_SHORT).show();
                Intent newActivity;
                newActivity = new Intent(RegisterScreen.this, Login.class);
                startActivity(newActivity);
                finish();
            }
            else
            {
                Toast.makeText(RegisterScreen.this, R.string.error_message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
