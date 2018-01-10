package com.lesgood.guru.ui.order_detail;


import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.Invoices;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.OrderService;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

    /*=============================================================================================================================
                                                                FROM CHANGE THEACER
      =============================================================================================================================*/
    // TODO: 12/20/17 Accept pergantian guru, update gui order dengan gui baru, delete order pergantian  guru sesuai dengan oid
    public void acceptChangeTeacher(Order order,String status){
        orderService.changeStatusPergantianGuru(order.getOldOid(),status)
            .addOnCompleteListener(task -> {
                // TODO: 12/20/17 jika status task is true update data order
                if (task.isSuccessful()){
                    if (status.equalsIgnoreCase("accept")){
                        updateDataOrderWithNewData(order);
                    }else if (status.equalsIgnoreCase("decline")){
                        updateOldOrderId(order);
                        updateOrder(order);
                    }
                }
            })
            .addOnFailureListener(e -> {

            });
    }

    public void updateOrder(Order order) {
        Log.e("updateOrder", "OrderDetailPresenter" + order.getOid());
        orderService.changeStatusPergantianGuru(order.getOid(),"decline").addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                activity.showProgress(false);
            }
        });
        orderService.declineOrder(order.getOid()).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                activity.showProgress(false);
            }
        });
    }

    private void updateOldOrderId(Order order) {
        Log.e("updateOrder", "OrderDetailPresenter" + order.getOid());
        orderService.changeStatusPergantianGuru(order.getOldOid(),"none").addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                activity.showProgress(false);
            }
        });
    }

    public void updateDataOrderWithNewData(Order order){
        orderService.changeDataGidOrder(order.getGid(),order.getOldOid())
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    BaseApplication.get(activity).createOrderDetailComponent(order);
                    updateStatusOrderRequestGantiGuru(order.getOid());
                }
            })
            .addOnFailureListener(e -> {
                activity.showProgress(false);
                Log.e("updateDataOrder", "OrderDetailPresenter" + e.getMessage());
            });
    }

    private void updateStatusOrderRequestGantiGuru(String oid) {
        orderService.declineOrder(oid).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                activity.showProgress(false);
                activity.successAction(order);
            }
        });

    }


    public void viewDetailOrder(String param){
        orderService.getOrdersById(param).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order =  dataSnapshot.getValue(Order.class);
                Log.e("onDataChange", "OrderDetailPresenter" + order);
                if(dataSnapshot != null){
                    BaseApplication.get(activity).createOrderDetailComponent(order);
                    activity.updateUI(order);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", "OrderDetailPresenter" + databaseError.getMessage());
            }
        });
    }
    public void getGuru(String gid){
        orderService.getUsers(gid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null){
                    User user = dataSnapshot.getValue(User.class);
                    activity.initDetailGuru(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void getSiswa(String uid){
        orderService.getUsers(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null){
                    User user = dataSnapshot.getValue(User.class);
                    activity.initDetailSiswa(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void getInvoice(String iid){
        orderService.getInvoce(iid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null){
                    Invoices invoices = dataSnapshot.getValue(Invoices.class);
                    activity.initDetailInvoice(invoices);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getAlamatOrder(LatLng latLng) {
        Geocoder geocoder = new Geocoder(activity, new Locale("in"));
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,2);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses !=null && addresses.size() != 0){
            Log.e("getAlamatOrder", "OrderDetailPresenter" + addresses.get(0));
            activity.updateAlamatSiswa(addresses.get(0).getAddressLine(0));
        }
    }
}
