package com.lesgood.guru.ui.order_detail;


import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.data.remote.OrderService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 5/3/17.
 */

@Module
public class OrderDetailActivityModule {
    OrderDetailActivity activity;

    public OrderDetailActivityModule(OrderDetailActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    OrderDetailActivity provideOrderDetailActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    OrderDetailPresenter provideOrderDetailPresenter(OrderService orderService, Order order){
        return new OrderDetailPresenter(activity, orderService, order);
    }
    @ActivityScope
    @Provides
    PustakaAdapter providePustaka(OrderService orderService){
        return new PustakaAdapter(activity,orderService);

    }


}
