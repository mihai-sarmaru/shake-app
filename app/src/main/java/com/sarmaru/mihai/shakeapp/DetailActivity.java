package com.sarmaru.mihai.shakeapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class DetailActivity extends ActionBarActivity {

    private static final int MAP_ZOOM = 6;
    private GoogleMap googleMap;

    private static final String QUAKEID = "QUAKEID";
    private QuakeObject quake = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent quakeIntent = getIntent();
        if (quakeIntent.hasExtra(QUAKEID)) {
            DatabaseHandler db = new DatabaseHandler(this);
            quake = db.getQuakeObject(quakeIntent.getExtras().getInt(QUAKEID));
        } else {
            Toast.makeText(this, getString(R.string.toast_no_quake_info), Toast.LENGTH_LONG).show();
            finish();
        }

        try {
            initializeMap();
        } catch (Exception ex) {
            Log.d("MAPS", "Failed initializing maps");
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeMap();
    }

    private void initializeMap () {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
            createQuakeMarker(this.quake);
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.toast_unable_create_maps), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createQuakeMarker (QuakeObject quake) {
        // Position marker on map
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(quake.getLatitude(), quake.getLongitude()))
                .title(String.valueOf(quake.getMagnitude()) + " " + quake.getRegion());
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        googleMap.addMarker(marker);

        // Position camera on event
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(quake.getLatitude(), quake.getLongitude()))
                .zoom(MAP_ZOOM).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }
}
