package com.lesgood.guru.ui.splash;



import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(
        modules = {
                SplashActivityModule.class
        }
)
public interface SplashActivityComponent {
    SplashActivity inject(SplashActivity activity);
}
