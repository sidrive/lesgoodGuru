package com.lesgood.guru.ui.intro;


import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.remote.UserService;
import com.lesgood.guru.ui.splash.SplashActivity;
import com.lesgood.guru.ui.splash.SplashPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class IntroActivityModule {
    private IntroActivity activity;

    public IntroActivityModule(IntroActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    IntroActivity provideIntroActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    IntroPresenter provideIntroPresenter(UserService userService) {
        return new IntroPresenter(activity, userService);
    }

}
