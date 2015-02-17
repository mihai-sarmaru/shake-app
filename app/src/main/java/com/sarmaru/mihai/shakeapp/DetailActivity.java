package com.sarmaru.mihai.shakeapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;


public class DetailActivity extends ActionBarActivity {

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
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Unable to create map", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
