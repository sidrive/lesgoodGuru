package com.lesgood.guru.ui.main;


import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.remote.UserService;

/**
 * Created by Agus on 4/16/17.
 */

public class MainPresenter implements BasePresenter {

    MainActivity activity;
    UserService userService;

    public MainPresenter(MainActivity activity, UserService userService){
        this.activity = activity;
        this.userService = userService;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
