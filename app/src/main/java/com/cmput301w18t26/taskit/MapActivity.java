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

/**
 * Activity for starting Google Maps and handling the interactions with it
 * will likely be refactored into multiple classes in the future so this is View class
 * and the controller functionality can be separated out
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private final Integer MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;
    private LocationManager locationManager;
    private LocationListener networkListener;
    private LocationListener GPSListener;
    private MapFragment mapFragment;
    private final float FIVE_KM_ZOOM = 11;
    private GoogleMap myMap;
    private String networkProvider;
    private String GPSProvider;
    private TaskItData db;
    private Location currentLocation;

    /**
     * Initialize necessary classes and request a googlemap object
     * @param savedInstanceState handled by android, never you mind
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        db = TaskItData.getInstance();
        currentLocation = null;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        GPSProvider = LocationManager.GPS_PROVIDER;
        networkProvider = LocationManager.NETWORK_PROVIDER;
        networkListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationManager.removeUpdates(networkListener);
                currentLocation = location;
                mapStart();

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
//        GPSListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                locationManager.removeUpdates(networkListener);
//                locationManager.removeUpdates(GPSListener);
//                currentLocation = location;
//                mapStart();
//
//            }
//
//            @Override
//            public void onStatusChanged(String s, int i, Bundle bundle) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String s) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String s) {
//
//            }
//        };
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * overrriden callback method for the OnMapReadyCallback. This is called when the
     * requested googleMap object is ready to use
     * @param googleMap object through which process interacts with Google Maps
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // set camera to user location
        // TODO: get user location information and input here
        myMap = googleMap;
        requestCurrentLocation();

    }

    /**
     * get the app user's current location
     */
    public void requestCurrentLocation() {


        int FINEPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (FINEPermission != PackageManager.PERMISSION_GRANTED){
            String[] permissionStrings = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(MapActivity.this,
                    permissionStrings,
                    MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);

        } else {
            locationManager.requestLocationUpdates(networkProvider, 0, 0, networkListener);
//            locationManager.requestLocationUpdates(GPSProvider, 0, 0, GPSListener);
//            int timeout = 3;
//            while (true) {
//                try {
//                    Thread.sleep(5000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (currentLocation != null || timeout <= 1) {
//                    break;
//                }
//                timeout--;
//            }
//            if (currentLocation == null) {
//                //TODO display some sort of error
//                finish();
//            }
//            locationManager.removeUpdates(networkListener);
//            locationManager.removeUpdates(GPSListener);
//            mapStart();

        }
    }

    /**
     * callback method of implemented OnRequestPermissionsResultCallback interface
     * if permission was granted retry requesting user location, otherwise finish the activity
     * @param requestCode integer code given to the request to identify the request by
     * @param permissions list of permissions requested
     * @param grantResults results of each permission request
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestCurrentLocation();
            } else {
                finish();
            }
        }
    }

    /**
     * initialize the user's view on the map to his/her current location and place markers for all
     * of the tasks within 5 km
     */
    public void mapStart(){
        Log.i("MapActivity", "Current location = " + currentLocation.toString());
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            myMap.setMyLocationEnabled(true);
        }
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, FIVE_KM_ZOOM));
//        myMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
        // set pins at all tasks with status requested/bidded within 5 km of userLocation
        TaskList nearbyTasks = db.tasksWithin5K(currentLocation);
        for (Task task: nearbyTasks.getTasks()) {
            if ( ! task.getLocation().equals("") && task.getLocation() != null) {
                LatLng loc = new LatLng(Double.parseDouble(task.getLocation().split(" ")[0]),
                        Double.parseDouble(task.getLocation().split(" ")[1]));
                myMap.addMarker(new MarkerOptions().position(loc).title(task.getTitle() + ":\n" + task.getDescription()));
            }
        }
    }
}
