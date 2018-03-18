package com.cmput301w18t26.taskit;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Brady on 2018-03-10.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private final Integer MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private MapFragment mapFragment;
    private final float FIVE_KM_ZOOM = 11;
    private GoogleMap myMap;
    private String GPSLocation;
    private TaskItData db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = TaskItData.getInstance();
        setContentView(R.layout.map);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        GPSLocation = LocationManager.GPS_PROVIDER;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationManager.removeUpdates(locationListener);
                mapStart(location);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // set camera to user location
        // TODO: get user location information and input here
        myMap = googleMap;
        setCurrentLocation();
        // set pins at all tasks with status requested/bidded within 5 km of userLocation
        // TaskList nearbyTasks = [retrieve list of tasks within 5 km of userLocation]
        // for (Task task: nearbyTasks){
        //      LatLng location = task.getLocation().[convert to LatLng]
        //      googleMap.addMarker(new MarkerOptions().position(location).title(task.getTitle());
        // }
        //
        // additionally can optionally constraint camera view to within 5 km of user location.
    }

    public void setCurrentLocation() {


        int FINEPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (FINEPermission != PackageManager.PERMISSION_GRANTED){
            String[] permissionStrings = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(MapActivity.this,
                    permissionStrings,
                    MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);

        } else {

            locationManager.requestLocationUpdates(GPSLocation, 0, 0, locationListener);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setCurrentLocation();
            } else {
                finish();
            }
        }
    }

    public void mapStart(Location location){
        Log.i("MapActivity", "Current location = " + location.toString());
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, FIVE_KM_ZOOM));
        myMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));

    }
}
