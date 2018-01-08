package com.lesgood.guru.ui.order;

import android.util.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.OrderService;

import java.util.Calendar;

/**
 * Created by Agus on 5/2/17.
 */

public class PlaceHolderFragmentPresenter implements BasePresenter {
    PlaceholderFragment fragment;
    OrderService orderService;
    DatabaseReference databaseRef;
    User user;
    ChildEventListener orderEventListener;

    public PlaceHolderFragmentPresenter(PlaceholderFragment fragment, OrderService orderService, User user){
        this.fragment = fragment;
        this.orderService = orderService;
        this.user = user;
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (orderEventListener != null) databaseRef.removeEventListener(orderEventListener);
    }

    public void getOrders(final String status){
        orderEventListener = orderService.getOrders().orderByChild("gid").equalTo(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Order order =dataSnapshot.getValue(Order.class);

                if (order != null) {
                    if (order.getStatus()!=null){
                        if (status.equalsIgnoreCase("waiting")){
                            if (order.getStatus().equalsIgnoreCase("pending_guru") || order.getStatus().equalsIgnoreCase("pending_murid") || order.getStatus().equalsIgnoreCase("pending")|| order.getStatus().equalsIgnoreCase("change_guru")){
                                fragment.showAddedOrder(order);
                            }

                        }
                        if (status.equalsIgnoreCase("complete")){
                            if (order.getStatus().equalsIgnoreCase("success")  ){
                                fragment.showAddedOrder(order);
                            }
                        }
                        if (status.equalsIgnoreCase("oper order")){
                            if (order.getStatusGantiGuru().equalsIgnoreCase("request")){
                                fragment.showAddedOrder(order);
                            }
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Order order =dataSnapshot.getValue(Order.class);
                if (order != null) {
                    if (order.getStatus()!=null){
                        if (status.equalsIgnoreCase("waiting")){
                            if (order.getStatus().equalsIgnoreCase("pending_guru") || order.getStatus().equalsIgnoreCase("pending_murid") || order.getStatus().equalsIgnoreCase("pending")|| order.getStatus().equalsIgnoreCase("change_guru")){
                                fragment.showChangedOrder(order);
                            }

                        }
                        if (status.equalsIgnoreCase("complete")){
                            if (order.getStatus().equalsIgnoreCase("success") || order.getStatusGantiGuru().equalsIgnoreCase("none")){
                                fragment.showAddedOrder(order);
                            }
                        }
                        if (status.equalsIgnoreCase("oper order")){
                            if (order.getStatusGantiGuru().equalsIgnoreCase("request")){
                                fragment.showChangedOrder(order);
                            }
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
