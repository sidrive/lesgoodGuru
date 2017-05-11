package com.lesgood.guru.ui.profile;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.UserService;

/**
 * Created by Agus on 2/28/17.
 */

public class ProfilePresenter implements BasePresenter {
    ProfileFragment fragment;
    User user;
    UserService userService;

    public ProfilePresenter(ProfileFragment fragment, User user, UserService userService){
        this.fragment = fragment;
        this.user = user;
        this.userService = userService;

    }

    @Override
    public void subscribe() {
        if (user != null){
            getUserAbout(user.getUid());
        }

    }

    @Override
    public void unsubscribe() {

    }

    public void getUserAbout(String uid){
        userService.getUserAbout(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    fragment.initAbout(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateUserAbout(String uid, String about){
        userService.updateAbout(uid, about);
    }

    public void updateUserTotalSkill(String uid, int total){
        userService.updateTotalSkill(uid,total);
    }

    public void updateUserPrice(String uid, int price){
        userService.updateUserPrice(uid, price);
    }
}
