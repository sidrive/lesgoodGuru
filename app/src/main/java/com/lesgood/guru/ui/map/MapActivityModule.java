package com.lesgood.guru.ui.map;

import com.google.android.gms.location.LocationListener;
import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.LocationService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by sim-x on 11/27/17.
 */

@Module
public class MapActivityModule {
  MapsActivity activity;
  public MapActivityModule (MapsActivity activity){
    this.activity = activity;
  }
  @ActivityScope
  @Provides
  MapsActivity provideMapsActivity(){
    return activity;
  }
  @ActivityScope
  @Provides
  MapPresenter provideMapsPresenter(LocationService locationService, User user ){
    return new MapPresenter(activity,locationService,user);
  }
}
