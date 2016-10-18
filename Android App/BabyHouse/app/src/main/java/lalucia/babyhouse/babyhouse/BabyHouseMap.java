package lalucia.babyhouse.babyhouse;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class BabyHouseMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_house_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap = googleMap;
        //button to fetch current location
        googleMap.setMyLocationEnabled(true);

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        //to get provider
        Criteria criteria = new Criteria();

        //provider
        String provider = LocationManager.NETWORK_PROVIDER;
        //String provider = lm.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        Location location = lm.getLastKnownLocation(provider);

        googleMap.setMapType(googleMap.MAP_TYPE_HYBRID);

        //finds current lat lng
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        //sets lat lng
        LatLng latLng = new LatLng(latitude,longitude);


        googleMap.moveCamera((CameraUpdateFactory.newLatLng(latLng)));

        googleMap.animateCamera((CameraUpdateFactory.zoomTo(20)));

        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title("You are here"));


        //baby house
        // Create marker
        mMap = googleMap;
        LatLng babyhouse = new LatLng(-29.7454389, 31.0577183);

        //markers
        mMap.addMarker(new MarkerOptions().position(babyhouse).title("Baby House - Call").snippet("43 Ronan Road ,La Lucia ,4051 "));

        //open map on baby house coordinates
        double blat = -29.7454389;
        double blng = 31.0577183;
        LatLng bLatLng = new LatLng(blat,blng);
        mMap.moveCamera((CameraUpdateFactory.newLatLng(bLatLng)));
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
        
        //adding polylines
        PolylineOptions options = new PolylineOptions()
                .add(latLng)
                .add(bLatLng);

        mMap.addPolyline(options);
    }

    }
