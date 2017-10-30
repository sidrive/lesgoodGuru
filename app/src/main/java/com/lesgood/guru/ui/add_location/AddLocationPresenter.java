package com.lesgood.guru.ui.add_location;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.Location;
import com.lesgood.guru.data.model.Provinces;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.APIService;
import com.lesgood.guru.data.remote.LocationService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Agus on 3/3/17.
 */

public class AddLocationPresenter implements BasePresenter {
    AddLocationActivity activity;
    LocationService locationService;
    User user;
    APIService APIService;
    private boolean isForProvince = true;

    public AddLocationPresenter(AddLocationActivity activity,
                                LocationService locationService,
                                User user, Retrofit retrofit){
        this.activity = activity;
        this.locationService = locationService;
        this.user = user;
        this.APIService = retrofit.create(APIService.class);
    }

    @Override
    public void subscribe() {
        getUserLocation();
        getProvince();
    }

    @Override
    public void unsubscribe() {

    }

    public void getUserLocation(){
        activity.setLoadingProgress(true);
        locationService.getLocation(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                activity.setLoadingProgress(false);
                Location location = dataSnapshot.getValue(Location.class);
                if (location != null){
                    activity.init(location);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                activity.setLoadingProgress(false);
            }
        });
    }

    public void createLocation(final Location location){
        activity.setLoadingProgress(true);
        locationService.createLocation(location).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    activity.setLoadingProgress(false);
                    activity.successAddLocation(location);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                activity.setLoadingProgress(false);
            }
        });
    }

    public void getProvince(){
        isForProvince = true;
        Call<Provinces> call = APIService.getProvince();
        call.enqueue(callBackGetProvince);
    }

    private Callback<Provinces> callBackGetProvince = new Callback<Provinces>() {
        @Override
        public void onResponse(Call<Provinces> call, Response<Provinces> response) {
            if(response.isSuccessful()) {
                Provinces provinces = response.body();
                activity.setProvinces(provinces.getContent());
            }
        }

        @Override
        public void onFailure(Call<Provinces> call, Throwable t) {

        }
    };

    public void getChildren(String id){
        isForProvince = false;
        Call<Provinces> call = APIService.getChildren(id);
        call.enqueue(callBackGetKabupaten);
    }

    private Callback<Provinces> callBackGetKabupaten = new Callback<Provinces>() {
        @Override
        public void onResponse(Call<Provinces> call, Response<Provinces> response) {
            if(response.isSuccessful()) {
                Provinces provinces = response.body();
                activity.setKabupaten(provinces.getContent());
            }
        }

        @Override
        public void onFailure(Call<Provinces> call, Throwable t) {

        }
    };

    public void updateUserLocation(User user){
        locationService.updateUserLocation(user);
    }
}
