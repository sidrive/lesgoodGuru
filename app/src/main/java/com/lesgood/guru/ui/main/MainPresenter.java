package com.lesgood.guru.ui.main;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.icu.text.LocaleDisplayNames;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.UserService;
import com.lesgood.guru.util.Utils;

/**
 * Created by Agus on 4/16/17.
 */

public class MainPresenter implements BasePresenter {

    MainActivity activity;
    UserService userService;
    User user;

    public MainPresenter(MainActivity activity, UserService userService, User user){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
    }

    @Override
    public void subscribe() {
        if (user != null){
        }
    }

    @Override
    public void unsubscribe() {

    }

    public void updateFCMToken(String uid, String token){
        userService.updateUserToken(uid, token);
    }

}
