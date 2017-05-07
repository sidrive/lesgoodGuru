package com.lesgood.guru.data.firebase;

import android.app.Application;


import com.lesgood.guru.data.remote.FirebaseImageService;
import com.lesgood.guru.data.remote.FirebaseUserService;
import com.lesgood.guru.data.remote.UserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class FirebaseModule {
    @Provides
    @Singleton
    public FirebaseUserService provideFirebaseUserService(Application application) {
        return new FirebaseUserService(application);
    }

    @Provides
    @Singleton
    public UserService provideUserService(Application application) {
        return new UserService(application);
    }

    @Provides
    @Singleton
    public FirebaseImageService provideFirebaseImageService(Application application) {
        return new FirebaseImageService(application);
    }

}
