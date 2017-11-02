package com.lesgood.guru.ui.add_location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.AppComponent;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.base.config.DefaultConfig;
import com.lesgood.guru.data.helper.Const;
import com.lesgood.guru.data.model.Location;
import com.lesgood.guru.data.model.Provinces;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.APIService;
import com.lesgood.guru.data.remote.LocationService;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
    Location location;
    APIService APIService;
    DefaultConfig defaultConfig;
    Retrofit retrofit;
    public AddLocationPresenter(AddLocationActivity activity,
                                LocationService locationService,
                                User user, Retrofit retrofit ){
        this.activity = activity;
        this.locationService = locationService;
        this.user = user;
        this.retrofit = retrofit;
        this.location = new Location();
        this.APIService = retrofit.create(APIService.class);
        this.defaultConfig = new DefaultConfig(activity.getApplicationContext());
    }

    @Override
    public void subscribe() {
        getUserLocation();
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
                Log.e("onDataChange", "AddLocationPresenter" + dataSnapshot);
                if (location != null){
                    //activity.init(location);
                    LatLng latLng = new LatLng(location.getLat(),location.getLng());
                    activity.markUserLocation(latLng,location.getAddress());
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
        locationService.createLocation(location).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                activity.setLoadingProgress(false);
                //activity.successAddLocation(location);
            }
        }).addOnFailureListener(e -> {
            e.printStackTrace();
            activity.setLoadingProgress(false);
        });
    }
    public void getAddressLocation(LatLng latLng){
        activity.setLoadingProgress(true);
        String loc = latLng.latitude +","+latLng.longitude;
        defaultConfig.setApiUrl(Const.BASE_URL_MAP);
        retrofit.create(com.lesgood.guru.data.remote.APIService.class).getAddress("AIzaSyCGimiNQYU3Sj9LECSPgpAGXoRdGMiqJZY",loc)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                responseGeomap -> {
                    activity.setLoadingProgress(false);
                    if (responseGeomap.getStatus().equals("OK")){
                        user.setLatitude(latLng.latitude);
                        user.setLongitude(latLng.longitude);
                        user.setFullAddress(responseGeomap.getResults().get(0).getFormattedAddress());
                        user.setLocation(responseGeomap.getResults().get(7).getFormattedAddress());
                        locationService.updateUserLocation(user);
                        location.setAddress(responseGeomap.getResults().get(0).getFormattedAddress());
                        location.setAddress_2(responseGeomap.getResults().get(0).getFormattedAddress());
                        location.setLat(responseGeomap.getResults().get(0).getGeometry().getLocation().getLat());
                        location.setLng(responseGeomap.getResults().get(0).getGeometry().getLocation().getLng());
                        location.setProvince_name(responseGeomap.getResults().get(7).getFormattedAddress());
                        location.setUid(user.getUid());
                        createLocation(location);
                        BaseApplication.get(activity.getApplicationContext()).createUserComponent(user);
                        activity.setAddressMap(responseGeomap.getResults().get(0).getFormattedAddress());
                    }
                },
                throwable -> {
                    activity.setLoadingProgress(false);
                }
        );
    }
        public void updateUserLocation(User user){
        locationService.updateUserLocation(user);
    }

}
