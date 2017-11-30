package com.lesgood.guru.ui.add_location;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.base.config.DefaultConfig;

import com.lesgood.guru.data.model.Location;

import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.APIService;
import com.lesgood.guru.data.remote.LocationService;


import com.lesgood.guru.util.Const;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    CompositeDisposable disposable;
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
        this.disposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        getUserLocation();
        defaultConfig.setApiUrl(Const.BASE_URL_MAP);
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
                    activity.init(location);
                   /* LatLng latLng = new LatLng(location.getLat(),location.getLng());
                    activity.markUserLocation(latLng,location.getAddress());*/
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
                activity.successAddLocation(location);
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
        APIService.getAddress("AIzaSyCGimiNQYU3Sj9LECSPgpAGXoRdGMiqJZY",loc)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            responseGeomap -> {
                activity.setLoadingProgress(false);
                Log.e("getAddressLocation", "AddLocationPresenter" + responseGeomap.getStatus());
                Log.e("getAddressLocation", "AddLocationPresenter" + responseGeomap.getResults().size());
                if (responseGeomap.getStatus().equals("OK")){
                    user.setLatitude(latLng.latitude);
                    user.setLongitude(latLng.longitude);
                    user.setFullAddress(responseGeomap.getResults().get(0).getFormattedAddress());
                    user.setLocation(responseGeomap.getResults().get(5).getFormattedAddress());

                    location.setAddress(responseGeomap.getResults().get(0).getFormattedAddress());
                    location.setAddress_2(responseGeomap.getResults().get(0).getFormattedAddress());
                    location.setLat(responseGeomap.getResults().get(0).getGeometry().getLocation().getLat());
                    location.setLng(responseGeomap.getResults().get(0).getGeometry().getLocation().getLng());
                    location.setProvince_name(responseGeomap.getResults().get(5).getFormattedAddress());
                    location.setUid(user.getUid());
                    createLocation(location);
                    BaseApplication.get(activity.getApplicationContext()).createUserComponent(user);
                    updateUserLocation(user);
                    activity.setAddressMap(responseGeomap.getResults().get(0).getFormattedAddress());


                }
            },
            throwable -> {
                activity.setLoadingProgress(false);
                Log.e("getAddressLocation", "AddLocationPresenter" + throwable.getMessage());
            }
        );
    }
    public void updateUserLocation(User user){
        locationService.updateUserLocation(user);
    }
    public void getAddress(LatLng latLng){
        activity.setLoadingProgress(true);
        disposable.add(
            Observable.just(latLng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(
                latLng1 -> {
                    Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
                    List<Address> address = new ArrayList<>();
                    try {
                        address.addAll(geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return address;
                })
            .subscribe(
                addresses -> {
                    activity.setLoadingProgress(false);
                    Log.e("getAddress", "AddLocationPresenter" + addresses.get(0));
                    user.setLatitude(latLng.latitude);
                    user.setLongitude(latLng.longitude);
                    user.setFullAddress(addresses.get(0).getAddressLine(0));
                    user.setLocation(addresses.get(0).getAdminArea());
                    BaseApplication.get(activity.getApplicationContext()).createUserComponent(user);
                    updateUserLocation(user);

                    activity.setAddressMap(addresses.get(0).getAddressLine(0));
                    location.setAddress(addresses.get(0).getAddressLine(0));
                    location.setAddress_2(addresses.get(0).getAddressLine(0));
                    location.setLat(addresses.get(0).getLatitude());
                    location.setLng(addresses.get(0).getLongitude());
                    location.setProvince_name(addresses.get(0).getAdminArea());
                    location.setUid(user.getUid());
                    createLocation(location);
                }
            )
        );
    }
}
