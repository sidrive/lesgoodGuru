package com.lesgood.guru.ui.home;

import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 4/27/17.
 */

@Module
public class HomeFragmentModule {
    HomeFragment fragment;

    public HomeFragmentModule(HomeFragment fragment){
        this.fragment=fragment;
    }

    @ActivityScope
    @Provides
    HomeFragment provideHomeFragment(){
        return fragment;
    }

    @ActivityScope
    @Provides
    HomePresenter provideHomePresenter(){
        return new HomePresenter(fragment);
    }

}
