package com.lesgood.guru.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import com.google.android.gms.location.LocationListener;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.base.config.DefaultConfig;
import com.lesgood.guru.data.model.Location;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.APIService;
import com.lesgood.guru.data.remote.LocationService;
import retrofit2.Retrofit;

/**
 * Created by sim-x on 11/27/17.
 */

public class MapPresenter implements BasePresenter {
  MapsActivity activity;
  LocationService locationService;
  User user;
  Location location;
  APIService apiService;
  DefaultConfig defaultConfig;


  public MapPresenter(MapsActivity activity,

      LocationService locationService, User usert) {
    this.activity = activity;
    this.locationService = locationService;
    this.user = user;

  }

  @Override
  public void subscribe() {

  }

  @Override
  public void unsubscribe() {

  }


  public interface OnLocationListener {
    void onLocationFetched(Location location);
  }

}
