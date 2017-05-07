package com.lesgood.guru.ui.order;



import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 4/27/17.
 */

@Module
public class OrderFragmentModule {
    OrderFragment fragment;

    public OrderFragmentModule(OrderFragment fragment){
        this.fragment =fragment;
    }

    @Provides
    @ActivityScope
    OrderFragment provideFeedFragment(){
        return fragment;
    }

    @ActivityScope
    @Provides
    OrderPresenter provideFeedPresenter(){
        return new OrderPresenter(fragment);
    }
}
