package com.lesgood.guru.ui.edit_profile;


import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.remote.FirebaseImageService;
import com.lesgood.guru.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 4/20/17.
 */

@Module
public class EditProfileActivityModule {
    EditProfileActivity activity;

    public EditProfileActivityModule(EditProfileActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    EditProfilePresenter provideEditProfilePresenter(UserService userService, FirebaseImageService firebaseImageService){
        return new EditProfilePresenter(activity, userService, firebaseImageService);
    }
}
