package com.lesgood.guru.ui.wallet;

import android.util.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.PartnerPayment;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.model.Withdraw;
import com.lesgood.guru.data.remote.UserService;
import com.lesgood.guru.util.Utils;

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
  getWithdeawList();
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
          activity.showDialogSuccessRequest();
        })
        .addOnCompleteListener(task -> {
          activity.showLoading(false);
          userService.updateSaldoUser(withdraw.getUid());
        });
  }
  public void chekPaymentPartner(String uid){
    activity.showLoading(true);

   userService.getUserPayment(uid).addListenerForSingleValueEvent(new ValueEventListener() {
     @Override
     public void onDataChange(DataSnapshot dataSnapshot) {
       Log.e("onDataChange", "WalletPresenter" + dataSnapshot.toString());
       if (dataSnapshot.getValue()!=null){
         PartnerPayment partnerPayment = dataSnapshot.getValue(PartnerPayment.class);
         if (partnerPayment.getAccount().length()!=0){
           activity.prosesWithdraw();
           getDetailUser(uid);
         }else {
           activity.showLoading(false);
           activity.showDiloagDataPayment(uid);
         }
       }
     }

     @Override
     public void onCancelled(DatabaseError databaseError) {
       Utils.showDialog(activity,databaseError.getMessage().toString(),null);
     }
   });
  }
  public void getWithdeawList(){
    userService.getWithdrawList(user.getUid()).addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot!=null){
          Withdraw withdraw = dataSnapshot.getValue(Withdraw.class);
          activity.startAddWithdraw(withdraw);
        }
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {

      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        Log.e("onCancelled", "WalletPresenter" + databaseError.getMessage());
      }
    });
  }
}
