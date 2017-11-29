package com.lesgood.guru.ui.order;


import com.lesgood.guru.base.annotation.FragmentScope;
import com.lesgood.guru.data.model.User;

import com.lesgood.guru.data.remote.OrderService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 3/2/17.
 */

@Module
public class OrderFragmentModule {
    OrderFragment fragment;

    public OrderFragmentModule(OrderFragment fragment){
        this.fragment = fragment;
    }

    @FragmentScope
    @Provides
    OrderFragment provideOrderFragment(){
        return fragment;
    }

    @FragmentScope
    @Provides
    OrderPresenter provideOrderPresenter(User user,OrderService orderService){
        return new OrderPresenter(fragment, user,orderService);
    }
}
