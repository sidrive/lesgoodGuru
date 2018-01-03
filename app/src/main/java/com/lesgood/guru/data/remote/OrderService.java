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
    /*=============================================================================================================================
                                                                    ORDER
     =============================================================================================================================*/
    public DatabaseReference getOrders(){
        return databaseRef.child("orders");
    }

    public DatabaseReference getOrdersById(String param){
        return databaseRef.child("orders").child(param);
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

    /*=============================================================================================================================
                                                                CHANGE THEACER
      =============================================================================================================================*/

    public Task<Void> changeStatusPergantianGuru(String oid, String status){
        return databaseRef.child("orders").child(oid).child("statusGantiGuru").setValue(status);
    }
    public Task<Void> changeDataGidOrder(String gid, String oid){
        return databaseRef.child("orders").child(oid).child("gid").setValue(gid);
    }
    /*=============================================================================================================================
                                                                INVOICE
      =============================================================================================================================*/
    public DatabaseReference getInvoce(String iid){
        return databaseRef.child("invoices").child(iid);
    }
    public DatabaseReference getUsers(String gid){
        return databaseRef.child("users").child(gid);
    }


    public DatabaseReference getStatusOrdersById(String oid){
        return databaseRef.child("orders").child(oid).child("status");
    }


}
