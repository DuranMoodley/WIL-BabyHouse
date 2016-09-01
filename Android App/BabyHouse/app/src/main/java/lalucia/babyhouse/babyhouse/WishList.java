package lalucia.babyhouse.babyhouse;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class WishList extends AppCompatActivity {

    TextView wishListtv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        wishListtv = (TextView) findViewById(R.id.tvWishList);
        new RetrieveWishListData().execute();
    }
    //**********************************************************************
    public class RetrieveWishListData extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            String line = "";
            String entireLine = "";
            JSONObject jsonObject;
            JSONArray jsonArray;
            HttpURLConnection urlConnection = null;
            String wishListData = "";
            URL url = null;
            try {
                url = new URL("http://www.duran.dx.am/wishlistbabyhouse.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                BufferedReader objread = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = objread.readLine()) != null) {
                    entireLine += line;
                }

                //Get json elements
                jsonObject = new JSONObject(entireLine);
                jsonArray = jsonObject.optJSONArray("wishlist");

                for(int count = 0 ; count < jsonArray.length();count++)
                {
                    JSONObject jsonStudentData = jsonArray.getJSONObject(count);
                    wishListData = jsonStudentData.optString("wish_title") + "\n" + jsonStudentData.optString("wish_description");
                }
                objread.close();
                return wishListData;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "Nothing Returned!!";
        }
        //**********************************************************************
        @Override
        protected void onPostExecute(String s)
        {
            wishListtv.setText(s);
        }
    }
}
