package com.lesgood.guru.ui.order_detail;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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


    public void acceptOrder(final Order order){
        orderService.approveOrder(order.getOid()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                order.setStatus("pending_murid");
                activity.successAction(order);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                activity.successAction(order);
            }
        });
    }

    public void declineOrder(final Order order){
        orderService.declineOrder(order.getOid()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                activity.successAction(order);
            }
        });
    }

}
