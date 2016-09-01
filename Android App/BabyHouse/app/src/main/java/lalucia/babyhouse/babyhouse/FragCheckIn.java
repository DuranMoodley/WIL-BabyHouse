package lalucia.babyhouse.babyhouse;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragCheckIn extends Fragment implements View.OnClickListener{

    TextView addressTexttv;
    Geocoder objCoder;
    String provider;
    LocationManager objLocationManager;
    Location objLocation;
    View view;
    Button locationbtn;

    public FragCheckIn() {
        // Required empty public constructor
    }
    //**********************************************************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_check_in, container, false);
        addressTexttv = (TextView) view.findViewById(R.id.tvAddress);
        objLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationbtn = (Button) view.findViewById(R.id.btnCheckInVolunteer);
        provider = LocationManager.NETWORK_PROVIDER;
        locationbtn.setOnClickListener(this);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
        }
        objLocationManager.requestLocationUpdates(provider, 1000, 0, new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider)
            {
                Toast.makeText(getActivity(), "Location Settings On", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider)
            {
                Toast.makeText(getActivity(), "Location Setting Off", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    //****************************************************************************************
    @Override
    public void onClick(View v) {
        RetrieveLocation objAddress = new RetrieveLocation();
        objAddress.execute(objLocation);
    }
    //****************************************************************************************
    public class RetrieveLocation extends AsyncTask<Location, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            Toast.makeText(getActivity(), "Finding Your Location....", Toast.LENGTH_SHORT).show();
        }
        //***************************************************************************
        @Override
        protected String doInBackground(Location... params)
        {
            String myAddress = "";
            if (Geocoder.isPresent())
            {
                objCoder = new Geocoder(getActivity());
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return "";
                }

                params[0] = objLocationManager.getLastKnownLocation(provider);
                if (params[0] != null)
                {
                    List<Address> objAddressList = null;
                    try {
                        objAddressList = objCoder.getFromLocation(params[0].getLatitude(), params[0].getLongitude(), 1);
                        if (objAddressList != null)
                        {
                            for (Address addressLoc : objAddressList)
                            {
                                int addressIndex = addressLoc.getMaxAddressLineIndex();
                                for (int count = 0; count <= addressIndex; count++) {
                                    String line = addressLoc.getAddressLine(count);
                                    myAddress += line + "\n";
                                }
                            }
                        }
                        else
                        {
                            return "No Location Found";
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    return "Nothing Found";
                }
            }
            return myAddress;
        }
        //****************************************************************************************
        @Override
        protected void onPostExecute(String address)
        {
            if(!address.equalsIgnoreCase("Nothing Found") || !address.equalsIgnoreCase("No Location Found"))
            {
                addressTexttv.setText("You checked into :\n" + address);
            }
            else
            {
                addressTexttv.setText(address + "\nPlease Try Again");
            }
        }
    }
}