/*
BabyHouseMap.java
Shows user the directions and draws a poly line
Lecturer : Rajesh Chanderman
WIL Assessment
Date Updated : 10/24/16
 */
package lalucia.babyhouse.babyhouse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class BabyHouseMap extends FragmentActivity implements OnMapReadyCallback , com.google.android.gms.location.LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private PolylineOptions polylineOptions;
    private Polyline polyline;
    private GoogleApiClient googleApiClient;
    private LocationRequest objLocationRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_house_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        createLocationRequest();
        setUpGoogleApiClient();
    }
    //**********************************************************************************************
    private void createLocationRequest()
    {
        objLocationRequest = LocationRequest.create()
                .setInterval(10000)
                .setFastestInterval(5000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }
    //**********************************************************************
    private void setUpGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(BabyHouseMap.this).addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();
        googleApiClient.connect();
    }
    //**********************************************************************
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng bLatLngBabyHouse = new LatLng(-29.7454389,31.0577183);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }

        //markers
        mMap.addMarker(new MarkerOptions().position(bLatLngBabyHouse).title("Baby House - Call").snippet("43 Ronan Road ,La Lucia ,4051 "));

        //open map on baby house coordinates
        mMap.moveCamera((CameraUpdateFactory.newLatLng(bLatLngBabyHouse)));
        mMap.animateCamera((CameraUpdateFactory.zoomTo(14)));

        // Handle marker info window clicks by dialing the number for the corresponding business.
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.getId().equals("m0")) { // if marker is equal to baby house , dial their number.
                    Uri number = Uri.parse("tel:0849895432");
                    Intent intent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(intent);
                }}
        });

        mMap.setMyLocationEnabled(true);
        polylineOptions = new PolylineOptions().width(10).color(Color.BLUE).geodesic(true).add(bLatLngBabyHouse);
        polyline = mMap.addPolyline(polylineOptions);
        polyline.setVisible(true);
    }
    //***********************************************************************
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }
    //***********************************************************************
    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Sorry, A problem has occured. Please Restart Application.", Toast.LENGTH_SHORT).show();
    }
    //***********************************************************************
    @Override
    public void onLocationChanged(Location location) {
        if(location != null)
        {
            LatLng newPoint = new LatLng(location.getLatitude(), location.getLongitude());
            List<LatLng> points = polyline.getPoints();
            points.add(newPoint);
            polyline.setPoints(points);
        }
        else{
            Toast.makeText(BabyHouseMap.this,"Please put on your location settings",Toast.LENGTH_LONG).show();
        }
    }
    //***********************************************************************
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Sorry, A problem has occured. Please Restart Application.", Toast.LENGTH_SHORT).show();
    }
    //*************************************************************************
    private void stopLocationUpdates()
    {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
    }
    //***********************************************************************
    private void startLocationUpdates()
    {
        if (ActivityCompat.checkSelfPermission(BabyHouseMap.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BabyHouseMap.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
    //***********************************************************************
    @Override
    public void onPause()
    {
        super.onPause();
        stopLocationUpdates();
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
    //**************************************************************************************
    @Override
    public void onStart()
    {
        super.onStart();
        googleApiClient.connect();
    }
}
