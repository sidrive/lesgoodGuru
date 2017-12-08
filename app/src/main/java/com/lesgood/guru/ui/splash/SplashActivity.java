package com.lesgood.guru.ui.splash;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;


import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.verification.VerificationActivity;
import com.lesgood.guru.ui.edit_profile.EditProfileActivity;
import com.lesgood.guru.ui.intro.IntroActivity;
import com.lesgood.guru.ui.login.LoginActivity;
import com.lesgood.guru.ui.main.MainActivity;
import com.lesgood.guru.ui.order_detail.OrderDetailActivity;

import javax.inject.Inject;


public class SplashActivity extends BaseActivity {
    @Inject
    SplashPresenter presenter;
    String oid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LocalBroadcastManager.getInstance(this).registerReceiver(tokenReceiver,
                new IntentFilter("tokenReceiver"));
        Bundle extra = getIntent().getExtras();

       /* oid = "51561";*/
        if (extra != null) {
            for (String key : extra.keySet()) {
                String value = extra.getString(key);

                if (key.equals("orderid") && (value != null)) {
                    /*OrderDetailActivity.starFromNotif(this,value);*/
                    Log.e("SplashActivity", "onCreate: " + value);
                    oid = value.replaceAll("\\s+","");
                }

            }
        }

    }

    BroadcastReceiver tokenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra("token");
            Log.e("SplashActivity", "onReceive: " + token);
            if(token != null)
            {
                presenter.updateFCMToken(token);
            }


        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    public void showLoginActivity() {
        Intent a=new Intent(this,LoginActivity.class);
        startActivity(a);
        finish();

    }

    public void showRegisterActivity(User user){
        EditProfileActivity.startWithUser(this, user, true);
        finish();
    }

    public void showMainActivity(User user){
        if (oid!=null){
            Log.e("showMainActivity", "SplashActivity" + oid.length());
            MainActivity.startWithUserParam(this,user,oid);
            finish();
        }else {
            MainActivity.startWithUser(this, user);
            finish();
        }

    }

    public void showIntroActivity(User user){
        IntroActivity.startWithUser(this, user);
    }

    public void showVerificationActivity(User user){
        VerificationActivity.startWithUser(this, user);
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new SplashActivityModule(this))
                .inject(this);
    }


}
