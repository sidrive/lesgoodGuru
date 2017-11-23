package com.lesgood.guru.data.remote;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Agus on 3/2/17.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    private DatabaseReference databaseRef;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        this.databaseRef = FirebaseDatabase.getInstance().getReference();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.e("FCN TOKEN GET", "Refreshed token: " + refreshedToken);

        final Intent intent = new Intent("tokenReceiver");
        // You can also include some extra data.
        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        intent.putExtra("token",refreshedToken);
        broadcastManager.sendBroadcast(intent);

    }
    // [END refresh_token]

    private void sendRegistrationToServer(String token) {

    }
}
