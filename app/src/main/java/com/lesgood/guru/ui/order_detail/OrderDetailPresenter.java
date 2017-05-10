package com.lesgood.guru.ui.order_detail;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.data.remote.OrderService;

/**
 * Created by Agus on 5/3/17.
 */

public class OrderDetailPresenter implements BasePresenter {
    OrderDetailActivity activity;
    OrderService orderService;
    Order order;

    public OrderDetailPresenter(OrderDetailActivity activity, OrderService orderService, Order order){
        this.activity = activity;
        this.orderService = orderService;
        this.order = order;
    }

    @Override
    public void subscribe() {
        if (order != null){
        }
    }

    @Override
    public void unsubscribe() {

    }
}
