package com.lesgood.guru.ui.reviews;

import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 6/1/17.
 */
@Module
public class ReviewsActivityModule {
    ReviewsActivity activity;

    public ReviewsActivityModule(ReviewsActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    ReviewsActivity provideReviewsActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    ReviewsPresenter provideReviewsPresenter(UserService userService, User user){
        return new ReviewsPresenter(activity, userService, user);
    }

    @ActivityScope
    @Provides
    ReviewsAdapter provideReviewsAdapter(){
        return new ReviewsAdapter(activity);
    }
}
