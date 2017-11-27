package com.lesgood.guru.data.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.guru.data.model.Order;

/**
 * Created by Agus on 2/27/17.
 */

public class OrderService {
    DatabaseReference databaseRef;

    public OrderService(){
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getOrders(){
        return databaseRef.child("orders");
    }

    public DatabaseReference getOrderItems(String oid){
        return databaseRef.child("order-items").child(oid);
    }

    public Task<Void> approveOrder(String oid){
        return databaseRef.child("orders").child(oid).child("status").setValue("pending_murid");
    }

    public Task<Void> declineOrder(String oid){
        return databaseRef.child("orders").child(oid).child("status").setValue("cancel_guru");
    }
    public Task<Void> changeOrderSuccess(String oid){
        return databaseRef.child("orders").child(oid).child("status").setValue("SUCCESS");
    }
    public Task<Void> createOrderFromChangeTeacher(Order order){
        return databaseRef.child("orders").child(order.getOrderType()).setValue(order);
    }
    public Task<Void> removeOrderFromChangeTeacher(String temOid){
        return databaseRef.child("orders").child(temOid).removeValue();
    }
    public Task<Void> updateOrderFromChangeTeacher(Order order){
        return databaseRef.child("orders").child(order.getOid()).child("oid").setValue(order.getOrderType());
    }
}
