package com.lesgood.guru.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.lesgood.guru.base.config.DefaultConfig;
import com.lesgood.guru.data.firebase.FirebaseModule;
import com.lesgood.guru.data.main.MainComponent;
import com.lesgood.guru.data.main.MainModule;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.network.NetworkModule;
import com.lesgood.guru.data.user.UserComponent;
import com.lesgood.guru.data.user.UserModule;
import com.lesgood.guru.ui.main.MainActivity;

/**
 * Created by Agus on 4/16/17.
 */

public class BaseApplication extends Application {
    private AppComponent appComponent;
    private UserComponent userComponent;
    private MainComponent mainComponent;
    private DefaultConfig defaultConfig;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        FirebaseApp.initializeApp(base);
        defaultConfig = new DefaultConfig(base);
        MultiDex.install(getBaseContext());
    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        Log.d("initappcomponent", " = "+defaultConfig.getApiUrl());
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .firebaseModule(new FirebaseModule())
                .networkModule(new NetworkModule(defaultConfig.getApiUrl()))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public UserComponent createUserComponent(User user) {
        userComponent = appComponent.plus(new UserModule(user));
        return userComponent;
    }

    public UserComponent getUserComponent() {
        return userComponent;
    }

    public void releaseUserComponent() {
        userComponent = null;
    }

    public MainComponent createMainComponent(MainActivity activity) {
        mainComponent = userComponent.plus(new MainModule(activity));
        return mainComponent;
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    public void releaseMainComponent() {
        mainComponent = null;
    }

}
