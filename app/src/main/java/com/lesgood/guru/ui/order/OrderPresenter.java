package com.lesgood.guru.ui.order;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.OrderService;
import com.lesgood.guru.ui.order_detail.OrderDetailActivity;


/**
 * Created by Agus on 3/2/17.
 */

public class OrderPresenter implements BasePresenter {
    OrderFragment fragment;
    User user;
    OrderService orderService;
    public OrderPresenter(OrderFragment fragment, User user,
        OrderService orderService){
        this.fragment = fragment;
        this.user = user;
        this.orderService = orderService;
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }
    public void openOrderdetail(String oid){
        orderService.getOrdersById(oid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);
                if (dataSnapshot!=null){
                    fragment.openDetailOrder(order);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
