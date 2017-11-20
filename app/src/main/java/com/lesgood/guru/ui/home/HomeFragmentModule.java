package com.lesgood.guru.ui.home;

import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.base.annotation.FragmentScope;
import com.lesgood.guru.data.remote.UserService;

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
    HomePresenter provideHomePresenter(UserService userService){
        return new HomePresenter(fragment, userService);
    }
    @ActivityScope
    @Provides
    DaysAdapter provideDayAdapter(){
        return new DaysAdapter(fragment);
    }

    @ActivityScope
    @Provides
    TimesAdapter provideTimeAdapter(){return new TimesAdapter(fragment);}
}
