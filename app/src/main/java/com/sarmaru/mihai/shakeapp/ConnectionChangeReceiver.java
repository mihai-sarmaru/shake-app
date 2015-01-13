package com.sarmaru.mihai.shakeapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Mihai Sarmaru on 13.01.2015.
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isConnectedToInternet(intent)) {
            // TODO Start Service
        } else {
            // TODO Stop Service
        }
    }

    private boolean isConnectedToInternet (Intent intent) {
        NetworkInfo network = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
        logConnections(network);
        return network.isConnected();
    }

    private void logConnections(NetworkInfo network) {
        String connection = network.isConnected() ? "CONNECTED" : "NO CONNECTION";
        Log.d("INTERNET", connection);
    }
}
