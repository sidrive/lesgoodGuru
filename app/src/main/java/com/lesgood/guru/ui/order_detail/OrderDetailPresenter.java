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
        orderService.approveOrder(order.getOid()).addOnCompleteListener(task -> {
            order.setStatus("pending_murid");
            activity.successAction(order);
        }).addOnFailureListener(e -> activity.successAction(order));
    }

    public void declineOrder(final Order order){
        orderService.declineOrder(order.getOid()).addOnCompleteListener(task -> {
            order.setStatus("cancel_guru");
            activity.successAction(order);
        }).addOnFailureListener(e -> activity.successAction(order));
    }

    /*public void declineOrder(final Order order){
        orderService.declineOrder(order.getOid()).addOnFailureListener(e -> {

        }).addOnCompleteListener(task -> activity.successAction(order));
    }*/
    public void acceptOrderFromChangeTeacher(final  Order order){
        order.setStatus("SUCCSES");

        orderService.createOrderFromChangeTeacher(order).addOnCompleteListener(task -> {
            if (task.isComplete()){
                activity.successAction(order);
                updateValueOrderId(order);
            }
        });
        orderService.changeOrderSuccess(order.getStatusPayment());
    }
    public void updateValueOrderId(Order order){
        orderService.updateOrderFromChangeTeacher(order).addOnCompleteListener(task -> {
            updateDataOrder(order);
            updateOrderIdandStatus(order);
        });
    }

    private void updateOrderIdandStatus(Order order) {
        orderService.changeOrderSuccess(order.getOrderType());
    }

    private void updateDataOrder(Order order) {
        orderService.removeOrderFromChangeTeacher(order.getOid());
    }
}
