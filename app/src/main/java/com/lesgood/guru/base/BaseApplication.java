package com.lesgood.guru.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.lesgood.guru.base.config.DefaultConfig;
import com.lesgood.guru.data.firebase.FirebaseModule;
import com.lesgood.guru.data.location.LocationComponent;
import com.lesgood.guru.data.location.LocationModule;
import com.lesgood.guru.data.main.MainComponent;
import com.lesgood.guru.data.main.MainModule;
import com.lesgood.guru.data.model.Location;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.data.model.Skill;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.network.NetworkModule;
import com.lesgood.guru.data.order_detail.OrderDetailComponent;
import com.lesgood.guru.data.order_detail.OrderDetailModule;
import com.lesgood.guru.data.skill.SkillComponent;
import com.lesgood.guru.data.skill.SkillModule;
import com.lesgood.guru.data.user.UserComponent;
import com.lesgood.guru.data.user.UserModule;
import com.lesgood.guru.ui.main.MainActivity;
import com.lesgood.guru.ui.order_detail.OrderDetailActivity;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

/**
 * Created by Agus on 4/16/17.
 */

public class BaseApplication extends MultiDexApplication {
    private AppComponent appComponent;
    private UserComponent userComponent;
    private MainComponent mainComponent;
    private DefaultConfig defaultConfig;
    private LocationComponent locationComponent;
    private OrderDetailComponent orderDetailComponent;
    private SkillComponent skillComponent;
    private Context context;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ///FirebaseApp.initializeApp(base);
        defaultConfig = new DefaultConfig(base);
        context =base;
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
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .firebaseModule(new FirebaseModule())
                .networkModule(new NetworkModule(defaultConfig.getApiUrl()))
                .build();
        FirebaseApp.initializeApp(getBaseContext());
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

    public LocationComponent createLocationComponent(Location location){
        locationComponent = userComponent.plus(new LocationModule(location));
        return locationComponent;
    }

    public LocationComponent getLocationComponent(){
        return locationComponent;
    }

    public void releaseLocationComponent(){
        locationComponent = null;
    }


    public OrderDetailComponent createOrderDetailComponent(Order order){
        orderDetailComponent = mainComponent.plus(new OrderDetailModule((order)));
        return orderDetailComponent;
    }

    public OrderDetailComponent getOrderDetailComponent(){
        return orderDetailComponent;
    }

    public void releaseOrderDetailComponent(){
        orderDetailComponent = null;
    }

    public SkillComponent createSkillComponent(Skill skill){
        skillComponent = userComponent.plus(new SkillModule(skill));
        return skillComponent;
    }

    public SkillComponent getSkillComponent(){
        return skillComponent;
    }

    public void releaseSkillComponent(){
        skillComponent = null;
    }

}
