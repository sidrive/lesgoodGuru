package com.lesgood.guru.ui.intro;


import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.ui.splash.SplashActivity;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(
        modules = {
                IntroActivityModule.class
        }
)
public interface IntroActivityComponent {
    IntroActivity inject(IntroActivity activity);
}
