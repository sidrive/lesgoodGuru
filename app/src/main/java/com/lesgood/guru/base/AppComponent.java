package com.lesgood.guru.base;


import com.lesgood.guru.data.firebase.FirebaseModule;
import com.lesgood.guru.data.network.NetworkModule;
import com.lesgood.guru.data.user.UserComponent;
import com.lesgood.guru.data.user.UserModule;
import com.lesgood.guru.ui.add_location.AddLocationActivity;
import com.lesgood.guru.ui.add_location.AddLocationActivityComponent;
import com.lesgood.guru.ui.add_location.AddLocationActivityModule;
import com.lesgood.guru.ui.login.LoginActivityComponent;
import com.lesgood.guru.ui.login.LoginActivityModule;
import com.lesgood.guru.ui.splash.SplashActivityComponent;
import com.lesgood.guru.ui.splash.SplashActivityModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Agus on 4/16/17.
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                FirebaseModule.class,
                NetworkModule.class
        }
)
public interface AppComponent {

    SplashActivityComponent plus(SplashActivityModule activityModule);

    LoginActivityComponent plus(LoginActivityModule activityModule);

    UserComponent plus(UserModule userModule);

    Retrofit retrofit();
}