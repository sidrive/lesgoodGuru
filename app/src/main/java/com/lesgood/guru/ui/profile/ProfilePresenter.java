package com.lesgood.guru.ui.profile;

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

    }

    @Override
    public void unsubscribe() {

    }
}
