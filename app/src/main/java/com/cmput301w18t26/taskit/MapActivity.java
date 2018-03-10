package com.cmput301w18t26.taskit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Brady on 2018-03-10.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // set camera to user location
        // TODO: get user location information and input here
        LatLng userLocation = new LatLng(100, 100);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        // set pins at all tasks within 5 km
        // TaskList nearbyTasks = [retrieve list of tasks within 5 km of userLocation]
        // for (Task task: nearbyTasks){
        //      LatLng location = task.getLocation().[convert to LatLng]
        //      googleMap.addMarker(new MarkerOptions().position(location).title(task.getTitle());
        // }
        //
        // additionally can optionally constraint camera view to within 5 km of user location.
    }
}
