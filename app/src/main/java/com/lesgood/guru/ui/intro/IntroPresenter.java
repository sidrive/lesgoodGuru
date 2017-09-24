package com.lesgood.guru.ui.intro;

import com.google.firebase.auth.FirebaseAuth;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.remote.UserService;
import com.lesgood.guru.ui.splash.SplashActivity;

/**
 * Created by Agus on 9/23/17.
 */

public class IntroPresenter implements BasePresenter {

    IntroActivity activity;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private UserService userService;

    public IntroPresenter(IntroActivity activity, UserService userService) {
        this.activity = activity;
        this.userService = userService;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }


    public void save(String uid, boolean status){
        userService.updateAcceptTOS(uid, status);
        activity.showMainActivity();
    }
}
