package com.lesgood.guru.ui.setting;


import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.FirebaseUserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 4/21/17.
 */

@Module
public class SettingActivityModule {
    SettingActivity activity;

    public SettingActivityModule(SettingActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    SettingActivity provideSettingActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    SettingPresenter provideSettingPresenter(User user, FirebaseUserService firebaseUserService){
        return new SettingPresenter(activity, firebaseUserService, user);
    }
}
