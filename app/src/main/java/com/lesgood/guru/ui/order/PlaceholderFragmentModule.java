package com.lesgood.guru.ui.order;


import com.lesgood.guru.base.annotation.FragmentScope;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.OrderService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 3/15/17.
 */

@Module
public class PlaceholderFragmentModule {
    PlaceholderFragment fragment;

    public PlaceholderFragmentModule(PlaceholderFragment fragment){
        this.fragment = fragment;
    }

    @FragmentScope
    @Provides
    PlaceholderFragment providePlaceholderFragment(){
        return fragment;
    }

    @FragmentScope
    @Provides
    PlaceHolderFragmentPresenter providePlaceHolderFragmentPresenter(User user, OrderService orderService){
        return new PlaceHolderFragmentPresenter(fragment, orderService, user);
    }


}
