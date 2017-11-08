package com.lesgood.guru.ui.prestasi;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.Prestasi;
import com.lesgood.guru.data.model.Skill;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.UserService;
import com.lesgood.guru.util.AppUtils;

/**
 * Created by Agus on 6/1/17.
 */

public class PrestasiPresenter implements BasePresenter {

    PrestasiActivity activity;
    UserService userService;
    User user;
    ChildEventListener childEventListener;
    DatabaseReference databaseReference;

    public PrestasiPresenter(PrestasiActivity activity, UserService userService, User user){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void subscribe() {
        getPrestasi();
    }

    @Override
    public void unsubscribe() {
        if (childEventListener != null) databaseReference.removeEventListener(childEventListener);
    }

    public void getPrestasi(){
        childEventListener = userService.getUserPrestasi(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Prestasi prestasi = dataSnapshot.getValue(Prestasi.class);
                if (prestasi != null){
                    activity.showAddedItem(prestasi);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Prestasi prestasi = dataSnapshot.getValue(Prestasi.class);
                if (prestasi != null){
                    activity.showChangedItem(prestasi);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Prestasi prestasi = dataSnapshot.getValue(Prestasi.class);
                if (prestasi != null){
                    activity.showRemovedItem(prestasi);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppUtils.showToas(activity.getApplicationContext(),databaseError.getMessage());
            }
        });
    }

    public void deletePrestasi(final Prestasi prestasi){
        userService.removePrestasi(user.getUid(), prestasi).addOnCompleteListener(task -> {
            activity.showLoading(false);
            activity.showRemovedItem(prestasi);
        });
    }

    public void updatePrestasi(Prestasi prestasi){
        userService.updatePrestasi(user.getUid(), prestasi).addOnCompleteListener(
            task -> activity.showLoading(false));
    }
}
