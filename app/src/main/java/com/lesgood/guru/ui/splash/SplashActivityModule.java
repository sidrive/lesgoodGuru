package com.lesgood.guru.ui.splash;



import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.remote.OrderService;
import com.lesgood.guru.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashActivityModule {
    private SplashActivity activity;

    public SplashActivityModule(SplashActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    SplashActivity provideSplashActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    SplashPresenter provideSplashActivityPresenter(UserService userService) {
        return new SplashPresenter(activity, userService);
    }

}
