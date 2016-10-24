/*
FragEvents.java
Displays event details to the user from MYSQL Database
Lecturer : Rajesh Chanderman
WIL Assessment
Date Updated : 10/24/16
 */
package lalucia.babyhouse.babyhouse;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragEvents extends Fragment
{
    private ListView eventlist;
    //*********************************************************
    public FragEvents()
    {
        // Required empty public constructor
    }
    //*********************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_events, container, false);
        eventlist = (ListView) view.findViewById(R.id.lstEvents);
        new RetrieveEventsListData().execute();
        return view;
    }
    //*********************************************************
    public class RetrieveEventsListData extends AsyncTask<String,Void,String>
    {
        ArrayList objListOfEvents = new ArrayList<>();
        @Override
        protected String doInBackground(String... params)
        {
            String line;
            String entireLine = "";
            JSONObject jsonObject;
            JSONArray jsonArray;
            HttpURLConnection urlConnection;
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
                    objListOfEvents.add(eventListData);
                }
                objread.close();
                return eventListData;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return "Nothing Returned!!";
        }
        //**********************************************************************
        @Override
        protected void onPostExecute(String s)
        {
            if(s.trim().equalsIgnoreCase("Nothing Returned!!"))
            {
                Toast.makeText(getActivity(),R.string.error_message,Toast.LENGTH_LONG).show();
            }
            else
            {
                ArrayAdapter<String> arrAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, objListOfEvents);
                eventlist.setAdapter(arrAdapter);
            }
        }
    }
}
