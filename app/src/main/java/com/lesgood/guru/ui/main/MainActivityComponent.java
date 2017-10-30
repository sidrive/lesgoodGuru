package com.lesgood.guru.ui.main;


import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 4/16/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                MainActivityModule.class
        }
)
public interface MainActivityComponent {
    MainActivity inject(MainActivity activity);
}
