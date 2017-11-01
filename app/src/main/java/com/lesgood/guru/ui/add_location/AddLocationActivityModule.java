package com.lesgood.guru.ui.add_location;


import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.model.Location;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.LocationService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Agus on 3/3/17.
 */

@Module
public class AddLocationActivityModule {
    AddLocationActivity activity;

    public AddLocationActivityModule(AddLocationActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    AddLocationActivity provideAddLocationActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    AddLocationPresenter provideAddLocationPresenter(LocationService locationService,
                                                     User user, Retrofit retrofit){
        return new AddLocationPresenter(activity, locationService, user, retrofit);
    }
}
