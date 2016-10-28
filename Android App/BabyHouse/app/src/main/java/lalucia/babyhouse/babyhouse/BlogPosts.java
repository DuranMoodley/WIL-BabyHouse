/*
BlogPosts.java
Sends a blog post to the Online MYSQL Database
Lecturer : Rajesh Chanderman
WIL Assessment
Date Updated : 10/24/16
 */
package lalucia.babyhouse.babyhouse;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BlogPosts extends AppCompatActivity {

    private EditText blogHeading;
    private EditText blogBody;
    private Blog objBlog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_posts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        blogHeading = (EditText) findViewById(R.id.edtHeading);
        blogBody = (EditText) findViewById(R.id.edtBody);
    }
    //***********************************************************************
    public void btnCreateBlog(View v)
    {
        //Record the data of the blog and add it the object
        //Send data to the Dbd
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        Date date = new Date();
        dateFormat.format(date);

        objBlog = new Blog(blogHeading.getText().toString(),blogBody.getText().toString(),date);

        if(Validation(objBlog.getBlogHeading(),objBlog.getBlogBody()))
        {
            SendBlogFeed objFeed = new SendBlogFeed();
            objFeed.execute();
        }
    }
    //***********************************************************************
    private boolean Validation(String heading, String body)
    {
        //Check if fields are empty, show error messages when needed
        boolean isValid = true;
        if(heading.isEmpty())
        {
            isValid = false;
            Toast.makeText(this,"Please Enter A Heading",Toast.LENGTH_SHORT).show();
        }
        else if(body.isEmpty())
        {
            isValid = false;
            Toast.makeText(this,"Please Enter Something in the Body",Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }
    //***************************************************
    public class SendBlogFeed extends AsyncTask<String, Void, String>
    {
        //********************************************************************************
        @Override
        protected String doInBackground(String... params) {
            String line;
            String entireLine = "Nothing";
            String app_data;
            HttpURLConnection urlConnection;
            try {
                //Create connection to the url
                URL url = new URL("http://www.babyhouse.dx.am/blogAndroid.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                //Write the data/post to the php script containing blog data fields
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter objWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                app_data = URLEncoder.encode("bHeading","UTF-8")+"="+URLEncoder.encode(objBlog.getBlogHeading(),"UTF-8") +"&" +
                           URLEncoder.encode("bBody","UTF-8")+"="+URLEncoder.encode(objBlog.getBlogBody(),"UTF-8")+"&"+
                           URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(String.valueOf(objBlog.getBlogDate()),"UTF-8");
                objWriter.write(app_data);
                objWriter.flush();
                objWriter.close();
                outputStream.close();

                //Retrieve the input from the url
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader objReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                while ((line = objReader.readLine()) != null) {
                    entireLine = line;
                }

                objReader.close();
                inputStream.close();
                urlConnection.disconnect();
                return entireLine;
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
                if (!s.equalsIgnoreCase("Nothing")) {
                    Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), R.string.error_message, Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getApplication(), R.string.error_message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
