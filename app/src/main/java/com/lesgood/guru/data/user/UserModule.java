package com.lesgood.guru.data.user;



import com.lesgood.guru.base.annotation.UserScope;
import com.lesgood.guru.data.model.User;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {
    User user;

    public UserModule(User user) {
        this.user = user;
    }

    @Provides
    @UserScope
    User provideUser() {
        return user;
    }

}
