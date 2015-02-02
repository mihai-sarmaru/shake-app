package com.sarmaru.mihai.shakeapp;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.yellow, R.color.green, R.color.red);

        // Register receiver to check for Internet connection
        registerReceiver(new ConnectionChangeReceiver(),
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
