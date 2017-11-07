package com.lesgood.guru.ui.profile;

import android.util.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.helper.AppUtils;
import com.lesgood.guru.data.model.Skill;
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
            getTotalSkillUsers(user.getUid());
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
                AppUtils.showToas(fragment.getContext(),databaseError.getMessage());
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
    public void getTotalSkillUsers(String uid){
        userService.getTotalUserSkill(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null){
                    fragment.setTotalSkillUser(Integer.valueOf(dataSnapshot.getValue().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppUtils.showToas(fragment.getContext(),databaseError.getMessage());
            }
        });
    }
}
