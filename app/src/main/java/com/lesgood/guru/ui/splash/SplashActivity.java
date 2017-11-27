package com.lesgood.guru.ui.splash;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;


import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.verification.VerificationActivity;
import com.lesgood.guru.ui.edit_profile.EditProfileActivity;
import com.lesgood.guru.ui.intro.IntroActivity;
import com.lesgood.guru.ui.login.LoginActivity;
import com.lesgood.guru.ui.main.MainActivity;

import javax.inject.Inject;


public class SplashActivity extends BaseActivity {

    @Inject
    SplashPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LocalBroadcastManager.getInstance(this).registerReceiver(tokenReceiver,
                new IntentFilter("tokenReceiver"));

    }

    BroadcastReceiver tokenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra("token");
            Toast.makeText(context, "--------------", Toast.LENGTH_SHORT).show();
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
        MainActivity.startWithUser(this, user);
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
