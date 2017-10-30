package com.lesgood.guru.ui.home;

import com.google.firebase.database.DatabaseReference;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.remote.UserService;


/**
 * Created by Agus on 4/27/17.
 */

public class HomePresenter implements BasePresenter {
    HomeFragment fragment;
    UserService userService;
    DatabaseReference databaseRef;

    public HomePresenter(HomeFragment fragment, UserService userService){
        this.fragment = fragment;
        this.userService = userService;
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
    }

    public void updaeStatus(String uid, boolean status){
        userService.updateStatus(uid, status);
    }

}
