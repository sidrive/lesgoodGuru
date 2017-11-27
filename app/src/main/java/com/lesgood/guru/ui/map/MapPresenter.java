package com.lesgood.guru.ui.map;

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
  Retrofit retrofit;

  public MapPresenter(MapsActivity activity,
      LocationService locationService, User user, Retrofit retrofit) {
    this.activity = activity;
    this.locationService = locationService;
    this.user = user;
    this.retrofit = retrofit;
  }

  @Override
  public void subscribe() {

  }

  @Override
  public void unsubscribe() {

  }
}
