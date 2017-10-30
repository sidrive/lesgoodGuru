package com.lesgood.guru.data.location;



import com.lesgood.guru.base.annotation.UserScope;
import com.lesgood.guru.data.model.Location;
import com.lesgood.guru.data.remote.LocationService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 3/14/17.
 */

@Module
public class LocationModule {
    Location location;

    public LocationModule(Location location){
        this.location = location;
    }

    @UserScope
    @Provides
    Location provideLocation(){
        return location;
    }


}
