package com.lesgood.guru.ui.home;



import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 4/27/17.
 */
@ActivityScope
@Subcomponent(
        modules = {
                HomeFragmentModule.class
        }
)
public interface HomeFragmentComponent {
    HomeFragment inject(HomeFragment fragment);
}
