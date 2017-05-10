package com.lesgood.guru.data.remote;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.guru.data.model.Location;
import com.lesgood.guru.data.model.User;

/**
 * Created by Agus on 3/6/17.
 */

public class LocationService {
    private DatabaseReference databaseRef;

    public LocationService(){
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
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
    }
}
