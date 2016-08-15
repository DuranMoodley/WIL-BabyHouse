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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BlogPosts extends AppCompatActivity {

    EditText blogHeading;
    EditText blogBody;
    Blog objBlog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_posts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        blogHeading = (EditText) findViewById(R.id.edtHeading);
        blogBody = (EditText) findViewById(R.id.edtBody);
    }
    //***********************************************************************
    public void btnCreateBlog(View v)
    {
        objBlog = new Blog(blogHeading.getText().toString(),blogBody.getText().toString());
        RetrieveFeed objFeed = new RetrieveFeed();
        objFeed.execute();
    }
    //***********************************************************************
    public class RetrieveFeed extends AsyncTask<String, Void, String>
    {
        //********************************************************************************
        @Override
        protected String doInBackground(String... params) {
            String line = "";
            String entireLine = "";
            String app_data = "";
            HttpURLConnection urlConnection = null;
            try {
                //Create connection to the url
                URL url = new URL("http://www.duran.dx.am/blog.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                //Write the data/post which is the student number to the url
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter objWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                app_data = URLEncoder.encode("bHeading","UTF-8")+"="+URLEncoder.encode(objBlog.getBlogHeading(),"UTF-8") +"&" +
                           URLEncoder.encode("bBody","UTF-8")+"="+URLEncoder.encode(objBlog.getBlogBody(),"UTF-8");
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
                return entireLine;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return entireLine;
        }
        //********************************************************************************
        @Override
        protected void onPostExecute(String s)
        {
            Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
        }
    }
}
