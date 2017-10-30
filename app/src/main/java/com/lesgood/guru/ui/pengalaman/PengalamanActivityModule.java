package com.lesgood.guru.ui.pengalaman;

import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 6/1/17.
 */

@Module
public class PengalamanActivityModule {
    PengalamanActivity activity;

    public PengalamanActivityModule(PengalamanActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    PengalamanActivity providePrestasiActivity(){
        return activity = activity;
    }

    @ActivityScope
    @Provides
    PengalamanPresenter providePrestasiPresenter(UserService userService, User user){
        return new PengalamanPresenter(activity, userService, user);
    }

    @ActivityScope
    @Provides
    PengalamanAdapter providePrestasiAdapter(){
        return new PengalamanAdapter(activity);
    }
}
