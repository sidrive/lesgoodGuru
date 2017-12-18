package com.lesgood.guru.ui.wallet;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.PartnerPayment;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.model.Withdraw;
import com.lesgood.guru.data.remote.UserService;

/**
 * Created by sim-x on 11/30/17.
 */

public class WalletPresenter implements BasePresenter {
  WalletActivity activity;
  UserService userService;
  User user;

  public WalletPresenter(WalletActivity activity,
      UserService userService, User user) {
    this.activity = activity;
    this.userService = userService;
    this.user = user;
  }

  @Override
  public void subscribe() {

  }



  @Override
  public void unsubscribe() {

  }

  public void getDetailUser(String uid) {
    userService.getUser(uid).addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        if (dataSnapshot!=null){
          activity.updateUi(user);
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }
  public void requestWitdraw(Withdraw  withdraw){
    userService.createRequestWithdraw(withdraw)
        .addOnFailureListener(e -> {
          activity.showLoading(false);
          Log.e("requestWitdraw", "WalletPresenter" + e.getMessage());
        })
        .addOnCompleteListener(task -> {
          activity.showLoading(false);
          userService.updateSaldoUser(withdraw.getUid());
        });
  }
  public void chekPaymentPartner(String uid){
    final boolean[] isDataExist = {false};
    activity.showLoading(true);
    userService.getUserPayment(uid).child("bank").addListenerForSingleValueEvent(
        new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.getValue()!=null){
              Log.e("onDataChange", "WalletPresenter" + dataSnapshot.getValue());
              activity.prosesWithdraw();
            }else {
              activity.showLoading(false);
              activity.showDiloagDataPayment(uid);
            }
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
            activity.showLoading(false);
          }
        });

  }
}
