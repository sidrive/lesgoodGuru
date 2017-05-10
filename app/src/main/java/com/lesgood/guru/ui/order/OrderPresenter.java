package com.lesgood.guru.ui.order;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.OrderService;


/**
 * Created by Agus on 3/2/17.
 */

public class OrderPresenter implements BasePresenter {
    OrderFragment fragment;
    User user;

    public OrderPresenter(OrderFragment fragment, User user){
        this.fragment = fragment;
        this.user = user;
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }



}
