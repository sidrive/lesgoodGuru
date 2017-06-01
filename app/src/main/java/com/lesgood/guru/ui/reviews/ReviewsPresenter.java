package com.lesgood.guru.ui.reviews;

import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.UserService;

/**
 * Created by Agus on 6/1/17.
 */

public class ReviewsPresenter implements BasePresenter {
    ReviewsActivity activity;
    UserService userService;
    User user;

    public ReviewsPresenter(ReviewsActivity activity, UserService userService, User user){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
