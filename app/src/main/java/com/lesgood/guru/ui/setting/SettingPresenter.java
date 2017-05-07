package com.lesgood.guru.ui.setting;


import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.FirebaseUserService;

/**
 * Created by Agus on 4/21/17.
 */

public class SettingPresenter implements BasePresenter {
    SettingActivity activity;
    FirebaseUserService firebaseUserService;
    User user;

    public SettingPresenter(SettingActivity activity, FirebaseUserService firebaseUserService, User user){
        this.activity = activity;
        this.firebaseUserService = firebaseUserService;
        this.user = user;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void logout(){
        firebaseUserService.logOut(user.getProvider());
        activity.logingOut();
    }
}
