package com.sarmaru.mihai.shakeapp;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout loadingLayout;
    RecyclerView quakeRecyclerView;
    RecyclerView.Adapter adapter;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.yellow, R.color.green, R.color.red);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utils.isNetworkAvailable(getApplicationContext())) {
                    ShakeAppPreferences prefs = new ShakeAppPreferences(getApplicationContext());
                    prefs.setLatestDatabaseId(0);
                    new GetQuakeAsync().execute();
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_no_internet),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Register receiver to check for Internet connection
        receiver =  new ConnectionChangeReceiver();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        quakeRecyclerView = (RecyclerView) findViewById(R.id.quake_main_list);
        quakeRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        quakeRecyclerView.setLayoutManager(layoutManager);

        loadingLayout = (RelativeLayout) findViewById(R.id.getting_quakes_layout);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        TextView iconTV = (TextView) findViewById(R.id.wait_icon_tv);
        iconTV.setTypeface(typeface);

        new GetQuakeAsync().execute();
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

    @Override
    protected void onStop() {
        ShakeAppPreferences prefs = new ShakeAppPreferences(this);
        prefs.setLatestDatabaseId(0);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private class GetQuakeAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                while (Utils.isServiceRunning(getApplicationContext(), ShakeAppService.class)) {
                    try {
                        Thread.sleep(200);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            adapter = new QuakeListAdapter(db.getQuakeList(), getApplicationContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (adapter != null) {
                quakeRecyclerView.setAdapter(adapter);
            }
            loadingLayout.setVisibility(View.GONE);
            quakeRecyclerView.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
