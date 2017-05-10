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
        return databaseRef.child("orders").child(oid).child("status").setValue("pending");
    }

    public Task<Void> declineOrder(String oid){
        return databaseRef.child("orders").child(oid).child("status").setValue("cancel");
    }
}
