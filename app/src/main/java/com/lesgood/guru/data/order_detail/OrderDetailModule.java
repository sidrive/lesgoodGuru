package com.lesgood.guru.data.order_detail;


import com.lesgood.guru.base.annotation.UserScope;
import com.lesgood.guru.data.model.Order;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 5/3/17.
 */

@Module
public class OrderDetailModule {
    Order order;

    public OrderDetailModule(Order order){
        this.order = order;
    }

    @UserScope
    @Provides
    Order provideOrder(){
        return order;
    }
}
