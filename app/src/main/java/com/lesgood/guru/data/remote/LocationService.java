package com.lesgood.guru.data.remote;


import android.util.Log;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.guru.data.model.Location;
import com.lesgood.guru.data.model.User;

/**
 * Created by Agus on 3/6/17.
 */

public class LocationService {
    private DatabaseReference databaseRef;
    private GeoFire geoFire;
    private GeoFire geoFire2;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    public LocationService(){
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
        this.mAuth = FirebaseAuth.getInstance();
        this.mUser = mAuth.getCurrentUser();
        this.geoFire = new GeoFire(databaseRef.child("users").child(mUser.getUid()));
        this.geoFire2 = new GeoFire(databaseRef.child("user-geofire"));
    }


    public DatabaseReference getLocation(String uid){
        return databaseRef.child("user-location").child(uid);
    }

    public Task<Void> createLocation(Location location){
        return databaseRef.child("user-location").child(location.getUid()).setValue(location);
    }

    public void deleteLocation(Location location){
        databaseRef.child("user-location").child(location.getUid()).removeValue();
    }

    public void updateUserLocation(User user){
        databaseRef.child("users").child(user.getUid()).setValue(user);
        geoFire2.setLocation(user.getUid(), new GeoLocation(user.latitude,user.longitude));
    }

}
