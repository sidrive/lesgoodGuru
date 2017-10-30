package com.lesgood.guru.ui.payment_detail;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.PartnerPayment;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.UserService;

/**
 * Created by Agus on 3/2/17.
 */

public class PaymentDetailPresenter implements BasePresenter {
    PaymentDetailActivity activity;
    User user;
    UserService userService;

    public PaymentDetailPresenter(PaymentDetailActivity activity,
                                  User user, UserService userService){
        this.activity = activity;
        this.user = user;
        this.userService = userService;
    }

    @Override
    public void subscribe() {
        if (user != null){
            getPayment(user.getUid());
        }
    }

    @Override
    public void unsubscribe() {

    }

    public void getPayment(String uid){
        userService.getUserPayment(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PartnerPayment partnerPayment = dataSnapshot.getValue(PartnerPayment.class);
                if (partnerPayment != null) activity.initPayment(partnerPayment);

                activity.setLoadingProgress(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                activity.setLoadingProgress(false);
            }
        });
    }

    public void save(PartnerPayment partnerPayment){
        userService.updateUserPayment(partnerPayment);
        activity.showSuccessSaved();
    }
}
