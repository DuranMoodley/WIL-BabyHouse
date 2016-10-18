package lalucia.babyhouse.babyhouse;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragCheckIn extends Fragment implements View.OnClickListener,
        LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    TextView addressTexttv;
    View view;
    Button locationbtn;
    private GoogleApiClient googleApiClient;
    private LocationRequest objLocationRequest;
    Location objLocation;

    public FragCheckIn() {
        // Required empty public constructor
    }
    //**********************************************************************
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
        googleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();
    }
    //**********************************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_check_in, container, false);
        createLocationRequest();
        setUpGoogleApiClient();
        addressTexttv = (TextView) view.findViewById(R.id.tvAddress);
        locationbtn = (Button) view.findViewById(R.id.btnCheckInVolunteer);
        locationbtn.setOnClickListener(this);
        return view;
    }
    //****************************************************************************************
    @Override
    public void onClick(View v) {
        RetrieveLocation objAddress = new RetrieveLocation();
        objAddress.execute();
    }
    //****************************************************************************************
    @Override
    public void onLocationChanged(Location location) {
        objLocation = location;
    }
    //****************************************************************************************
    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        startLocationUpdates();
    }
    //****************************************************************************************
    @Override
    public void onConnectionSuspended(int i) {

    }
    //****************************************************************************************
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_SHORT).show();
    }
    //**********************************************************************
    private void stopLocationUpdates()
    {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
    }
    //**************************************************************************************
    @Override
    public void onPause()
    {
        super.onPause();
        stopLocationUpdates();
    }
    //**************************************************************************************
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            Toast.makeText(getActivity(), "Finding Your Location....", Toast.LENGTH_SHORT).show();
        }
        //***************************************************************************
        @Override
        protected String doInBackground(Location... params) {
            String myAddress = "";
            String errorMessage = "Location Not Found";
            if (Geocoder.isPresent()) {

                Geocoder objCoder = new Geocoder(getActivity());
                try {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            if(!address.isEmpty())
            {
                if (!address.equalsIgnoreCase("Location Not Found")) {
                    addressTexttv.setText("You checked into :\n" + address);
                } else {
                    addressTexttv.setText(address + "\nPlease Try Again\nPlease Make Sure your Location and WIFI Settings are on");
                }
            }
            else
            {
                address += "\nPlease Try Again!!\n"+"Please Make Sure your Location and WIFI Settings are on";
                addressTexttv.setText(address);
            }
        }
    }
}
