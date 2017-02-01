package org.vbm.kittyware;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static org.vbm.kittyware.Globals.kitties;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Context thisCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisCon = this;
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        kitties = new DatabaseAccess(this);
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

        // Add a marker in Sydney and move the camera
        LatLng Washington = new LatLng(38.890232, -77.010376);
        //mMap.addMarker(new MarkerOptions().position(Washington).title("You are in Washington"));
        while(kitties.cursor.moveToNext()){
            LatLng temp = new LatLng(kitties.cursor.getDouble(6), kitties.cursor.getDouble(7));
            mMap.addMarker(new MarkerOptions().position(temp).title(kitties.cursor.getString(1)));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Washington));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Globals.kittieName = marker.getTitle();
                Intent intt = new Intent(thisCon, KittieActivity.class);
                thisCon.startActivity(intt);
            }
        });
    }
}
