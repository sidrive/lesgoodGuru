package com.lesgood.guru.data.verification;

import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.remote.FirebaseImageService;
import com.lesgood.guru.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 5/31/17.
 */

@Module
public class VerificationActivityModule {
    VerificationActivity activity;

    public VerificationActivityModule(VerificationActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    VerificationActivity provideVerificationActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    VerificationPresenter provideVerificationPresenter(UserService userService, FirebaseImageService firebaseImageService){
        return new VerificationPresenter(activity, userService, firebaseImageService);
    }
}
